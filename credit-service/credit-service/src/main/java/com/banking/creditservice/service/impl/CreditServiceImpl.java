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

/**
 * Implementation of the credit management service.
 *
 * Provides credit CRUD operations, business validations,
 and payment history logging using Reactive MongoDB and RxJava.
 */
@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    /**
     * Credit repository.
     */
    private final CreditRepository repository;
    /**
     * Payment history repository.
     */
    @Autowired
    private CreditPaymentLogRepository paymentLogRepository;

    /**
     * Register a new credit.
     *
     * @param credit credit information.
     * @return registered credit.
     */

    @Override
    public Single<Credit> create(Credit credit) {
        return Single.fromPublisher(repository.save(credit));
    }

    /**
     * Retrieves all registered credits.
     *
     * @return reactive credit list.
     */

    @Override
    public Observable<Credit> findAll() {
        return Observable.fromPublisher(repository.findAll());
    }

    /**
     * Busca un crédito por su identificador.
     *
     * @param id identificador del crédito.
     * @return crédito encontrado.
     */

    @Override
    public Maybe<Credit> findById(String id) {
        return Maybe.fromPublisher(repository.findById(id));
    }
    /**
     * Retrieves the credits associated with a customer.
     *
     * @param customerId customer identifier.
     * @return customer credits.
     */
    @Override
    public Observable<Credit> findByCustomerId(String customerId) {
        return Observable.fromPublisher(repository.findByCustomerId(customerId));
    }
    /**
     * Updates loan information.
     *
     * @param id loan identifier.
     * @param credit updated information.
     * @return updated credit information.
     */
    @Override
    public Single<Credit> update(String id, Credit credit) {
        credit.setId(id);

        return Single.fromPublisher(
                repository.save(credit)
        );
    }
    /**
     * Deletes a credit by its identifier.
     *
     * @param id Identifier of the credit.
     * @return Reactive deletion operation.
     */
    @Override
    public Completable delete(String id) {
        return Completable.fromPublisher(
                repository.deleteById(id)
        );
    }
    /**
     * Checks if a customer has a credit card.
     * Uses a circuit breaker to prevent failures in case of
     * service unavailability or prolonged wait times.
     * @param customerId customer identifier.
     * @return true if the customer has a credit card.
     */
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

    /**
     * Circuit Breaker Fallback Method
     *
     * Executes when an error or timeout occurs during
     credit card validation.
     * @param customerId Customer identifier.
     * @param ex Exception thrown.
     * @return false Default.
     */

    public Single<Boolean> fallbackHasCreditCard(String customerId, Throwable ex) {
        System.out.println("Credit service fallback: " + ex.getMessage());
        return Single.just(false);
    }
    /**
     * Checks if the customer has any overdue credits.
     * @param customerId customer identifier.
     * @return true if at least one overdue credit exists.
     */
    public boolean hasOverdueDebt(String customerId) {
        return !repository.findByCustomerIdAndStatus(customerId, "VENCIDA").isEmpty();
    }


    /**
     * Records a payment made against a loan.
     * The payment is stored in the payment history collection.
     * @param creditId Loan identifier.
     * @param payerId Payer identifier.
     * @param amount Amount paid.
     * @return Record of payment stored.
     */
    public Single<CreditPaymentLog> registrarPago(String creditId, String payerId, BigDecimal amount) {
        CreditPaymentLog log = CreditPaymentLog.builder()
                .creditId(creditId)
                .payerId(payerId)
                .amount(amount)
                .paymentDate(LocalDateTime.now())
                .build();


        return RxJava3Adapter.monoToSingle(paymentLogRepository.save(log));
    }
    /**
     * Retrieves the payment history for a loan.
     *
     * @param creditId loan identifier.
     * @return recorded payment history.
     */
    public Flowable<CreditPaymentLog> creditPaymentHistory(String creditId) {
        return RxJava3Adapter.fluxToFlowable(paymentLogRepository.findByCreditId(creditId));
    }
}