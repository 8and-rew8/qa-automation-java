package com.tinkoff.edu;

import com.tinkoff.edu.app.LoanResponse;
import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.DefaultLoanCalcService;
import com.tinkoff.edu.app.LoanCalcController;
import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.StaticVariableLoanCalcRepo;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;
import com.tinkoff.edu.app.interfaces.LoanCalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    private LoanCalcService loanCalcService;
    private LoanCalcRepo loanCalcRepo;
    private LoanRequest loanRequest;

    @BeforeEach
    public void Init() {
        loanRequest = new LoanRequest(ClientType.PERSON, 10, 1000);
        loanCalcRepo = new StaticVariableLoanCalcRepo();
        loanCalcService = new DefaultLoanCalcService(loanCalcRepo);
    }

    @Test
    public void shouldGetId1WhenFirstCall() {
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
        assertEquals(1, loanResponse.getRequestId());
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall(){
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
        assertEquals(loanCalcRepo.getRepoSize(), loanResponse.getRequestId());
    }

}
