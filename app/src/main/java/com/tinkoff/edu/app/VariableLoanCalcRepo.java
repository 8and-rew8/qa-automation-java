package com.tinkoff.edu.app;

import com.tinkoff.edu.app.interfaces.LoanCalcRepo;

import java.util.ArrayList;

/**
 * Store loan request data
 */
public class VariableLoanCalcRepo implements LoanCalcRepo {
    private ArrayList<LoanResponse> loanResponseArrayList = new ArrayList<>();

    /**
     * get size of repo
     *
     * @return int
     */
    @Override
    public int getRepoSize() {
        return loanResponseArrayList.size();
    }

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
