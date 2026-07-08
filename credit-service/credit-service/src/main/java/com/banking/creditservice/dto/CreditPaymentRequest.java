package com.banking.creditservice.dto;

import java.math.BigDecimal;

public class CreditPaymentRequest {
    private String payerId;
    private BigDecimal amount;

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}