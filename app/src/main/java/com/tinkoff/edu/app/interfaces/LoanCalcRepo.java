package com.tinkoff.edu.app.interfaces;

import com.tinkoff.edu.app.LoanResponse;

public interface LoanCalcRepo {
    LoanResponse save();
    int getRepoSize();
}
