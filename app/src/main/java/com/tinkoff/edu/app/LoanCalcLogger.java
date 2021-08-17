package com.tinkoff.edu.app;

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
        System.out.println("Created request with ID " + loanResponse.getRequestId());
    }
}
