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
    public LoanResponse validationRequest(LoanRequest loanRequest) throws RuntimeException {
        LoanResponse loanResponse = new LoanResponse();
        try {
            boolean isPresent = Arrays.stream(ClientType.values()).anyMatch(element ->
                    Objects.equals(element.getType(), loanRequest.getType().toString()));
            if ((loanRequest.getAmount() <= 0) || (loanRequest.getMonths() <= 0) || (!isPresent)) {
                loanResponse.setCreationFlag(-1);
                return loanResponse;
            } else {
                loanResponse.setCreationFlag(0);
            }
            return loanResponse;
        } catch (NullPointerException e) {
            loanResponse.setCreationFlag(-1);
            return loanResponse;
        }
    }

    /**
     * check request status
     *
     * @param requestUUID
     * @return LoanResponseType in String
     */
    @Override
    public String getRequestStatus(String requestUUID) {
        return repo.getRequestStatus(requestUUID);
    }

    @Override
    public boolean updateRequestStatus(String requestUUID) {
        return repo.updateRequestStatus(requestUUID);
    }

    /**
     * Loan calculation
     *
     * @param loanRequest
     */
    @Override
    public LoanResponse createRequest(LoanRequest loanRequest) {
        LoanResponse loanResponse = repo.save(loanRequest);
        switch (loanRequest.getType()) {
            case PERSON:
                if ((loanRequest.getAmount() <= 10_000.0) & (loanRequest.getMonths() <= 12)) {
                    loanResponse.setResponseType(LoanResponseType.APPROVED);
                } else {
                    loanResponse.setResponseType(LoanResponseType.DENIED);
                    loanResponse.setCreationFlag(-1);
                }
                break;
            case OOO:
                if ((loanRequest.getAmount() > 10_000.0) & (loanRequest.getMonths() < 12)) {
                    loanResponse.setResponseType(LoanResponseType.APPROVED);
                } else {
                    loanResponse.setResponseType(LoanResponseType.DENIED);
                    loanResponse.setCreationFlag(-1);
                }
                break;
            case IP:
                loanResponse.setResponseType(LoanResponseType.DENIED);
                loanResponse.setCreationFlag(-1);
                break;
        }
        return loanResponse;
    }

}
