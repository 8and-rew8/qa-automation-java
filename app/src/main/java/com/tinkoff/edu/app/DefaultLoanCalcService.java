package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.enums.LoanResponseType;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;
import com.tinkoff.edu.app.interfaces.LoanCalcService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public LoanResponse validationRequest(LoanRequest loanRequest) throws RuntimeException, BusinessRulesException {
        LoanResponse loanResponse = new LoanResponse();
        try {
            boolean isPresent = Arrays.stream(ClientType.values())
                    .anyMatch(element -> Objects.equals(element.getType(), loanRequest.getType().toString()));
            if ((loanRequest.getAmount() == 0) || (loanRequest.getMonths() == 0) || (!isPresent)) {
                throw new NullPointerException("request with null argument");
            } else if (loanRequest.getAmount() < 0.01 || loanRequest.getAmount() > 999_999.99) {
                throw new BusinessRulesException("wrong loan amount");
            } else if (loanRequest.getMonths() < 1 || loanRequest.getMonths() > 100) {
                throw new BusinessRulesException("wrong amount of months");
            } else {
                String regex = "^[А-Яа-я_ ]{9,99}$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(loanRequest.getFio());
                if (!m.matches()) {
                    throw new BusinessRulesException("wrong fio");
                }
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("request with null argument");
        } catch (BusinessRulesException e) {
            throw new BusinessRulesException("request validation failed", e);
        }
        return loanResponse;
    }

    /**
     * check request status
     *
     * @param requestUUID
     * @return LoanResponseType in String
     */
    @Override
    public String getRequestStatus(String requestUUID) throws IOException {
        return repo.getRequestStatus(requestUUID);
    }

    @Override
    public boolean updateRequestStatus(String requestUUID) throws IOException {
        return repo.updateRequestStatus(requestUUID);
    }


    /**
     * Loan calculation
     *
     * @param loanRequest
     */
    @Override
    public LoanResponse createRequest(LoanRequest loanRequest) throws IOException {
        LoanResponse loanResponse = repo.save(loanRequest);
        switch (loanRequest.getType()) {
            case PERSON:
                if ((loanRequest.getAmount() <= 10_000.0) & (loanRequest.getMonths() <= 12)) {
                    repo.updateRequestStatus(loanResponse.getRequestUUID(), LoanResponseType.APPROVED);
                    loanResponse.setResponseType(LoanResponseType.APPROVED);
                } else {
                    repo.updateRequestStatus(loanResponse.getRequestUUID(), LoanResponseType.DENIED);
                    loanResponse.setResponseType(LoanResponseType.DENIED);
                }
                break;
            case OOO:
                if ((loanRequest.getAmount() > 10_000.0) & (loanRequest.getMonths() < 12)) {
                    repo.updateRequestStatus(loanResponse.getRequestUUID(), LoanResponseType.APPROVED);
                    loanResponse.setResponseType(LoanResponseType.APPROVED);
                } else {
                    repo.updateRequestStatus(loanResponse.getRequestUUID(), LoanResponseType.DENIED);
                    loanResponse.setResponseType(LoanResponseType.DENIED);
                }
                break;
            case IP:
                repo.updateRequestStatus(loanResponse.getRequestUUID(), LoanResponseType.DENIED);
                loanResponse.setResponseType(LoanResponseType.DENIED);
                break;
        }
        return loanResponse;
    }

}
