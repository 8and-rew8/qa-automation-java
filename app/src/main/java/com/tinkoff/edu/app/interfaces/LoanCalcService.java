package com.tinkoff.edu.app.interfaces;

import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.LoanResponse;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest loanRequest);
    LoanResponse validationRequest(LoanRequest loanRequest);
}
