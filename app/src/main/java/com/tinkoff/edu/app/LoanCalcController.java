package com.tinkoff.edu.app;

/**
 * Creating new loan-request controller
 */
public class LoanCalcController {
    private LoanCalcService loanCalcService;

    /**
     * Constructor DI
     *
     * @param loanCalcService
     */
    public LoanCalcController(LoanCalcService loanCalcService) {
        this.loanCalcService = loanCalcService;
    }

    /**
     * Validate and logs request
     *
     * @param loanRequest
     */
    public LoanResponse createRequest(LoanRequest loanRequest) {

        LoanResponse loanResponse = loanCalcService.createRequest(loanRequest);
        LoanCalcLogger.log(loanResponse);

        return loanResponse;
    }
}
