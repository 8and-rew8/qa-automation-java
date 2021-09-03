package com.tinkoff.edu;

import com.tinkoff.edu.app.LoanResponse;
import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.DefaultLoanCalcService;
import com.tinkoff.edu.app.LoanCalcController;
import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.VariableLoanCalcRepo;
import com.tinkoff.edu.app.enums.LoanResponseType;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;
import com.tinkoff.edu.app.interfaces.LoanCalcService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    private static LoanCalcRepo loanCalcRepo;
    private LoanCalcService loanCalcService;

    @BeforeAll
    public static void initRepo() {
        loanCalcRepo = new VariableLoanCalcRepo();
    }

    @BeforeEach
    public void init() {
        loanCalcService = new DefaultLoanCalcService(loanCalcRepo);
    }

    @Test
    @DisplayName("Case #1 - Первый запрос должен иметь ID = 1")
    public void shouldGetId1WhenFirstCall() {
        LoanRequest firstLoanRequest = new LoanRequest(ClientType.PERSON, 10, 100, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(firstLoanRequest);
        assertEquals(1, loanResponse.getCreationFlag(), "Expected 1 but actual " + loanResponse.getCreationFlag());
    }

    @Test
    @DisplayName("Case #2 - Поля запроса не должны быть нулевыми")
    public void shouldGetErrorWhenApplyNullRequest() {
        LoanRequest loanNullRequest = new LoanRequest();
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanNullRequest);
        assertEquals(-1, loanResponse.getCreationFlag(),
                "Expected validation failure with code -1 but actual " + loanResponse.getCreationFlag());
    }

    @Test
    @DisplayName("Case #3 - Нельзя дать кредит на нулевую сумму")
    public void shouldGetErrorWhenApplyZeroAmountRequest() {
        LoanRequest loanZeroAmountRequest = new LoanRequest(ClientType.PERSON, 12, 0, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanZeroAmountRequest);
        assertEquals(-1, loanResponse.getCreationFlag(),
                "Expected validation failure with code -1 but actual " + loanResponse.getCreationFlag());
    }

    @Test
    @DisplayName("Case #4 - Нельзя дать кредит на отрицательную сумму")
    public void shouldGetErrorWhenApplyNegativeAmountRequest() {
        LoanRequest loanNegativeAmountRequest = new LoanRequest(ClientType.PERSON, 12, -1, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanNegativeAmountRequest);
        assertEquals(-1, loanResponse.getCreationFlag(),
                "Expected validation failure with code -1 but actual " + loanResponse.getCreationFlag());
    }

    @Test
    @DisplayName("Case #5 - Нельзя дать кредит на 0 месяцев")
    public void shouldGetErrorWhenApplyZeroMonthsRequest() {
        LoanRequest loanZeroMonthsRequest = new LoanRequest(ClientType.OOO, 0, 11000, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanZeroMonthsRequest);
        assertEquals(-1, loanResponse.getCreationFlag(),
                "Expected validation failure with code -1 but actual " + loanResponse.getCreationFlag());
    }

    @Test
    @DisplayName("Case #6 - Нельзя дать кредит на отрицательное число месяцев")
    public void shouldGetErrorWhenApplyNegativeMonthsRequest() {
        LoanRequest loanNegativeMonthsRequest = new LoanRequest(ClientType.OOO, -1, 11000, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanNegativeMonthsRequest);
        assertEquals(-1, loanResponse.getCreationFlag(),
                "Expected validation failure with code -1 but actual " + loanResponse.getCreationFlag());

    }

    @Test
    @DisplayName("Case #7 - Не даем кредит ИПшникам")
    public void shouldGetErrorWhenIpClientType() {
        LoanRequest loanRequestForIp = new LoanRequest(ClientType.IP, 10, 1000, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForIp);
        assertEquals(-1, loanResponse.getCreationFlag(),
                "Expected validation failure with code -1 but actual " + loanResponse.getCreationFlag());
    }

    @Test
    @DisplayName("Case #8 - Даем кредит ООО только на суммы больше 10к")
    public void shouldGetErrorWhenOooClientTypeAndAmountTooLittle() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 1000, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
        assertEquals(-1, loanResponse.getCreationFlag(),
                "Expected validation failure with code -1 but actual " + loanResponse.getCreationFlag());
    }

    @Test
    @DisplayName("Case #9 - Для ФЛ кредит на <= 12 месяцев")
    public void shouldGetErrorWhenPersonLoanForMoreThenYear() {
        LoanRequest loanRequestForPerson = new LoanRequest(ClientType.PERSON, 15, 10000, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForPerson);
        assertEquals(-1, loanResponse.getCreationFlag(),
                "Expected validation failure with code -1 but actual " + loanResponse.getCreationFlag());
    }

    @Test
    @DisplayName("Case #10 - Для ООО кредит на < 12 месяцев")
    public void shouldGetErrorWhenOooLoanForMoreThenYear() {
        LoanRequest loanRequestForOoo = new LoanRequest(ClientType.OOO, 12, 11000, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForOoo);
        assertEquals(-1, loanResponse.getCreationFlag(),
                "Expected validation failure with code -1 but actual " + loanResponse.getCreationFlag());
    }

    @Test
    @DisplayName("Case #11 - Для ООО даем кредит на сумму больше 10к и сроком меньше 12м")
    public void shouldNotGetErrorWhenOooLoanForLessThenYearAndAmountMoreThen10k() {
        LoanRequest loanRequestForOoo = new LoanRequest(ClientType.OOO, 10, 11000, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForOoo);
        assertEquals(1, loanResponse.getCreationFlag(),
                "Expected request creation with ID 1 but actual " + loanResponse.getCreationFlag());
    }

    @Test
    @DisplayName("Case #12 - Получить статус запроса по uuid")
    public void shouldGetStatusUsingUUID() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 12, 11000, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
        assertEquals(LoanResponseType.DENIED.name(), loanCalcService.getRequestStatus(loanResponse.getRequestUUID()),
                "Expected request status denied but actual " + loanCalcService.getRequestStatus(loanResponse.getRequestUUID()));
    }


    @Test
    @DisplayName("Case #13 - Можем обновить статус у только что созданной заявки с approved на denied")
    public void shouldGetUpdatedRequestStatusAfterManagerUpdatingToDenied() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 11000, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
        assertEquals(1, loanResponse.getCreationFlag(), "Request didn't create");
        assertTrue(loanCalcService.updateRequestStatus(loanResponse.getRequestUUID()),
                "Status not updated");
    }

    @Test
    @DisplayName("Case #14 - Можем обновить статус у только что созданной заявки с denied на approved")
    public void shouldGetUpdatedRequestStatusAfterManagerUpdatingToApproved() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 12, 11000, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
        assertTrue(loanCalcService.updateRequestStatus(loanResponse.getRequestUUID()),
                "Status not updated");
    }

    @Test
    @DisplayName("Case #15 - Не можем обновить статус у заявки со статусом null")
    public void shouldGetUpdatedRequestStatusAfterManagerUpdating11() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 12, 11000, "Ф И О");
        LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
        loanResponse.setResponseType(null);
        assertFalse(loanCalcService.updateRequestStatus(loanResponse.getRequestUUID()),
                "Status not updated");
    }

    @Test
    @DisplayName("Case #16 - Не можем получить статус по несуществующему UUID")
    public void shouldGetErrorWhenGetStatusFromNonCreatedRequest() {
        // TODO refactor without sut creation, but with npe catching
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 11000, "Ф И О");
        new LoanCalcController(loanCalcService).createRequest(loanRequest);
        assertEquals("There is no such request with id abc", loanCalcService.getRequestStatus("abc"),
                "Expected error but find request with id " + loanCalcService.getRequestStatus("abc"));
    }

    @Test
    @DisplayName("Case #17 - Не можем обновить статус по нулевому UUID")
    public void shouldGetErrorWhenGetStatusFromNonCreatedRequest1() {
        // TODO refactor without sut creation, but with npe catching
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 11000, "Ф И О");
        new LoanCalcController(loanCalcService).createRequest(loanRequest);
        assertFalse(loanCalcService.updateRequestStatus(null),
                "Expected error but find request with id " + loanCalcService.updateRequestStatus(null));
    }


}
