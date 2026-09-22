package com.barbearia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_client",
        uniqueConstraints = @UniqueConstraint(columnNames = {"barbershop_id", "phone"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = false)
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Appointment> appointments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "barbershop_id", nullable = false)
    private Barbershop barbershop;
}
