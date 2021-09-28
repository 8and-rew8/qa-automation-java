package com.tinkoff.edu.app;

import com.tinkoff.edu.app.interfaces.LoanCalcService;

import java.io.IOException;

/**
 * Creating new loan-request controller
 */
public class LoanCalcController {
    private final LoanCalcService loanCalcService;

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
    public LoanResponse createRequest(LoanRequest loanRequest) throws RuntimeException, BusinessRulesException, IOException {
        try {
            loanCalcService.validationRequest(loanRequest);
            LoanResponse loanResponse = loanCalcService.createRequest(loanRequest);
            LoanCalcLogger.log(loanResponse);
            return loanResponse;
        } catch (NullPointerException e) {
            throw new NullPointerException("request with null argument");
        } catch (BusinessRulesException e) {
            throw new BusinessRulesException("request validation failed", e);
        }
    }
}
