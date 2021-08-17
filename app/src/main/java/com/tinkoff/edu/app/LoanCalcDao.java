package com.tinkoff.edu.app;

/**
 * Store loan request data
 */
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
