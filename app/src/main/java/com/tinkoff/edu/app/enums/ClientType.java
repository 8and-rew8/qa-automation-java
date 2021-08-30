package com.tinkoff.edu.app.enums;

public enum ClientType {
    PERSON ("PERSON"),
    IP ("IP"),
    OOO ("OOO");
    private String type;

    ClientType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
