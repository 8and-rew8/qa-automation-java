package com.tinkoff.edu.app;

public class BusinessRulesException extends Exception {
    public BusinessRulesException(String massage, Throwable error) {
        super(massage, error);
    }

    public BusinessRulesException(String massage) {
        super(massage);
    }
}
