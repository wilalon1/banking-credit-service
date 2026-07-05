package com.banking.creditservice.controller;

import com.banking.creditservice.model.Credit;
import com.banking.creditservice.service.CreditService;
import io.reactivex.rxjava3.core.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credits")
public class CreditController {

    private final CreditService service;

    public CreditController(CreditService service) {
        this.service = service;
    }

    @PostMapping
    public Single<Credit> create(@RequestBody Credit credit) {
        return service.create(credit);
    }

    @GetMapping
    public Observable<Credit> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Maybe<Credit> findById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping("/customer/{customerId}")
    public Observable<Credit> findByCustomerId(@PathVariable String customerId) {
        return service.findByCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public Single<Credit> update(@PathVariable String id, @RequestBody Credit credit) {
        return service.update(id, credit);
    }

    @DeleteMapping("/{id}")
    public Completable delete(@PathVariable String id) {
        return service.delete(id);
    }

    @GetMapping("/cards/customer/{customerId}")
    public Single<Boolean> hasCreditCard(
            @PathVariable String customerId) {

        return service.hasCreditCard(customerId);
    }
}