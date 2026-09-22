package com.barbearia.service;

import com.barbearia.enums.PaymentStatus;
import com.barbearia.model.Appointment;
import com.barbearia.repository.AppointmentRepository;
import com.barbearia.repository.PayableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

/**
 * Service de calculo financeiro.
 *
 * CRITICO: todo calculo agora e ESCOPADO por barbearia.
 * Sem isso, o barbeiro A veria o faturamento do barbeiro B.
 */
@Service
public class FinanceService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PayableRepository payableRepository;

    private List<Appointment> appointmentsOfDay(LocalDate date, Long barbershopId) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return appointmentRepository.findByDateTimeBetweenAndBarbershopId(
                start, end, barbershopId);
    }

    private BigDecimal sumValues(List<Appointment> list) {
        return list.stream()
                .map(Appointment::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal dailyRevenue(LocalDate date, Long barbershopId) {
        return sumValues(appointmentsOfDay(date, barbershopId));
    }

    public BigDecimal dailyReceived(LocalDate date, Long barbershopId) {
        List<Appointment> paid = appointmentsOfDay(date, barbershopId).stream()
                .filter(a -> a.getPaymentStatus() == PaymentStatus.PAGO)
                .toList();
        return sumValues(paid);
    }

    public BigDecimal dailyCredit(LocalDate date, Long barbershopId) {
        List<Appointment> credit = appointmentsOfDay(date, barbershopId).stream()
                .filter(a -> a.getPaymentStatus() == PaymentStatus.FIADO)
                .toList();
        return sumValues(credit);
    }

    public BigDecimal totalOwed(Long barbershopId) {
        return appointmentRepository.sumValueByPaymentStatusAndBarbershopId(
                PaymentStatus.FIADO, barbershopId);
    }

    public BigDecimal monthlyExpenses(int month, int year, Long barbershopId) {
        YearMonth ym = YearMonth.of(year, month);
        return payableRepository.sumAmountByDueDateBetweenAndBarbershopId(
                ym.atDay(1), ym.atEndOfMonth(), barbershopId);
    }

    public BigDecimal monthlyProfit(int month, int year, Long barbershopId) {
        YearMonth ym = YearMonth.of(year, month);

        List<Appointment> ofMonth = appointmentRepository.findByDateTimeBetweenAndBarbershopId(
                ym.atDay(1).atStartOfDay(),
                ym.atEndOfMonth().atTime(LocalTime.MAX),
                barbershopId);

        BigDecimal revenue = sumValues(ofMonth);
        BigDecimal expenses = monthlyExpenses(month, year, barbershopId);

        return revenue.subtract(expenses);
    }

    public BigDecimal dailyAverageTicket(LocalDate date, Long barbershopId) {
        List<Appointment> ofDay = appointmentsOfDay(date, barbershopId);

        if (ofDay.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return sumValues(ofDay).divide(
                new BigDecimal(ofDay.size()), 2, RoundingMode.HALF_UP);
    }

    public Integer dailyClientCount(LocalDate date, Long barbershopId) {
        return appointmentsOfDay(date, barbershopId).size();
    }
}