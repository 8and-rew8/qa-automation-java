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

        LoanCalcLogger.log(loanCalcService.createRequest(loanRequest));

        return loanCalcService.createRequest(loanRequest);
    }
}
