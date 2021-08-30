package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.enums.LoanResponseType;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;
import com.tinkoff.edu.app.interfaces.LoanCalcService;

import java.util.Arrays;
import java.util.Objects;

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
     * validate request for non-valid inputs
     *
     * @param loanRequest
     * @return Error code
     * @throws RuntimeException
     */
    public int validationRequest(LoanRequest loanRequest) throws RuntimeException {
        try {
            boolean isPresent = Arrays.stream(ClientType.values()).anyMatch(element ->
                    Objects.equals(element.getType(), loanRequest.getType().toString()));
            if ((loanRequest.getAmount() <= 0) || (loanRequest.getMonths() <= 0) || (!isPresent)) {
                return -1;
            }
            else return 0;
        } catch (NullPointerException e) {
            return -1;
        }
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
        if (loanRequest.getType() == ClientType.PERSON){
            if ((loanRequest.getAmount() <= 10_000.0) & (loanRequest.getMonths() <= 12)) {
                loanResponse.setResponseType(LoanResponseType.APPROVED);
            } else {
                loanResponse.setResponseType(LoanResponseType.DENIED);
                loanResponse.setRequestId(-1);
            }
        } else if (loanRequest.getType() == ClientType.OOO){
            if ((loanRequest.getAmount() > 10_000.0) & (loanRequest.getMonths() < 12)) {
                loanResponse.setResponseType(LoanResponseType.APPROVED);
            } else {
                loanResponse.setResponseType(LoanResponseType.DENIED);
                loanResponse.setRequestId(-1);
            }
        } else {
            loanResponse.setResponseType(LoanResponseType.DENIED);
            loanResponse.setRequestId(-1);
        }

        return loanResponse;
    }
}
