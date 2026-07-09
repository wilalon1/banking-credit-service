package com.banking.creditservice.service.impl;

import com.banking.creditservice.model.Credit;
import com.banking.creditservice.model.CreditPaymentLog;
import com.banking.creditservice.repository.CreditPaymentLogRepository;
import com.banking.creditservice.repository.CreditRepository;
import io.reactivex.rxjava3.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CreditServiceImplTest {


    @Mock
    private CreditRepository repository;


    @Mock
    private CreditPaymentLogRepository paymentLogRepository;


    @InjectMocks
    private CreditServiceImpl service;


    private Credit credit;


    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(
                service,
                "paymentLogRepository",
                paymentLogRepository
        );


        credit = Credit.builder()
                .id("1")
                .customerId("C001")
                .type("PERSONAL")
                .amount(BigDecimal.valueOf(10000))
                .balance(BigDecimal.valueOf(5000))
                .status("VIGENTE")
                .build();
    }


    @Test
    void shouldCreateCredit() {

        when(repository.save(any(Credit.class)))
                .thenReturn(Mono.just(credit));


        Credit result =
                service.create(credit)
                        .blockingGet();


        assertNotNull(result);
        assertEquals("1", result.getId());

        verify(repository)
                .save(credit);
    }


    @Test
    void shouldFindAllCredits() {

        when(repository.findAll())
                .thenReturn(Flux.just(credit));


        List<Credit> result =
                service.findAll()
                        .toList()
                        .blockingGet();


        assertEquals(1, result.size());

        verify(repository)
                .findAll();
    }


    @Test
    void shouldFindCreditById() {

        when(repository.findById("1"))
                .thenReturn(Mono.just(credit));


        Credit result =
                service.findById("1")
                        .blockingGet();


        assertEquals("1", result.getId());

        verify(repository)
                .findById("1");
    }


    @Test
    void shouldFindCreditsByCustomerId() {

        when(repository.findByCustomerId("C001"))
                .thenReturn(Flux.just(credit));


        List<Credit> result =
                service.findByCustomerId("C001")
                        .toList()
                        .blockingGet();


        assertEquals(1, result.size());

        verify(repository)
                .findByCustomerId("C001");
    }


    @Test
    void shouldUpdateCredit() {

        when(repository.save(any(Credit.class)))
                .thenReturn(Mono.just(credit));


        Credit result =
                service.update("10", credit)
                        .blockingGet();


        assertEquals("10", result.getId());

        verify(repository)
                .save(credit);
    }


    @Test
    void shouldDeleteCredit() {

        when(repository.deleteById("1"))
                .thenReturn(Mono.empty());


        assertDoesNotThrow(() ->
                service.delete("1")
                        .blockingAwait()
        );


        verify(repository)
                .deleteById("1");
    }


    @Test
    void shouldReturnTrueWhenHasCreditCard() {

        Credit creditCard = Credit.builder()
                .id("2")
                .customerId("C001")
                .type("CREDIT_CARD")
                .build();


        when(repository.findByCustomerId("C001"))
                .thenReturn(Flux.just(creditCard));


        Boolean result =
                service.hasCreditCard("C001")
                        .blockingGet();


        assertTrue(result);
    }


    @Test
    void shouldReturnFalseWhenNoCreditCard() {

        when(repository.findByCustomerId("C001"))
                .thenReturn(Flux.just(credit));


        Boolean result =
                service.hasCreditCard("C001")
                        .blockingGet();


        assertFalse(result);
    }


    @Test
    void shouldExecuteFallbackHasCreditCard() {

        Boolean result =
                service.fallbackHasCreditCard(
                                "C001",
                                new RuntimeException("Error")
                        )
                        .blockingGet();


        assertFalse(result);
    }


    @Test
    void shouldReturnTrueWhenHasOverdueDebt() {

        when(repository.findByCustomerIdAndStatus(
                "C001",
                "VENCIDA"
        ))
                .thenReturn(List.of(credit));


        boolean result =
                service.hasOverdueDebt("C001");


        assertTrue(result);
    }


    @Test
    void shouldReturnFalseWhenNoOverdueDebt() {

        when(repository.findByCustomerIdAndStatus(
                "C001",
                "VENCIDA"
        ))
                .thenReturn(Collections.emptyList());


        boolean result =
                service.hasOverdueDebt("C001");


        assertFalse(result);
    }


    @Test
    void shouldRegisterPayment() {

        CreditPaymentLog log =
                CreditPaymentLog.builder()
                        .id("P001")
                        .creditId("1")
                        .payerId("USER1")
                        .amount(BigDecimal.valueOf(500))
                        .paymentDate(LocalDateTime.now())
                        .build();


        when(paymentLogRepository.save(any(CreditPaymentLog.class)))
                .thenReturn(Mono.just(log));


        CreditPaymentLog result =
                service.registrarPago(
                                "1",
                                "USER1",
                                BigDecimal.valueOf(500)
                        )
                        .blockingGet();


        assertNotNull(result);
        assertEquals("1", result.getCreditId());
        assertEquals("USER1", result.getPayerId());

        verify(paymentLogRepository)
                .save(any(CreditPaymentLog.class));
    }


    @Test
    void shouldReturnPaymentHistory() {

        CreditPaymentLog log =
                CreditPaymentLog.builder()
                        .id("1")
                        .creditId("CR001")
                        .payerId("P001")
                        .amount(BigDecimal.valueOf(100))
                        .build();


        when(paymentLogRepository.findByCreditId("CR001"))
                .thenReturn(Flux.just(log));


        List<CreditPaymentLog> result =
                service.creditPaymentHistory("CR001")
                        .toList()
                        .blockingGet();


        assertEquals(1, result.size());
        assertEquals(
                "CR001",
                result.get(0).getCreditId()
        );


        verify(paymentLogRepository)
                .findByCreditId("CR001");
    }
}