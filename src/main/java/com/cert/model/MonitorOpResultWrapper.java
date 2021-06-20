package com.cert.model;

import org.json.JSONObject;

public class MonitorOpResultWrapper {

    private Status status;
    private JSONObject response;

    public MonitorOpResultWrapper(Status status, JSONObject response) {
        this.status = status;
        this.response = response;
    }

    public Status getStatus() {
        return status;
    }

    public JSONObject getResponse() {
        return response;
    }

    public enum Status{
        SUCCESS,
        ERROR
    }

}
