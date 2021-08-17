package com.tinkoff.edu.app;

public class LoanResponse {
    private int requestId;
    private LoanResponseType responseType;

    public LoanResponse() {
        this.requestId++;
    }

    public void setResponseType(LoanResponseType responseType) {
        this.responseType = responseType;
    }

    public int getRequestId() {
        return requestId;
    }

    public String toString() {
        return "Answer for request with ID "
                + this.requestId
                + " is "
                + this.responseType;
    }
}
