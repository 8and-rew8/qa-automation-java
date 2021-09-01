package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.LoanResponseType;

/**
 * loan response instance
 */
public class LoanResponse {
    private int requestId;
    private LoanResponseType responseType;

    public LoanResponse() {
        this.requestId++;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public LoanResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(LoanResponseType responseType) {
        this.responseType = responseType;
    }
}
