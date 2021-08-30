package com.tinkoff.edu;

import com.tinkoff.edu.app.LoanResponse;
import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.DefaultLoanCalcService;
import com.tinkoff.edu.app.LoanCalcController;
import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.VariableLoanCalcRepo;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;
import com.tinkoff.edu.app.interfaces.LoanCalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.*;

public class AppTest {
    private LoanCalcService loanCalcService;
    private LoanCalcRepo loanCalcRepo;
    private LoanRequest loanRequest;

    @BeforeEach
    public void Init() {
        loanRequest = new LoanRequest(ClientType.PERSON, -100, 0);
        loanCalcRepo = new VariableLoanCalcRepo();
        loanCalcService = new DefaultLoanCalcService(loanCalcRepo);
    }

    @Test
    @DisplayName("Первый запрос должен иметь ID = 1")
    public void shouldGetId1WhenFirstCall() {
        LoanRequest firstLoanRequest = new LoanRequest(ClientType.PERSON, 10, 100);
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(firstLoanRequest);
        assertEquals(1, loanResponse.getRequestId(), "Expected 1 but actual " + loanResponse.getRequestId());
    }

    @Test
    @DisplayName("Номер запроса должен совпадать с размером списка")
    public void shouldGetIncrementedIdWhenAnyCall() {
        LoanRequest someLoanRequest = new LoanRequest(ClientType.PERSON, 10, 100);
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(someLoanRequest);
        assertEquals(loanCalcRepo.getRepoSize(), loanResponse.getRequestId(), "Expected " + loanCalcRepo.getRepoSize() + " but actual " + loanResponse.getRequestId());
    }

    @Test
    @DisplayName("Поля запроса не должны быть нулевыми")
    public void shouldGetErrorWhenApplyNullRequest() {
        LoanRequest loanNullRequest = new LoanRequest();
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanNullRequest);
        assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " + loanResponse.getRequestId());
    }

    @Test
    @DisplayName("Можно дать кредит только на положительное число месяцев")
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequest() {
        LoanRequest loanZeroAmountRequest = new LoanRequest(ClientType.PERSON, 12, 0);
        LoanRequest loanNegativeAmountRequest = new LoanRequest(ClientType.PERSON, 12, -1);
        if (loanRequest.getAmount() == 0) {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanZeroAmountRequest);
            assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " + loanResponse.getRequestId());
        } else if (loanRequest.getAmount() < 0) {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanNegativeAmountRequest);
            assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " + loanResponse.getRequestId());
        }
    }

    @Test
    @DisplayName("Можно дать кредит только на положительную сумму")
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequest() {
        LoanRequest loanZeroMonthsRequest = new LoanRequest(ClientType.OOO, 0, 11000);
        LoanRequest loanNegativeMonthsRequest = new LoanRequest(ClientType.OOO, -1, 11000);
        if (loanRequest.getMonths() == 0) {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanZeroMonthsRequest);
            assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " + loanResponse.getRequestId());
        } else if (loanRequest.getMonths() < 0) {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanNegativeMonthsRequest);
            assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " + loanResponse.getRequestId());
        }
    }

    @Test
    @DisplayName("Не даем кредит ИПшникам")
    public void shouldGetErrorWhenIpClientType() {
        LoanRequest loanRequest = new LoanRequest(ClientType.IP, 10, 1000);
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
        assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " + loanResponse.getRequestId());
    }

    @Test
    @DisplayName("Даем кредит ООО только на суммы больше 10к")
    public void shouldGetErrorWhenOooClientTypeAndAmountTooLittle() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 1000);
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
        assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " + loanResponse.getRequestId());
    }

    @Test
    @DisplayName("Для ФЛ кредит на <= 12, Для ООО < 12 месяцев")
    public void shouldGetErrorWhenLoanForMoreThenYear() {
        LoanRequest loanRequestForPerson = new LoanRequest(ClientType.PERSON, 15, 10000);
        LoanRequest loanRequestForOoo = new LoanRequest(ClientType.OOO, 12, 11000);
        if (loanRequest.getType() == ClientType.PERSON) {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForPerson);
            assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " + loanResponse.getRequestId());
        } else if (loanRequest.getType() == ClientType.OOO) {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForOoo);
            assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " + loanResponse.getRequestId());
        }
    }

}
