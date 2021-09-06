package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ClientType;

/**
 * loan request instance
 */
public class LoanRequest {
    private ClientType type;
    private int months;
    private int amount;
    private String fio;

    public LoanRequest(ClientType type, int months, int amount, String fio) {
        this.type = type;
        this.months = months;
        this.amount = amount;
        this.fio = fio;
    }

    public LoanRequest() {
    }

    public ClientType getType() {
        return type;
    }

    public int getMonths() {
        return months;
    }

    public int getAmount() {
        return amount;
    }
}
