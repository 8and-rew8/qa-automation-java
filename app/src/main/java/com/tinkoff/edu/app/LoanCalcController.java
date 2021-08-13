package com.tinkoff.edu.app;

/**
 *
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
