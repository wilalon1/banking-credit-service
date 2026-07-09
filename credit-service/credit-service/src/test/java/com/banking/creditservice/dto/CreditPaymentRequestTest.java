package com.banking.creditservice.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreditPaymentRequestTest {

    @Test
    void shouldSetAndGetPayerId() {

        CreditPaymentRequest request = new CreditPaymentRequest();

        request.setPayerId("P001");

        assertEquals("P001", request.getPayerId());
    }


    @Test
    void shouldSetAndGetAmount() {

        CreditPaymentRequest request = new CreditPaymentRequest();

        BigDecimal amount = BigDecimal.valueOf(500);

        request.setAmount(amount);

        assertEquals(amount, request.getAmount());
    }


    @Test
    void shouldCreateRequestWithAllValues() {

        CreditPaymentRequest request = new CreditPaymentRequest();

        request.setPayerId("P001");
        request.setAmount(BigDecimal.valueOf(1000));


        assertAll(
                () -> assertNotNull(request),
                () -> assertEquals("P001", request.getPayerId()),
                () -> assertEquals(
                        BigDecimal.valueOf(1000),
                        request.getAmount()
                )
        );
    }
}