package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ClientType;
import com.tinkoff.edu.app.enums.LoanResponseType;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Store loan request data
 */
public class VariableLoanCalcRepo implements LoanCalcRepo {
    private final Map<String, LoanResponse> loanResponseMap = new HashMap<>();
    private final Map<String, Integer> map = new HashMap<>();
    private final ArrayList<LoanRequest> loanRequests = new ArrayList<>();

    @Override
    public String getRequestStatus(String requestUUID) {
        for (String element: loanResponseMap.keySet()) {
                if (Objects.equals(element, requestUUID)) {
                    return loanResponseMap.get(requestUUID).getResponseType().name();
                }
        }
        return "There is no such request with id " + requestUUID;
    }

    @Override
    public boolean updateRequestStatus(String requestUUID) {
        for (String element: loanResponseMap.keySet()) {
            if ((Objects.equals(element, requestUUID)) && (loanResponseMap.get(requestUUID).getResponseType() != null)) {
                switch (loanResponseMap.get(requestUUID).getResponseType()) {
                    case DENIED:
                        loanResponseMap.get(requestUUID).setResponseType(LoanResponseType.APPROVED);
                        System.out.println("Status updated. New status is " + loanResponseMap.get(requestUUID).getResponseType());
                        break;
                    case APPROVED:
                        loanResponseMap.get(requestUUID).setResponseType(LoanResponseType.DENIED);
                        System.out.println("Status updated. New status is " + loanResponseMap.get(requestUUID).getResponseType());
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<LoanRequest> parameterizedRequestSearch(ClientType clientType) {
        ArrayList<LoanRequest> sortedRequestList = new ArrayList<>();
        loanRequests.stream()
                .filter(element -> element.getType() == clientType)
                .forEach(sortedRequestList::add);
        return sortedRequestList;
    }

    /**
     * @return Loan Response
     */
    @Override
    public LoanResponse save(LoanRequest loanRequest) {
            LoanResponse loanResponse = new LoanResponse();
            loanResponseMap.put(loanResponse.getRequestUUID(), loanResponse);
            loanRequests.add(loanRequest);
            map.put(loanResponse.getRequestUUID(), loanRequests.indexOf(loanRequest));
            return loanResponse;
    }
}