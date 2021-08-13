package com.tinkoff.edu.app;

public class LoanCalcDao {
    private static int requestId;
    /**
     * TODO persists request
     * @return Request ID
     */
    public static int save() {
        return ++requestId;
    }
}
