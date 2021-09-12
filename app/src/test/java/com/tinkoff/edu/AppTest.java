package com.tinkoff.edu;

import com.tinkoff.edu.app.*;
import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.enums.LoanResponseType;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;
import com.tinkoff.edu.app.interfaces.LoanCalcService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    private static LoanCalcRepo loanCalcRepo;
    private static LoanCalcService loanCalcService;

    @BeforeAll
    public static void initRepo() {
        loanCalcRepo = new VariableLoanCalcRepo();
    }

    @BeforeEach
    public void init() {
        loanCalcService = new DefaultLoanCalcService(loanCalcRepo);
    }


    @Test
    @DisplayName("Case #1 - Поля запроса не должны быть нулевыми")
    public void shouldGetErrorWhenApplyNullRequest() {
        LoanRequest loanNullRequest = new LoanRequest();
        assertThrows(NullPointerException.class, () -> new LoanCalcController(loanCalcService).createRequest(loanNullRequest),
                "Expected NPE");
    }

    @Test
    @DisplayName("Case #2 - Нельзя дать кредит на нулевую сумму")
    public void shouldGetErrorWhenApplyZeroAmountRequest() {
        LoanRequest loanZeroAmountRequest = new LoanRequest(ClientType.PERSON, 12, 0, "Пол Павель Михалыч");
        assertThrows(NullPointerException.class, () -> new LoanCalcController(loanCalcService).createRequest(loanZeroAmountRequest),
                "Expected NPE");
    }

    @Test
    @DisplayName("Case #3 - Нельзя дать кредит на отрицательную сумму")
    public void shouldGetErrorWhenApplyNegativeAmountRequest() {
        LoanRequest loanNegativeAmountRequest = new LoanRequest(ClientType.PERSON, 12, -1, "Пол Павель Михалыч");
        assertThrows(BusinessRulesException.class, () -> new LoanCalcController(loanCalcService).createRequest(loanNegativeAmountRequest),
                "Expected NPE");
    }

    @Test
    @DisplayName("Case #4 - Нельзя дать кредит на 0 месяцев")
    public void shouldGetErrorWhenApplyZeroMonthsRequest() {
        LoanRequest loanZeroMonthsRequest = new LoanRequest(ClientType.OOO, 0, 11000, "Пол Павель Михалыч");
        assertThrows(NullPointerException.class, () -> new LoanCalcController(loanCalcService).createRequest(loanZeroMonthsRequest),
                "Expected NPE");
    }

    @Test
    @DisplayName("Case #5 - Нельзя дать кредит на отрицательное число месяцев")
    public void shouldGetErrorWhenApplyNegativeMonthsRequest() {
        LoanRequest loanNegativeMonthsRequest = new LoanRequest(ClientType.OOO, -1, 11000, "Пол Павель Михалыч");
        assertThrows(BusinessRulesException.class, () -> new LoanCalcController(loanCalcService).createRequest(loanNegativeMonthsRequest),
                "Expected NPE");
    }

    @Test
    @DisplayName("Case #6 - Не даем кредит ИПшникам")
    public void shouldGetErrorWhenIpClientType() {
        LoanRequest loanRequestForIp = new LoanRequest(ClientType.IP, 10, 1000, "Пол Павель Михалыч");
        try {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForIp);
            assertEquals(LoanResponseType.DENIED, loanResponse.getResponseType(),
                    "Expected denied response but actual response status is " + loanResponse.getResponseType());
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Case #7 - Даем кредит ООО только на суммы больше 10к")
    public void shouldGetErrorWhenOooClientTypeAndAmountTooLittle() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 1000, "Пол Павель Михалыч");
        try {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
            assertEquals(LoanResponseType.DENIED, loanResponse.getResponseType(),
                    "Expected denied response but actual response status is " + loanResponse.getResponseType());
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Case #8 - Для ФЛ кредит на <= 12 месяцев")
    public void shouldGetErrorWhenPersonLoanForMoreThenYear() {
        LoanRequest loanRequestForPerson = new LoanRequest(ClientType.PERSON, 15, 10000, "Пол Павель Михалыч");
        try {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForPerson);
            assertEquals(LoanResponseType.DENIED, loanResponse.getResponseType(),
                    "Expected denied response but actual response status is " + loanResponse.getResponseType());
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Case #9 - Для ООО кредит на < 12 месяцев")
    public void shouldGetErrorWhenOooLoanForMoreThenYear() {
        LoanRequest loanRequestForOoo = new LoanRequest(ClientType.OOO, 12, 11000, "Пол Павель Михалыч");
        try {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForOoo);
            assertEquals(LoanResponseType.DENIED, loanResponse.getResponseType(),
                    "Expected denied response but actual response status is " + loanResponse.getResponseType());
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Case #10 - Для ООО даем кредит на сумму больше 10к и сроком меньше 12м")
    public void shouldNotGetErrorWhenOooLoanForLessThenYearAndAmountMoreThen10k() {
        LoanRequest loanRequestForOoo = new LoanRequest(ClientType.OOO, 10, 11000, "Пол Павель Михалыч");
        try {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForOoo);
            assertEquals(LoanResponseType.APPROVED, loanResponse.getResponseType(),
                    "Expected approved response but actual response status is " + loanResponse.getResponseType());
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("Case #11 - Получить статус запроса по uuid")
    public void shouldGetStatusUsingUUID() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 12, 11000, "Пол Павель Михалыч");
        try {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
            assertEquals(LoanResponseType.DENIED.name(), loanCalcService.getRequestStatus(loanResponse.getRequestUUID()),
                    "Expected request status denied but actual " + loanCalcService.getRequestStatus(loanResponse.getRequestUUID()));
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("Case #12 - Можем обновить статус у только что созданной заявки с approved на denied")
    public void shouldGetUpdatedRequestStatusAfterManagerUpdatingToDenied() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 11000, "Пол Павель Михалыч");
        try {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
            assertEquals(String.class, loanResponse.getRequestUUID().getClass(), "Request didn't create");
            assertTrue(loanCalcService.updateRequestStatus(loanResponse.getRequestUUID()), "Status not updated");
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Case #13 - Можем обновить статус у только что созданной заявки с denied на approved")
    public void shouldGetUpdatedRequestStatusAfterManagerUpdatingToApproved() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 12, 11000, "Пол Павель Михалыч");
        try {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
            assertTrue(loanCalcService.updateRequestStatus(loanResponse.getRequestUUID()), "Status not updated");
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Case #14 - Не можем обновить статус у заявки со статусом null")
    public void shouldGetErrorWhenTryToUpdateRequestWithStatusNull() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 12, 11000, "Пол Павель Михалыч");
        try {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
            loanResponse.setResponseType(null);
            assertFalse(loanCalcService.updateRequestStatus(loanResponse.getRequestUUID()),
                    "Status not updated");
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Case #15 - Не можем получить статус по несуществующему UUID")
    public void shouldGetErrorWhenGetStatusNotExistedUUID() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 11000, "Пол Павель Михалыч");
        try {
            new LoanCalcController(loanCalcService).createRequest(loanRequest);
            assertEquals("There is no such request with id abc", loanCalcService.getRequestStatus("abc"),
                    "Expected error but find request with id abc");
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Case #16 - Не можем обновить статус по нулевому UUID")
    public void shouldGetErrorWhenTryToUpdateRequestStatusUsingNullUUID() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 11000, "Пол Павель Михалыч");
        try {
            new LoanCalcController(loanCalcService).createRequest(loanRequest);
            assertFalse(loanCalcService.updateRequestStatus(null),
                    "Expected error but find request with null id");
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    @DisplayName("Case #17 - Переполнение массива")
    public void shouldGetErrorWhenRepoIsFull() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 11000, "Пол Павель Михалыч");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> loanCalcService.createManyRequests(101, loanRequest),
                "Repo didn't expend");
    }

    @Test
    @DisplayName("Case #18 - Нижний парог для ФИО")
    public void shouldGetErrorWhenFioTooShort() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 11000, "Пол");
        assertThrows(BusinessRulesException.class, () -> new LoanCalcController(loanCalcService).createRequest(loanRequest),
                "Expected get error because of fio too short");
    }

    @Test
    @DisplayName("Case #19 - Верхний парог для ФИО")
    public void shouldGetErrorWhenFioTooLong() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 11000,
                "ПолПолПоллПолПолПоллПолПолПоллПолПолПоллПолПолПоллПолПолПоллПолПолПоллПолПолПоллПолПолПоллПолПолПоллПолПолПолл");
        assertThrows(BusinessRulesException.class, () -> new LoanCalcController(loanCalcService).createRequest(loanRequest),
                "Expected get error because of fio too long");
    }

    @Test
    @DisplayName("Case #20 - Верхний парог для суммы")
    public void shouldGetErrorWhenAmountTooBig() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 10, 999_999.999, "Пол Павель Михалыч");
        assertThrows(BusinessRulesException.class, () -> new LoanCalcController(loanCalcService).createRequest(loanRequest),
                "Expected get error because of amount too big");
    }

    @Test
    @DisplayName("Case #21 - Верхний парог для месяцев")
    public void shouldGetErrorWhenTooManyMonths() {
        LoanRequest loanRequest = new LoanRequest(ClientType.OOO, 101, 999_999.9, "Пол Павель Михалыч");
        assertThrows(BusinessRulesException.class, () -> new LoanCalcController(loanCalcService).createRequest(loanRequest),
                "Expected get error because of too many months");
    }

    @Test
    @DisplayName("Case #22 - Выдаем кредит ФЛ")
    public void shouldGivePersonLoanWhenOkRequest() {
        LoanRequest loanRequest = new LoanRequest(ClientType.PERSON, 11, 1000, "Пол Павель Михалыч");
        try {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequest);
            assertEquals(LoanResponseType.APPROVED, loanResponse.getResponseType(),
                    "Expected approved response but actual response status is " + loanResponse.getResponseType());
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Case #23 - Не даем кредит ФЛ на > 10к")
    public void shouldGetErrorWhenPersonLoanForMoreThen10k() {
        LoanRequest loanRequestForPerson = new LoanRequest(ClientType.PERSON, 11, 10_001, "Пол Павель Михалыч");
        try {
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestForPerson);
            assertEquals(LoanResponseType.DENIED, loanResponse.getResponseType(),
                    "Expected denied response but actual response status is " + loanResponse.getResponseType());
        } catch (BusinessRulesException e) {
            e.printStackTrace();
        }
    }
}
