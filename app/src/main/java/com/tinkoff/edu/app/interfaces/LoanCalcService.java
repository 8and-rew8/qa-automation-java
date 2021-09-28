package com.tinkoff.edu.app.interfaces;

import com.tinkoff.edu.app.BusinessRulesException;
import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.LoanResponse;
import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.enums.LoanResponseType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest loanRequest) throws IOException;

    LoanResponse validationRequest(LoanRequest loanRequest) throws BusinessRulesException;

    String getRequestStatus(String requestUUID) throws IOException;

    boolean updateRequestStatus(String requestUUID) throws IOException;
}
