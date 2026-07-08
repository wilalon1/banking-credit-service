package com.banking.creditservice.repository;


import com.banking.creditservice.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CreditRepository extends ReactiveMongoRepository<Credit, String> {

    Flux<Credit> findByCustomerId(String customerId);


    Flux<Credit> findByCustomerIdAndType(
            String customerId,
            String type
    );

    List<Credit> findByCustomerIdAndStatus(String customerId, String status);

}