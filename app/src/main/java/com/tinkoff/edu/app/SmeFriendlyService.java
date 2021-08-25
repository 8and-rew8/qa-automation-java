package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.enums.LoanResponseType;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;
import com.tinkoff.edu.app.interfaces.LoanCalcService;

public class SmeFriendlyService implements LoanCalcService {
    private LoanCalcRepo repo;

    /**
     * Constructor DI
     * @param repo
     */
    public SmeFriendlyService(LoanCalcRepo repo) {
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
        if (loanRequest.getType() == ClientType.PERSON) {
            loanResponse.setResponseType(LoanResponseType.DENIED);
        } else {
            if (loanRequest.getType() == ClientType.IP) {
                loanResponse.setResponseType(LoanResponseType.APPROVED);
            }
            else {
                loanResponse.setResponseType(LoanResponseType.NEED_INFO);
            }
        }

        return loanResponse;
    }
}
