package com.tinkoff.edu.app.interfaces;

import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.LoanResponse;
import com.tinkoff.edu.app.enums.LoanResponseType;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest loanRequest);

    LoanResponse validationRequest(LoanRequest loanRequest);

    String getRequestStatus(String requestUUID);

    boolean updateRequestStatus(String requestUUID);
}
