package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.LoanResponseType;

/**
 * logging info about loan request
 */
public class LoanCalcLogger {

    /**
     * out info in command line
     *
     * @param loanResponse
     */
    public static void log(LoanResponse loanResponse) {
        int requestId = loanResponse.getRequestId();
        LoanResponseType loanResponseType = loanResponse.getResponseType();

        if (requestId == -1) {
            System.out.println("Had error with request ID " + requestId + " and response type " + loanResponseType);
        } else {
            System.out.println("Created request with ID " + requestId + " and response type " + loanResponseType);
        }
    }
}
