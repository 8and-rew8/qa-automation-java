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
    private final Object[][] loanRequestAndResponseArray = new Object[100][2];
    public int counter = 1;

    @Override
    public String getRequestStatus(String requestUUID) {
        for (int i = 0; i < counter - 1; i++) {
            if (loanRequestAndResponseArray[i][1] instanceof LoanResponse) {
                LoanResponse loanResponse = (LoanResponse) loanRequestAndResponseArray[i][1];
                if (Objects.equals(requestUUID, loanResponse.getRequestUUID())) {
                    return loanResponse.getResponseType().name();
                }
            }
        }
        return "There is no such request with id " + requestUUID;
    }

    @Override
    public boolean updateRequestStatus(String requestUUID) {
        for (int i = 0; i < counter - 1; i++) {
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
     * @return Loan Response
     */
    @Override
    public LoanResponse save(LoanRequest loanRequest) {
        try {
            LoanResponse loanResponse = new LoanResponse();
            for (int i = 0; i < counter; i++) {
                loanRequestAndResponseArray[i][0] = loanRequest;
                loanRequestAndResponseArray[i][1] = loanResponse;
            }
            counter++;
            return loanResponse;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Array has been expend");
        }

    }
}
