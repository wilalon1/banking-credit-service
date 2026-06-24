package com.banking.creditservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "credits")
public class Credit {

    @Id
    private String id;

    private String customerId;

    private String type; // PERSONAL, BUSINESS, CREDIT_CARD

    private BigDecimal amount;

    private BigDecimal balance;

    private Integer maxCreditsAllowed;

}