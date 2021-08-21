package com.tinkoff.edu.test;

import com.tinkoff.edu.app.*;

/**
 * Loan Calc Tests
 */
public class LoanCalcTest {
    public static void main(String... args) {

        System.out.println(
                new LoanCalcController(new DefaultLoanCalcService(new StaticArrayListLoanCalcRepo())).createRequest(
                        new LoanRequest(ClientType.PERSON, 10, 1000))
                        + "; ID should be 1 and answer should be APPROVED");
    }
}
