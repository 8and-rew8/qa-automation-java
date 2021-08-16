package com.tinkoff.edu.app;

/**
 * logging info about loan request
 */
public class LoanCalcLogger {
    /**
     * out info in command line
     * @param requestId
     */
    public static void log(int requestId) {
        System.out.println("created request with ID " + requestId);
    }
}
