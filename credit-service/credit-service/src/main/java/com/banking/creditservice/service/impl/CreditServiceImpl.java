package com.banking.creditservice.service.impl;

import com.banking.creditservice.model.Credit;
import com.banking.creditservice.repository.CreditRepository;
import com.banking.creditservice.service.CreditService;
import io.reactivex.rxjava3.core.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository repository;

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
}