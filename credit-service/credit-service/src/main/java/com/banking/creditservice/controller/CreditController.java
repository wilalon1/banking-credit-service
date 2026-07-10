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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * REST controller responsible for exposing services related to

 credit management.

 * Enables CRUD operations for credits, customer queries,

 credit card validations, and payment recording.
 *
 * Base URL:
 * /api/credits
 */

@Tag(
        name = "Credits",
        description = "Credit-related transactions"
)
@RestController
@RequestMapping("/api/credits")
public class CreditController {

    private final CreditService service;

    /**
     * Controller constructor.
     *
     * *
     * * @param service service responsible for handling the business logic
     *
     * * related to credits.
     */
    public CreditController(CreditService service) {
        this.service = service;
    }

    /**
     * Register a new loan.
     *
     * @param credit information about the loan to be registered.
     * @return loan created reactively.
     */
    @Operation(
            summary = "Create credit",
            description = "Register a new credit for a customer"
    )
    @PostMapping
    public Single<Credit> create(@RequestBody Credit credit) {
        return service.create(credit);
    }

    /**
     * Retrieves all registered credits.
     *
     * @return reactive flow with the credit list.
     */
    @GetMapping
    public Observable<Credit> findAll() {
        return service.findAll();
    }

    /**
     * Searches for a loan by its identifier.
     *
     * @param id Identifier of the loan.
     * @return Loan found, or empty if it doesn't exist.
     */
    @GetMapping("/{id}")
    public Maybe<Credit> findById(@PathVariable String id) {
        return service.findById(id);
    }

    /**
     * Retrieves all credits associated with a customer.
     * @param customerId Customer identifier.
     * @return Reactive customer credit flow.
     */
    @GetMapping("/customer/{customerId}")
    public Observable<Credit> findByCustomerId(@PathVariable String customerId) {
        return service.findByCustomerId(customerId);
    }

    /**
     * Updates the information of an existing loan.
     *
     * @param id Loan identifier.
     * @param credit Updated loan details.
     * @return Updated loan details.
     */
    @PutMapping("/{id}")
    public Single<Credit> update(@PathVariable String id, @RequestBody Credit credit) {
        return service.update(id, credit);
    }

    /**
     * Delete a credit using its identifier.
     *
     * @param id identifier of the credit to delete.
     * @return reactive deletion operation.
     */
    @DeleteMapping("/{id}")
    public Completable delete(@PathVariable String id) {
        return service.delete(id);
    }

    /**
     * Checks if a customer has an active credit card.
     * @param customerId customer identifier.
     * @return true if they have a credit card, otherwise false.
     */
    @GetMapping("/cards/customer/{customerId}")
    public Single<Boolean> hasCreditCard(
            @PathVariable String customerId) {

        return service.hasCreditCard(customerId);
    }

    /**
     * Check if a customer has overdue debts.
     *
     * @param customerId customer identifier.
     * @return HTTP response with the overdue debt indicator.
     */
    @GetMapping("/overdue/{customerId}")
    public ResponseEntity<Map<String, Boolean>> hasOverdue(@PathVariable String customerId) {
        boolean hasOverdue = service.hasOverdueDebt(customerId);
        return ResponseEntity.ok(Collections.singletonMap("overdue", hasOverdue));
    }


    /**
     * Registra un pago asociado a un crédito.
     *
     * @param creditId identificador del crédito.
     * @param request información del pago (pagador y monto).
     * @return mensaje indicando el resultado del registro.
     */
    @PostMapping("/{creditId}/payments")
    public ResponseEntity<String> makePayment(
            @PathVariable String creditId,
            @RequestBody CreditPaymentRequest request) {
        service.registrarPago(creditId, request.getPayerId(), request.getAmount());
        return ResponseEntity.ok("Payment made successfully.");
    }


}