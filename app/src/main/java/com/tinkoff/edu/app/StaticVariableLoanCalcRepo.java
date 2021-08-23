package com.tinkoff.edu.app;

import java.util.ArrayList;

/**
 * Store loan request data
 */
public class StaticVariableLoanCalcRepo implements LoanCalcRepo {
    private static final ArrayList<LoanResponse> loanResponseArrayList = new ArrayList<>();

    /**
     * TODO persists request
     *
     * @return Loan Response
     */
    @Override
    public LoanResponse save() {
        LoanResponse loanResponse = new LoanResponse();
        loanResponseArrayList.add(loanResponse);
        return loanResponse;
    }
}
