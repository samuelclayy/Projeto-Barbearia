# 💈 Navalha — SaaS de Gestão para Barbearias

API REST para gestão de barbearias: agendamentos, clientes, serviços, barbeiros e controle financeiro. Cada barbearia é um cliente do sistema (multi-tenant), com planos de assinatura.

> ⚠️ **Projeto em desenvolvimento.** Backend em andamento; autenticação e integrações ainda não implementadas (ver [Roadmap](#-roadmap)).

---

## 🛠️ Tecnologias

- **Java 21**
- **Spring Boot 4.1.0** (Web MVC, Data JPA, Validation)
- **PostgreSQL**
- **Lombok**
- **Maven** (com wrapper `mvnw`)

---

## ✅ Funcionalidades implementadas

- **Barbeiros** — CRUD completo
- **Clientes** — CRUD completo
- **Serviços** — CRUD (nome, preço, duração)
- **Agendamentos** — CRUD, com status e status de pagamento
- **Transações** — registro de entradas/saídas, consulta por data e por período
- **Contas a pagar** — CRUD + marcação de pagamento
- **Financeiro** — indicadores: faturamento, recebido, fiado, a receber, ticket médio, nº de clientes, despesas e lucro
- Tratamento centralizado de exceções (recurso não encontrado, telefone duplicado, horário indisponível, limite do plano)

---

## 📦 Modelo de domínio

| Entidade      | Campos principais                                                      | Relações                          |
|---------------|------------------------------------------------------------------------|-----------------------------------|
| `Barbershop`  | name, email, password, plan                                            | tem vários `Barber`               |
| `Barber`      | name                                                                    | pertence a `Barbershop`; tem `Appointment` |
| `Client`      | name, phone                                                            | pertence a `Barbershop`           |
| `Service`     | name, price, durationMinutes                                          | pertence a `Barbershop`           |
| `Appointment` | dateTime, status, paymentStatus, value                                | liga `Client`, `Barber`, `Service` |
| `Transaction` | type, amount, date, description                                       | pertence a `Barbershop`           |
| `Payable`     | description, amount, dueDate, status                                  | pertence a `Barbershop`           |

### Enums

- **Plan:** `BASICO`, `PRO`
- **AppointmentStatus:** `AGUARDANDO`, `CONFIRMADO`, `CONCLUIDO`, `CANCELADO`
- **PaymentStatus:** `PAGO`, `FIADO`
- **TransactionType:** `ENTRADA`, `SAIDA`
- **AccountStatus** (contas a pagar): `PENDENTE`, `PAGO`, `VENCIDO`

---

## 🔌 Endpoints

| Recurso        | Método & rota                                                                 |
|----------------|-------------------------------------------------------------------------------|
| Barbeiros      | `GET /barbers` · `GET /barbers/{id}` · `POST /barbers` · `PUT /barbers/{id}` · `DELETE /barbers/{id}` |
| Clientes       | `GET /clients` · `GET /clients/{id}` · `POST /clients` · `PUT /clients/{id}` · `DELETE /clients/{id}` |
| Serviços       | `GET /services` · `GET /services/{id}` · `POST /services` · `PUT /services/{id}` · `DELETE /services/{id}` |
| Agendamentos   | `GET /appointments` · `GET /appointments/{id}` · `POST /appointments` · `PUT /appointments/{id}` · `DELETE /appointments/{id}` |
| Transações     | `GET /transactions` · `GET /transactions/{id}` · `GET /transactions/by-date` · `GET /transactions/by-period` · `POST /transactions` · `DELETE /transactions/{id}` |
| Contas a pagar | `GET /payables` · `GET /payables/{id}` · `POST /payables` · `PATCH /payables/{id}/pay` · `DELETE /payables/{id}` |
| Financeiro     | `GET /finance/revenue` · `/received` · `/credit` · `/owed` · `/average-ticket` · `/clients` · `/expenses` · `/profit` |

---

## 🚀 Como rodar localmente

### Pré-requisitos
- Java 21
- PostgreSQL rodando

### 1. Banco de dados
Crie um banco PostgreSQL. Config padrão esperada:

| Campo    | Valor      |
|----------|------------|
| Host     | localhost  |
| Porta    | 5433       |
| Database | navalha    |
| Usuário  | navalha    |

### 2. Criar o `application.yaml`
> ⚠️ Este arquivo **não está no repositório** (contém a senha do banco) e precisa ser criado na mão em:
> `barbearia/src/main/resources/application.yaml`

```yaml
spring:
  application:
    name: barbearia
  datasource:
    url: jdbc:postgresql://localhost:5433/navalha
    username: navalha
    password: SUA_SENHA
    driver-class-name: org.postgresql.Driver
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
```

### 3. Rodar
```bash
cd barbearia
./mvnw spring-boot:run
```
A API sobe em `http://localhost:8080`.

---

## 📁 Estrutura do projeto

```
barbearia/
└── src/main/java/com/barbearia/
    ├── model/         # entidades JPA
    ├── enums/         # enums de domínio
    ├── dto/           # objetos de transferência
    ├── repository/    # repositórios JPA
    ├── service/       # regras de negócio
    │   └── exceptions/
    └── controller/    # endpoints REST
        └── exceptions/
```

---

## 🗺️ Roadmap

- [ ] **Spring Security** — autenticação/autorização (login da barbearia)
- [ ] **Integração com WhatsApp** — notificações/agendamento
- [ ] **Chaves do Google** — OAuth / integrações
- [ ] Frontend (Next.js)
- [ ] Testes automatizados
- [ ] Deploy
