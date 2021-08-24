package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ClientType;

/**
 * loan request instance
 */
public class LoanRequest {
    private final ClientType type;
    private final int months;
    private final int amount;

    public LoanRequest(ClientType type, int months, int amount) {
        this.type = type;
        this.months = months;
        this.amount = amount;
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
