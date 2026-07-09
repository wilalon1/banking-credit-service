package com.banking.creditservice.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CreditPaymentLogTest {

    @Test
    void shouldCreateCreditPaymentLogWithNoArgsConstructor() {

        CreditPaymentLog log = new CreditPaymentLog();

        assertNotNull(log);
    }


    @Test
    void shouldCreateCreditPaymentLogWithAllArgsConstructor() {

        LocalDateTime paymentDate = LocalDateTime.of(
                2026,
                7,
                9,
                10,
                30
        );

        CreditPaymentLog log = new CreditPaymentLog(
                "1",
                "CR001",
                "P001",
                BigDecimal.valueOf(500),
                paymentDate
        );


        assertAll(
                () -> assertEquals("1", log.getId()),
                () -> assertEquals("CR001", log.getCreditId()),
                () -> assertEquals("P001", log.getPayerId()),
                () -> assertEquals(
                        BigDecimal.valueOf(500),
                        log.getAmount()
                ),
                () -> assertEquals(paymentDate, log.getPaymentDate())
        );
    }


    @Test
    void shouldCreateCreditPaymentLogUsingBuilder() {

        LocalDateTime paymentDate = LocalDateTime.now();

        CreditPaymentLog log = CreditPaymentLog.builder()
                .id("2")
                .creditId("CR002")
                .payerId("P002")
                .amount(BigDecimal.valueOf(1000))
                .paymentDate(paymentDate)
                .build();


        assertEquals("2", log.getId());
        assertEquals("CR002", log.getCreditId());
        assertEquals("P002", log.getPayerId());
        assertEquals(BigDecimal.valueOf(1000), log.getAmount());
        assertEquals(paymentDate, log.getPaymentDate());
    }


    @Test
    void shouldUpdateCreditPaymentLogUsingSetters() {

        LocalDateTime paymentDate = LocalDateTime.now();

        CreditPaymentLog log = new CreditPaymentLog();

        log.setId("3");
        log.setCreditId("CR003");
        log.setPayerId("P003");
        log.setAmount(BigDecimal.valueOf(1500));
        log.setPaymentDate(paymentDate);


        assertAll(
                () -> assertEquals("3", log.getId()),
                () -> assertEquals("CR003", log.getCreditId()),
                () -> assertEquals("P003", log.getPayerId()),
                () -> assertEquals(
                        BigDecimal.valueOf(1500),
                        log.getAmount()
                ),
                () -> assertEquals(paymentDate, log.getPaymentDate())
        );
    }


    @Test
    void shouldSupportEqualsAndHashCode() {

        CreditPaymentLog log1 = CreditPaymentLog.builder()
                .id("1")
                .creditId("CR001")
                .payerId("P001")
                .amount(BigDecimal.valueOf(500))
                .build();


        CreditPaymentLog log2 = CreditPaymentLog.builder()
                .id("1")
                .creditId("CR001")
                .payerId("P001")
                .amount(BigDecimal.valueOf(500))
                .build();


        assertEquals(log1, log2);
        assertEquals(log1.hashCode(), log2.hashCode());
    }


    @Test
    void shouldGenerateToString() {

        CreditPaymentLog log = CreditPaymentLog.builder()
                .id("1")
                .creditId("CR001")
                .payerId("P001")
                .build();


        String result = log.toString();


        assertTrue(result.contains("CR001"));
        assertTrue(result.contains("P001"));
    }
}