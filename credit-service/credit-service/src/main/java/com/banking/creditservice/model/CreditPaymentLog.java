package com.banking.creditservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "credit_payment_log")
public class CreditPaymentLog {

    @Id
    private String id;

    private String creditId;

    private String payerId;

    private BigDecimal amount;

    private LocalDateTime paymentDate;

}