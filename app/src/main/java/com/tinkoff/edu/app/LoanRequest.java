package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ClientType;

/**
 * loan request instance
 */
public class LoanRequest {
    private ClientType type;
    private int months;
    private double amount;
    private String fio;

    public LoanRequest(ClientType type, int months, double amount, String fio) {
        this.type = type;
        this.months = months;
        this.amount = amount;
        this.fio = fio;
    }

    public LoanRequest() {
    }

    public String getFio() {
        return fio;
    }

    public ClientType getType() {
        return type;
    }

    public int getMonths() {
        return months;
    }

    public double getAmount() {
        return amount;
    }
}
