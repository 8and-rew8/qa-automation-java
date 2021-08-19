package com.tinkoff.edu.app;

/**
 * Logic for loan request
 */
public class LoanCalcService {
    /**
     * Loan calculation
     *
     * @param loanRequest
     */
    public LoanResponse createRequest(LoanRequest loanRequest) {
        //TODO more data processing
        LoanResponse loanResponse = new LoanCalcDao().save();
        if (loanRequest.getType() != ClientType.PERSON) {
            loanResponse.setResponseType(LoanResponseType.DENIED);
        } else {
            loanResponse.setResponseType(LoanResponseType.APPROVED);
        }

        return loanResponse;
    }
}
