package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.enums.LoanResponseType;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;
import com.tinkoff.edu.app.interfaces.LoanCalcService;

/**
 * Logic for loan request
 */
public class DefaultLoanCalcService implements LoanCalcService {
    private final LoanCalcRepo repo;

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
        if (((loanRequest.getType() == ClientType.PERSON) & (loanRequest.getAmount() <= 10_000.0) &
                (loanRequest.getAmount() > 0) & (loanRequest.getMonths() <= 12) & (loanRequest.getMonths() > 0)) |
                    ((loanRequest.getType() == ClientType.OOO) & (loanRequest.getAmount() > 10_000.0) &
                        (loanRequest.getMonths() < 12) & (loanRequest.getMonths() > 0))) {
            loanResponse.setResponseType(LoanResponseType.APPROVED);
        } else {
            loanResponse.setResponseType(LoanResponseType.DENIED);
            loanResponse.setRequestId(-1);
        }

        return loanResponse;
    }
}
