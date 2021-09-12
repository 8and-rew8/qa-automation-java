package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.LoanResponseType;
import com.tinkoff.edu.app.interfaces.LoanCalcService;

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
    public LoanResponse createRequest(LoanRequest loanRequest) throws RuntimeException, BusinessRulesException {
        try {
            loanCalcService.validationRequest(loanRequest);
            LoanResponse loanResponse = loanCalcService.createRequest(loanRequest);
            LoanCalcLogger.log(loanResponse);
            return loanResponse;
        } catch (NullPointerException e) {
            throw new NullPointerException("npe");
        } catch (BusinessRulesException e) {
            throw new BusinessRulesException("request validation failed", e);
        }
    }
}
