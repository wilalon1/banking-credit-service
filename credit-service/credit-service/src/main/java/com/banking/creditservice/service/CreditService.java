package com.banking.creditservice.service;

import com.banking.creditservice.model.Credit;
import com.banking.creditservice.model.CreditPaymentLog;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.math.BigDecimal;

public interface CreditService {

    Single<Credit> create(Credit credit);

    Observable<Credit> findAll();

    Maybe<Credit> findById(String id);

    Observable<Credit> findByCustomerId(String customerId);

    Single<Credit> update(String id, Credit credit);

    Completable delete(String id);

    Single<Boolean> hasCreditCard(String customerId);

    boolean hasOverdueDebt(String customerId);

    Single<CreditPaymentLog> registrarPago(String creditId, String payerId, BigDecimal amount);


}