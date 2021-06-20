package com.cert.model;

public enum ExecutionStatus {

    SUCCESS("Success"),
    EXPIRED("Expired"),
    FAILED("Failed");

    private String value;

    ExecutionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
