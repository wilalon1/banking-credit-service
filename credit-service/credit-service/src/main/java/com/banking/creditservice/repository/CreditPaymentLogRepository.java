package com.banking.creditservice.repository;

import com.banking.creditservice.model.CreditPaymentLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CreditPaymentLogRepository extends ReactiveMongoRepository<CreditPaymentLog, String> {


    Flux<CreditPaymentLog> findByCreditId(String creditId);

    Flux<CreditPaymentLog> findByPayerId(String payerId);


}
