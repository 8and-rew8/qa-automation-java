package com.tinkoff.edu.app;

/**
 * Logic for loan request
 */
public class LoanCalcService {
    /**
     * Loan calculation
     */
    public static int createRequest() {
        //TODO data processing
        return LoanCalcDao.save();
    }
}
