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

public class AppTest {
    private LoanCalcService loanCalcService;
    private LoanCalcRepo loanCalcRepo;

    @BeforeEach
    public void Init() {
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
        assertEquals(loanCalcRepo.getRepoSize(), loanResponse.getRequestId(), "Expected " +
                        loanCalcRepo.getRepoSize() + " but actual " + loanResponse.getRequestId());
    }

    @Test
    @DisplayName("Поля запроса не должны быть нулевыми")
    public void shouldGetErrorWhenApplyNullRequest() {
        LoanRequest loanNullRequest = new LoanRequest();
        int validationResponse = loanCalcService.validationRequest(loanNullRequest);
        assertEquals(-1, validationResponse,
                "Expected validation failure with code -1 but actual " + validationResponse);
    }

    @Test
    @DisplayName("Нельзя дать кредит на нулевую сумму")
    public void shouldGetErrorWhenApplyZeroAmountRequest() {
        LoanRequest loanZeroAmountRequest = new LoanRequest(ClientType.PERSON, 12, 0);
        int validationResponse = loanCalcService.validationRequest(loanZeroAmountRequest);
            assertEquals(-1, validationResponse,
                    "Expected validation failure with code -1 but actual " + validationResponse);
    }

    @Test
    @DisplayName("Нельзя дать кредит на отрицательную сумму")
    public void shouldGetErrorWhenApplyNegativeAmountRequest(){
        LoanRequest loanNegativeAmountRequest = new LoanRequest(ClientType.PERSON, 12, -1);
        int validationResponse = loanCalcService.validationRequest(loanNegativeAmountRequest);
        assertEquals(-1, validationResponse,
                "Expected validation failure with code -1 but actual " + validationResponse);
    }

    @Test
    @DisplayName("Нельзя дать кредит на 0 месяцнв")
    public void shouldGetErrorWhenApplyZeroMonthsRequest() {
        LoanRequest loanZeroMonthsRequest = new LoanRequest(ClientType.OOO, 0, 11000);
        int validationResponse = loanCalcService.validationRequest(loanZeroMonthsRequest);
            assertEquals(-1, validationResponse,
                    "Expected validation failure with code -1 but actual " + validationResponse);
    }

    @Test
    @DisplayName("Нельзя дать кредит на отрицательное число месяцев")
    public void shouldGetErrorWhenApplyNegativeMonthsRequest(){
        LoanRequest loanNegativeMonthsRequest = new LoanRequest(ClientType.OOO, -1, 11000);
        int validationResponse = loanCalcService.validationRequest(loanNegativeMonthsRequest);
        assertEquals(-1, validationResponse,
                "Expected validation failure with code -1 but actual " + validationResponse);

    }

    @Test
    @DisplayName("Не даем кредит ИПшникам")
    public void shouldGetErrorWhenIpClientType() {
        LoanRequest loanRequestForIp = new LoanRequest(ClientType.IP, 10, 1000);
        int validationResponse = loanCalcService.validationRequest(loanRequestForIp);
        assertEquals(0, validationResponse,
                "Expected validation pass with code 0 but actual " + validationResponse);
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForIp);
        assertEquals(-1, loanResponse.getRequestId(),
                "Expected request with ID -1 but actual " + loanResponse.getRequestId());
    }

    @Test
    @DisplayName("Даем кредит ООО только на суммы больше 10к")
    public void shouldGetErrorWhenOooClientTypeAndAmountTooLittle() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 1000);
        int validationResponse = loanCalcService.validationRequest(loanRequest);
        assertEquals(0, validationResponse,
                "Expected validation pass with code 0 but actual " + validationResponse);
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
        assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " +
                        loanResponse.getRequestId());
    }

    @Test
    @DisplayName("Для ФЛ кредит на <= 12 месяцев")
    public void shouldGetErrorWhenPersonLoanForMoreThenYear() {
        LoanRequest loanRequestForPerson = new LoanRequest(ClientType.PERSON, 15, 10000);
        int validationResponse = loanCalcService.validationRequest(loanRequestForPerson);
        assertEquals(0, validationResponse,
                "Expected validation pass with code 0 but actual " + validationResponse);
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForPerson);
            assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " +
                            loanResponse.getRequestId());
    }

    @Test
    @DisplayName("Для ООО кредит на < 12 месяцев")
    public void shouldGetErrorWhenOooLoanForMoreThenYear(){
        LoanRequest loanRequestForOoo = new LoanRequest(ClientType.OOO, 12, 11000);
        int validationResponse = loanCalcService.validationRequest(loanRequestForOoo);
        assertEquals(0, validationResponse,
                "Expected validation pass with code 0 but actual " + validationResponse);
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForOoo);
        assertEquals(-1, loanResponse.getRequestId(), "Expected request with ID -1 but actual " +
                loanResponse.getRequestId());

    }

}
