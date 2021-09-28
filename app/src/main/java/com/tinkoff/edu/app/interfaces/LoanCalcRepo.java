package com.tinkoff.edu.app.interfaces;

import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.LoanResponse;
import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.enums.LoanResponseType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface LoanCalcRepo {
    LoanResponse save(LoanRequest loanRequest) throws IOException;

    String getRequestStatus(String requestUUID) throws IOException;

    boolean updateRequestStatus(String requestUUID, LoanResponseType responseType) throws IOException;

    boolean updateRequestStatus(String requestUUID) throws IOException;
}
