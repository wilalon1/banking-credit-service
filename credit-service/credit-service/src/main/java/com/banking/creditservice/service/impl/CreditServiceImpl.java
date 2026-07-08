package com.banking.creditservice.service.impl;

import com.banking.creditservice.model.Credit;
import com.banking.creditservice.model.CreditPaymentLog;
import com.banking.creditservice.repository.CreditPaymentLogRepository;
import com.banking.creditservice.repository.CreditRepository;
import com.banking.creditservice.service.CreditService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.adapter.rxjava.RxJava2Adapter;
import reactor.adapter.rxjava.RxJava3Adapter;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository repository;
    @Autowired
    private CreditPaymentLogRepository paymentLogRepository;

    @Override
    public Single<Credit> create(Credit credit) {
        return Single.fromPublisher(repository.save(credit));
    }

    @Override
    public Observable<Credit> findAll() {
        return Observable.fromPublisher(repository.findAll());
    }

    @Override
    public Maybe<Credit> findById(String id) {
        return Maybe.fromPublisher(repository.findById(id));
    }

    @Override
    public Observable<Credit> findByCustomerId(String customerId) {
        return Observable.fromPublisher(repository.findByCustomerId(customerId));
    }

    @Override
    public Single<Credit> update(String id, Credit credit) {
        credit.setId(id);

        return Single.fromPublisher(
                repository.save(credit)
        );
    }

    @Override
    public Completable delete(String id) {
        return Completable.fromPublisher(
                repository.deleteById(id)
        );
    }

    @Override
    @CircuitBreaker(name = "creditService", fallbackMethod = "fallbackHasCreditCard")
    public Single<Boolean> hasCreditCard(String customerId) {

        return Single.fromPublisher(

                repository
                        .findByCustomerId(customerId)
                        .filter(c ->
                                "CREDIT_CARD".equals(c.getType())
                        )
                        .hasElements()
                        .timeout(Duration.ofSeconds(2))

        );

    }

    public Single<Boolean> fallbackHasCreditCard(String customerId, Throwable ex) {
        System.out.println("Credit service fallback: " + ex.getMessage());
        return Single.just(false);
    }

    public boolean hasOverdueDebt(String customerId) {
        return !repository.findByCustomerIdAndStatus(customerId, "VENCIDA").isEmpty();
    }



    public Single<CreditPaymentLog> registrarPago(String creditId, String payerId, BigDecimal amount) {
        CreditPaymentLog log = CreditPaymentLog.builder()
                .creditId(creditId)
                .payerId(payerId)
                .amount(amount)
                .paymentDate(LocalDateTime.now())
                .build();


        return RxJava3Adapter.monoToSingle(paymentLogRepository.save(log));
    }

    public Flowable<CreditPaymentLog> creditPaymentHistory(String creditId) {
        return RxJava3Adapter.fluxToFlowable(paymentLogRepository.findByCreditId(creditId));
    }
}