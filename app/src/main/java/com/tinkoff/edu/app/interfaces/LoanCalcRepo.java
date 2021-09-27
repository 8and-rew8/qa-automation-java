package com.tinkoff.edu.app.interfaces;

import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.LoanResponse;
import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.enums.LoanResponseType;

import java.util.ArrayList;

public interface LoanCalcRepo {
    LoanResponse save(LoanRequest loanRequest);

    String getRequestStatus(String requestUUID);

    boolean updateRequestStatus(String requestUUID);

    public ArrayList<LoanRequest> parameterizedRequestSearch (ClientType clientType);


}
