package com.tinkoff.edu.test;

import com.tinkoff.edu.app.ClientType;
import com.tinkoff.edu.app.DefaultLoanCalcService;
import com.tinkoff.edu.app.LoanCalcController;
import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.StaticVariableLoanCalcRepo;

/**
 * Loan Calc Tests
 */
public class LoanCalcTest {
    public static void main(String... args) {

        System.out.println(
                new LoanCalcController(new DefaultLoanCalcService(new StaticVariableLoanCalcRepo())).createRequest(
                        new LoanRequest(ClientType.PERSON, 10, 1000))
                        + "; ID should be 1 and answer should be APPROVED");
    }
}
