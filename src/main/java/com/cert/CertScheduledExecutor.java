package com.cert;

import com.cert.mail.MailSender;
import com.cert.model.ExecutionStatus;
import com.cert.model.Monitor;
import com.cert.model.MonitorOpResultWrapper;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CertScheduledExecutor {

    @Autowired
    private CertProcessor certProcessor;

    @Autowired
    private CertRepository certRepository;

    @Autowired
    private MailSender mailSender;

    private Map<String, Monitor> monitorMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void startExecution() throws Exception {
        loadMonitors();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(this::executeMonitors, 1, 10, TimeUnit.SECONDS); // Development
//        fillMockValuesToDB();
//        executorService.schedule(this::executeMonitor, 1, 6, TimeUnit.HOURS); //Production
    }

    private void loadMonitors() throws Exception {
        System.out.println("Loading all monitors for execution");
        List<Monitor> monitorList = certRepository.getAllMonitors();
        monitorList.forEach(monitor -> monitorMap.put(String.valueOf(monitor.id), monitor));
    }

    private void executeMonitors() {
        System.out.println("Executing monitors");
        Set<String> monitorIds = monitorMap.keySet();
        for (String monitorId : monitorIds) {
            Monitor monitor = monitorMap.get(monitorId);
            executeMonitor(monitor);
        }
    }

    private void executeMonitor(Monitor monitor) {
        System.out.println("Executing monitor : " + monitor.monitorName);
        try {
            MonitorOpResultWrapper operationResult = certProcessor.getCertInfo(monitor);
            JSONObject certInfo = operationResult.getResponse();
            ExecutionStatus executionStatus = ExecutionStatus.valueOf(String.valueOf(certInfo.get("execution_status")));
            boolean shouldAlert = verifyCertificateTimeout(certInfo, monitor, executionStatus);
            if (shouldAlert) sendAlert(monitor, certInfo, executionStatus);
            updateDatabase(monitor, executionStatus);
        } catch (Exception e) {
            System.err.println("Error occurred while executing monitor : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateDatabase(Monitor monitor, ExecutionStatus executionStatus) {
        boolean isSuccess = ExecutionStatus.SUCCESS == executionStatus;
        try {
            certRepository.addExecutionStatus(monitor.id, new Date(), executionStatus, isSuccess);
        } catch (Exception e) {
            System.err.println("Error occurred while saving execution status in DB");
            e.printStackTrace();
        }
    }

    private void fillMockValuesToDB() {
        Random random = new Random();
        for (int i = 1 ; i < 80; i++) {
            try {
                certRepository.addExecutionStatus(1, new Date(System.currentTimeMillis() - 3600*12*1000*i - 3600*12*1000*20),
                        ExecutionStatus.SUCCESS, random.nextBoolean());
            } catch (Exception e) {
                System.err.println("Error occurred while saving execution status in DB");
                e.printStackTrace();
            }
        }
        System.out.println("Done filling mock values");
    }

    private void sendAlert(Monitor monitor, JSONObject certInfo, ExecutionStatus executionStatus) {
        System.out.println("Sending alert for monitor : " + monitor.monitorName);
        try {
            if (!monitor.alertSent){
                String emailBodyStr = generateEmail(monitor, certInfo, executionStatus);
                mailSender.sendMail(MailSender.SYSTEM_ID, monitor.groupEmail, "CertMonitor Alert !", emailBodyStr);
                monitor.alertSent = true;
                certRepository.updateAlertSentStatus(monitor.id, true);
            } else {
                System.out.println("Alert already sent for the monitor : [" + monitor.monitorName + "]");
            }
        } catch (MessagingException| UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error occurred while updating alertSent for monitor : [" + monitor.monitorName + "]");
            e.printStackTrace();
        }
    }

    private String generateEmail(Monitor monitor, JSONObject certInfo, ExecutionStatus executionStatus) {
        if (ExecutionStatus.EXPIRED == executionStatus) {
            Map<String, Object> data = new HashMap<>();
            data.put("hostName", monitor.hostName);
            data.put("port", monitor.port);
            return new StrSubstitutor(data, "%{", "}").replace(mailSender.getMessageTemplateExpired());
        } else {
            Map<String, Object> data = new HashMap<>();
            data.put("hostName", monitor.hostName);
            data.put("daysToExpire", certInfo.getLong("diff_in_days"));
            data.put("port", monitor.port);
            data.put("validFrom", certInfo.getString("valid_from"));
            data.put("validTo", certInfo.getString("valid_to"));
            return new StrSubstitutor(data, "%{", "}").replace(mailSender.getMessageTemplate());
        }
    }

    private boolean verifyCertificateTimeout(JSONObject certInfo, Monitor monitor, ExecutionStatus executionStatus) {
        boolean shouldAlert = false;
        if (ExecutionStatus.SUCCESS == executionStatus) {
            long differenceInDays = Long.parseLong(String.valueOf(certInfo.get("diff_in_days")));
            shouldAlert = differenceInDays < monitor.alertDays;
            System.out.println(String.format("Difference in days : %s, Alert Days : %s, ShouldAlert : %s",
                    String.valueOf(differenceInDays), String.valueOf(monitor.alertDays), String.valueOf(shouldAlert)));
        } else if (ExecutionStatus.EXPIRED == executionStatus) {
            shouldAlert = true;
        }
        return shouldAlert;
    }

    public void addMonitor(Monitor monitor) {
        monitorMap.putIfAbsent(String.valueOf(monitor.id), monitor);
    }

    public void removeMonitor(String monitorId) {
        try {
            monitorMap.remove(monitorId);
        } catch (Exception e) {
            System.out.println("Ignored exception while removing monitor : " + e.getMessage());
        }
    }

    public void updateMonitor(Monitor monitor) {
        Monitor monitorInMap = monitorMap.get(String.valueOf(monitor.id));
        monitorInMap.update(monitor);
        if (!monitor.enabled) {
            monitorMap.remove(String.valueOf(monitor.id));
        }
    }
}
