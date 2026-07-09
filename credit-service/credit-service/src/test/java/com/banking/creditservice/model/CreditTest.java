package com.banking.creditservice.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreditTest {

    @Test
    void shouldCreateCreditWithNoArgsConstructor() {

        Credit credit = new Credit();

        assertNotNull(credit);
    }


    @Test
    void shouldCreateCreditWithAllArgsConstructor() {

        LocalDate dueDate = LocalDate.of(2026, 12, 31);

        Credit credit = new Credit(
                "1",
                "C001",
                "PERSONAL",
                BigDecimal.valueOf(10000),
                BigDecimal.valueOf(8000),
                3,
                dueDate,
                "VIGENTE"
        );

        assertAll(
                () -> assertEquals("1", credit.getId()),
                () -> assertEquals("C001", credit.getCustomerId()),
                () -> assertEquals("PERSONAL", credit.getType()),
                () -> assertEquals(
                        BigDecimal.valueOf(10000),
                        credit.getAmount()
                ),
                () -> assertEquals(
                        BigDecimal.valueOf(8000),
                        credit.getBalance()
                ),
                () -> assertEquals(3, credit.getMaxCreditsAllowed()),
                () -> assertEquals(dueDate, credit.getDueDate()),
                () -> assertEquals("VIGENTE", credit.getStatus())
        );
    }


    @Test
    void shouldCreateCreditUsingBuilder() {

        Credit credit = Credit.builder()
                .id("2")
                .customerId("C002")
                .type("BUSINESS")
                .amount(BigDecimal.valueOf(20000))
                .balance(BigDecimal.valueOf(15000))
                .maxCreditsAllowed(5)
                .dueDate(LocalDate.of(2027, 1, 15))
                .status("VIGENTE")
                .build();


        assertEquals("2", credit.getId());
        assertEquals("C002", credit.getCustomerId());
        assertEquals("BUSINESS", credit.getType());
        assertEquals(BigDecimal.valueOf(20000), credit.getAmount());
        assertEquals(BigDecimal.valueOf(15000), credit.getBalance());
        assertEquals(5, credit.getMaxCreditsAllowed());
        assertEquals(LocalDate.of(2027, 1, 15), credit.getDueDate());
        assertEquals("VIGENTE", credit.getStatus());
    }


    @Test
    void shouldUpdateCreditUsingSetters() {

        Credit credit = new Credit();

        credit.setId("3");
        credit.setCustomerId("C003");
        credit.setType("CREDIT_CARD");
        credit.setAmount(BigDecimal.valueOf(5000));
        credit.setBalance(BigDecimal.valueOf(3000));
        credit.setMaxCreditsAllowed(2);
        credit.setDueDate(LocalDate.now());
        credit.setStatus("PENDIENTE");


        assertAll(
                () -> assertEquals("3", credit.getId()),
                () -> assertEquals("C003", credit.getCustomerId()),
                () -> assertEquals("CREDIT_CARD", credit.getType()),
                () -> assertEquals(BigDecimal.valueOf(5000), credit.getAmount()),
                () -> assertEquals(BigDecimal.valueOf(3000), credit.getBalance()),
                () -> assertEquals(2, credit.getMaxCreditsAllowed()),
                () -> assertEquals("PENDIENTE", credit.getStatus())
        );
    }


    @Test
    void shouldSupportEqualsAndHashCode() {

        Credit credit1 = Credit.builder()
                .id("1")
                .customerId("C001")
                .type("PERSONAL")
                .build();

        Credit credit2 = Credit.builder()
                .id("1")
                .customerId("C001")
                .type("PERSONAL")
                .build();


        assertEquals(credit1, credit2);
        assertEquals(credit1.hashCode(), credit2.hashCode());
    }


    @Test
    void shouldGenerateToString() {

        Credit credit = Credit.builder()
                .id("1")
                .customerId("C001")
                .type("PERSONAL")
                .build();


        String result = credit.toString();


        assertTrue(result.contains("C001"));
        assertTrue(result.contains("PERSONAL"));
    }
}