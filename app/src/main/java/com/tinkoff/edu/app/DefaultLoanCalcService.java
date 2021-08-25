package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.enums.LoanResponseType;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;
import com.tinkoff.edu.app.interfaces.LoanCalcService;

/**
 * Logic for loan request
 */
public class DefaultLoanCalcService implements LoanCalcService {
    private LoanCalcRepo repo;

    /**
     * Constructor DI
     *
     * @param repo
     */
    public DefaultLoanCalcService(LoanCalcRepo repo) {
        this.repo = repo;
    }

    /**
     * Loan calculation
     *
     * @param loanRequest
     */
    @Override
    public LoanResponse createRequest(LoanRequest loanRequest) {
        //TODO more data processing
        LoanResponse loanResponse = repo.save();
        if (loanRequest.getType() != ClientType.PERSON) {
            loanResponse.setResponseType(LoanResponseType.DENIED);
        } else {
            loanResponse.setResponseType(LoanResponseType.APPROVED);
        }

        return loanResponse;
    }
}
