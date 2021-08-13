package com.tinkoff.edu.app;

/**
 * Creating new loan-request controller
 */

public class LoanCalcController {
    /**
     * Validate and logs request
     */
    public static int createRequest() {
        int requestId = LoanCalcService.createRequest();
        LoanCalcLogger.log(requestId);

        return requestId;
    }
}
