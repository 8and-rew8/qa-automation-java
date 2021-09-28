package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.LoanResponseType;
import com.tinkoff.edu.app.interfaces.LoanCalcRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.tinkoff.edu.app.enums.LoanResponseType.DENIED;
import static java.nio.file.StandardOpenOption.*;

/**
 * Store loan request data
 */
public class VariableLoanCalcRepo implements LoanCalcRepo {
    private final Path requestsPath = Path.of("requestsPath.txt");
    private final Path responsePath = Path.of("responsePath.txt");

    @Override
    public String getRequestStatus(String requestUUID) throws IOException {
        List<String> strings = Files.readAllLines(responsePath);
        for (String line : strings) {
            if ((requestUUID != null) && (line.startsWith(requestUUID))) {
                return line.split(",")[1];
            }
        }
        return "There is no such request with id " + requestUUID;
    }

    @Override
    public boolean updateRequestStatus(String requestUUID, LoanResponseType newStatus) throws IOException {
        List<String> strings = Files.readAllLines(responsePath);
        for (String line : strings) {
            if ((requestUUID != null) && (line.startsWith(requestUUID))) {
                String newLine = requestUUID + "," + newStatus.name();
                int index = strings.indexOf(line);
                strings.set(index, newLine);
                Files.write(responsePath, strings, WRITE);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateRequestStatus(String requestUUID) throws IOException {
        List<String> strings = Files.readAllLines(responsePath);
        for (String line : strings) {
            if ((requestUUID != null) && (line.startsWith(requestUUID))) {
                String newLine;
                int index = strings.indexOf(line);
                if (Objects.equals(line.split(",")[1], DENIED.name())) {
                    newLine = requestUUID + ",APPROVED";
                } else {
                    newLine = requestUUID + ",DENIED";
                }
                strings.set(index, newLine);
                Files.write(responsePath, strings, WRITE);
                return true;
            }
        }
        return false;
    }

    /**
     * @return Loan Response
     */
    @Override
    public LoanResponse save(LoanRequest loanRequest) throws IOException {
        LoanResponse loanResponse = new LoanResponse();
        Files.write(requestsPath, List.of(loanRequest.toString()), WRITE, CREATE, APPEND);
        Files.write(responsePath, List.of(loanResponse.toString()), WRITE, CREATE, APPEND);
        return loanResponse;
    }
}