package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.LoanResponseType;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Store loan request data
 */
public class VariableLoanCalcRepo implements LoanCalcRepo {
    private Object[][] loanRequestAndResponseArray = new Object[1000][2];

    @Override
    public String getRequestStatus(String requestUUID) {
        for (int i = 0; i < loanRequestAndResponseArray.length; i++) {
            LoanResponse loanResponse = (LoanResponse) loanRequestAndResponseArray[i][1];
            if (Objects.equals(requestUUID, loanResponse.getRequestUUID())) {
                return loanResponse.getResponseType().name();
            }
        }
        return "There is no such request with id " + requestUUID;
    }

    @Override
    public boolean updateRequestStatus(String requestUUID) {
        for (int i = 0; i < loanRequestAndResponseArray.length; i++) {
            LoanResponse loanResponse = (LoanResponse) loanRequestAndResponseArray[i][1];
            if ((Objects.equals(requestUUID, loanResponse.getRequestUUID())) & (loanResponse.getResponseType() != null)) {
                switch (loanResponse.getResponseType()) {
                    case DENIED:
                        loanResponse.setResponseType(LoanResponseType.APPROVED);
                        System.out.println("Status updated. New status is " + loanResponse.getResponseType());
                        break;
                    case APPROVED:
                        loanResponse.setResponseType(LoanResponseType.DENIED);
                        System.out.println("Status updated. New status is " + loanResponse.getResponseType());
                        break;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * TODO persists request
     *
     * @return Loan Response
     */
    @Override
    public LoanResponse save(LoanRequest loanRequest) {
        LoanResponse loanResponse = new LoanResponse();
        for (int i = 0; i < loanRequestAndResponseArray.length; i++) {
            loanRequestAndResponseArray[i][0] = loanRequest;
            loanRequestAndResponseArray[i][1] = loanResponse;
        }
        return loanResponse;
    }
}
