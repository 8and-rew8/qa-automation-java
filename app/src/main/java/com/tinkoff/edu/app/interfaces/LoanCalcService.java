package com.tinkoff.edu.app.interfaces;

import com.tinkoff.edu.app.BusinessRulesException;
import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.LoanResponse;
import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.enums.LoanResponseType;

import java.util.ArrayList;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest loanRequest);

    LoanResponse validationRequest(LoanRequest loanRequest) throws BusinessRulesException;

    String getRequestStatus(String requestUUID);

    boolean updateRequestStatus(String requestUUID);

    Double requestsSum (ClientType clientType);
}
