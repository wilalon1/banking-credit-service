package com.banking.creditservice.service;

import com.banking.creditservice.model.Credit;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.core.Single;
public interface CreditService {

    Single<Credit> create(Credit credit);

    Observable<Credit> findAll();

    Maybe<Credit> findById(String id);

    Observable<Credit> findByCustomerId(String customerId);

    Single<Credit> update(String id, Credit credit);

    Completable delete(String id);

    Single<Boolean> hasCreditCard(String customerId);
}