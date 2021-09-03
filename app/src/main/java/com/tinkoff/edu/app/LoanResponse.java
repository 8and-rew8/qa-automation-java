package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.LoanResponseType;

import java.util.UUID;

/**
 * loan response instance
 */
public class LoanResponse {
    private int creationFlag;
    private UUID requestUUID;
    private LoanResponseType responseType;

    public LoanResponse() {
        this.creationFlag++;
        this.requestUUID = UUID.randomUUID();
    }

    public String getRequestUUID() {
        return requestUUID.toString();
    }

    public int getCreationFlag() {
        return creationFlag;
    }

    public void setCreationFlag(int creationFlag) {
        this.creationFlag = creationFlag;
    }

    public LoanResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(LoanResponseType responseType) {
        this.responseType = responseType;
    }
}
