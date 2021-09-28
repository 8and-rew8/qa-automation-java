package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.LoanResponseType;

import java.util.UUID;

/**
 * loan response instance
 */
public class LoanResponse {
    private final UUID requestUUID;
    private LoanResponseType responseType;

    public LoanResponse() {
        this.requestUUID = UUID.randomUUID();
    }

    public String getRequestUUID() {
        return requestUUID.toString();
    }

    public LoanResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(LoanResponseType responseType) {
        this.responseType = responseType;
    }

    @Override
    public String toString() {
        return requestUUID +
                "," + responseType;
    }
}
