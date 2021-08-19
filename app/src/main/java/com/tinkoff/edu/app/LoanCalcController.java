package com.tinkoff.edu.app;

/**
 * Creating new loan-request controller
 */
public class LoanCalcController {
    /**
     * Validate and logs request
     *
     * @param loanRequest
     */
    public LoanResponse createRequest(LoanRequest loanRequest) {

        LoanResponse loanResponse = new LoanCalcService().createRequest(loanRequest);
        LoanCalcLogger.log(loanResponse);

        return loanResponse;
    }
}
