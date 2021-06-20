package com.cert.model;

import java.util.Objects;

public class Monitor {

    public int id;
    public String monitorName;
    public boolean enabled;
    public String hostName;
    public long alertDays;
    public String groupEmail;
    public String port;
    public MonitorStatus status;
    public boolean alertSent;

    public Monitor() {
    }

    public Monitor(int id, String monitorName, boolean enabled, String hostName, long alertDays, String groupEmail, String port) {
        this.id = id;
        this.monitorName = monitorName;
        this.enabled = enabled;
        this.hostName = hostName;
        this.alertDays = alertDays;
        this.groupEmail = groupEmail;
        this.port = port;
    }

    public String generateUrl() {
        if (Objects.nonNull(port) && !port.isEmpty())
            return hostName + ":" + port;
        else
            return hostName;
    }

    public void update(Monitor monitor) {
        this.monitorName = monitor.monitorName;
        this.enabled = monitor.enabled;
        this.hostName = monitor.hostName;
        this.alertDays = monitor.alertDays;
        this.groupEmail = monitor.groupEmail;
        this.port = monitor.port;
    }
}
