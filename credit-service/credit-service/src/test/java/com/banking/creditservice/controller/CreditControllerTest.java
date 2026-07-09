package com.banking.creditservice.controller;

import com.banking.creditservice.dto.CreditPaymentRequest;
import com.banking.creditservice.model.Credit;
import com.banking.creditservice.model.CreditPaymentLog;
import com.banking.creditservice.service.CreditService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditControllerTest {

    @Mock
    private CreditService service;

    @InjectMocks
    private CreditController controller;

    private Credit credit;

    @BeforeEach
    void setUp() {
        credit = new Credit();
        credit.setId("1");
        credit.setCustomerId("C001");
    }

    @Test
    void shouldCreateCredit() {

        when(service.create(credit)).thenReturn(Single.just(credit));

        Credit result = controller.create(credit).blockingGet();

        assertNotNull(result);
        assertEquals(credit, result);

        verify(service).create(credit);
    }

    @Test
    void shouldFindAllCredits() {

        when(service.findAll()).thenReturn(Observable.just(credit));

        List<Credit> result = controller.findAll()
                .toList()
                .blockingGet();

        assertEquals(1, result.size());
        assertEquals(credit, result.get(0));

        verify(service).findAll();
    }

    @Test
    void shouldFindCreditById() {

        when(service.findById("1")).thenReturn(Maybe.just(credit));

        Credit result = controller.findById("1").blockingGet();

        assertNotNull(result);
        assertEquals(credit, result);

        verify(service).findById("1");
    }

    @Test
    void shouldFindCreditsByCustomerId() {

        when(service.findByCustomerId("C001"))
                .thenReturn(Observable.just(credit));

        List<Credit> result = controller.findByCustomerId("C001")
                .toList()
                .blockingGet();

        assertEquals(1, result.size());
        assertEquals(credit, result.get(0));

        verify(service).findByCustomerId("C001");
    }

    @Test
    void shouldUpdateCredit() {

        when(service.update("1", credit))
                .thenReturn(Single.just(credit));

        Credit result = controller.update("1", credit).blockingGet();

        assertNotNull(result);
        assertEquals(credit, result);

        verify(service).update("1", credit);
    }

    @Test
    void shouldDeleteCredit() {

        when(service.delete("1"))
                .thenReturn(Completable.complete());

        assertDoesNotThrow(() ->
                controller.delete("1").blockingAwait());

        verify(service).delete("1");
    }

    @Test
    void shouldReturnTrueWhenCustomerHasCreditCard() {

        when(service.hasCreditCard("C001"))
                .thenReturn(Single.just(true));

        Boolean result = controller.hasCreditCard("C001")
                .blockingGet();

        assertTrue(result);

        verify(service).hasCreditCard("C001");
    }

    @Test
    void shouldReturnOverdueDebtResponse() {

        when(service.hasOverdueDebt("C001"))
                .thenReturn(true);

        ResponseEntity<Map<String, Boolean>> response =
                controller.hasOverdue("C001");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().get("overdue"));

        verify(service).hasOverdueDebt("C001");
    }

    @Test
    void shouldRegisterPayment() {

        CreditPaymentRequest request = new CreditPaymentRequest();
        request.setPayerId("P001");
        request.setAmount(BigDecimal.valueOf(500));

        CreditPaymentLog paymentLog = CreditPaymentLog.builder()
                .creditId("1")
                .payerId("P001")
                .amount(BigDecimal.valueOf(500))
                .build();

        when(service.registrarPago(
                eq("1"),
                eq("P001"),
                eq(BigDecimal.valueOf(500))
        )).thenReturn(Single.just(paymentLog));


        ResponseEntity<String> response =
                controller.makePayment("1", request);


        assertEquals(200, response.getStatusCode().value());
        assertEquals("Payment made successfully.", response.getBody());


        verify(service).registrarPago(
                eq("1"),
                eq("P001"),
                eq(BigDecimal.valueOf(500))
        );
    }
}