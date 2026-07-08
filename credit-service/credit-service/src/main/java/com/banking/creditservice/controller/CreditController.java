package com.banking.creditservice.controller;

import com.banking.creditservice.dto.CreditPaymentRequest;
import com.banking.creditservice.model.Credit;
import com.banking.creditservice.service.CreditService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

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

    @GetMapping("/overdue/{customerId}")
    public ResponseEntity<Map<String, Boolean>> hasOverdue(@PathVariable String customerId) {
        boolean hasOverdue = service.hasOverdueDebt(customerId);
        return ResponseEntity.ok(Collections.singletonMap("overdue", hasOverdue));
    }

    @PostMapping("/{creditId}/payments")
    public ResponseEntity<String> makePayment(
            @PathVariable String creditId,
            @RequestBody CreditPaymentRequest request) {
        service.registrarPago(creditId, request.getPayerId(), request.getAmount());
        return ResponseEntity.ok("Pago realizado correctamente.");
    }


}