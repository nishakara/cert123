package com.cert.model;

public enum MonitorStatus {

    NOT_YET_TESTED("Not Yet Tested"),
    OK("OK"),
    FAILED("Failed");

    private String value;

    MonitorStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
