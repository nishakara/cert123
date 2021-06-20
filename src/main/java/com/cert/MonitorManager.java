package com.cert;

import com.cert.model.Monitor;
import com.cert.model.MonitorOpResultWrapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.cert.model.MonitorOpResultWrapper.Status.ERROR;
import static com.cert.model.MonitorOpResultWrapper.Status.SUCCESS;

@Service
public class MonitorManager {

    @Autowired
    private CertRepository certRepository;

    @Autowired
    private CertScheduledExecutor certScheduledExecutor;

    public MonitorOpResultWrapper createMonitor(String monitorName, String hostName, String alertDays, String groupEmail, String port) throws Exception {
        MonitorOpResultWrapper creationResponse;
        JSONObject response = new JSONObject();
        if (!certRepository.isMonitorExist(0, monitorName)) {
            boolean resultStatus = certRepository.createMonitor(monitorName, hostName, alertDays, groupEmail, port);
            renderCreateMonitorResponse(monitorName, response, resultStatus);
            Monitor monitor = certRepository.getMonitorFromMonitorName(monitorName);
            certScheduledExecutor.addMonitor(monitor);
            creationResponse = new MonitorOpResultWrapper(SUCCESS, response);
        } else {
            System.out.println("Monitor with the name [" + monitorName + "] already exists");
            response.put("message", "Monitor with the name [" + monitorName + "] already exists");
            creationResponse = new MonitorOpResultWrapper(ERROR, response);
        }
        return creationResponse;
    }

    public MonitorOpResultWrapper updateMonitor(int id, String monitorName, String hostName, String alertDays, String groupEmail, String port) throws Exception {
        MonitorOpResultWrapper modificationResponse;
        JSONObject response = new JSONObject();
        if (!certRepository.isMonitorExist(id, monitorName)) {
            System.out.println("Monitor with the name [" + monitorName + "] does not exist");
            response.put("message", "Monitor with the name [" + monitorName + "] does not exist");
            modificationResponse = new MonitorOpResultWrapper(ERROR, response);
        } else {
            Monitor monitor = new Monitor(id, monitorName, true, hostName, Long.parseLong(alertDays), groupEmail, port);
            boolean resultStatus = certRepository.updateMonitor(id, monitorName, hostName, alertDays, groupEmail, port);
            renderUpdateMonitorResponse(monitorName, response, resultStatus);
            certScheduledExecutor.updateMonitor(monitor);
            modificationResponse = new MonitorOpResultWrapper(SUCCESS, response);
        }
        return modificationResponse;
    }

    public List<Map> getMonitorsForUser(String userId) throws Exception {
        getMonitorExecutionDetails("1");
        return certRepository.getMonitorsForUser(userId);
    }

    public MonitorOpResultWrapper deleteMonitor(String monitorId) throws Exception {
        certScheduledExecutor.removeMonitor(monitorId);
        boolean resultStatus = certRepository.deleteMonitor(monitorId);
        if (resultStatus){
            return new MonitorOpResultWrapper(SUCCESS, renderDeleteMonitorResponse(monitorId, true));
        } else {
            return new MonitorOpResultWrapper(ERROR, renderDeleteMonitorResponse(monitorId, false));
        }
    }

    public JSONArray getMonitorExecutionDetails(String monitorId) throws Exception {
        try {
            JSONArray resultArray = certRepository.getMonitorExecutionDetails(monitorId);
            System.out.println("Monitor execution details : \n" + resultArray.toString());
            return resultArray;
        } catch (Exception e) {
            System.err.println("Error occurred while getting execution details.");
            e.printStackTrace();
            throw e;
        }
    }

    private void renderCreateMonitorResponse(String monitorName, JSONObject response, boolean resultStatus) {
        if (resultStatus) {
            System.out.println("Monitor with the name [" + monitorName + "] created successfully");
            response.put("message", "Successfully created monitor");
        } else {
            response.put("message", "Error creating monitor, SQL Error");
        }
    }

    private void renderUpdateMonitorResponse(String monitorName, JSONObject response, boolean resultStatus) {
        if (resultStatus) {
            System.out.println("Monitor with the name [" + monitorName + "] updated successfully");
            response.put("message", "Successfully updated monitor");
        } else {
            response.put("message", "Error updating monitor, SQL Error");
        }
    }

    private JSONObject renderDeleteMonitorResponse(String monitorId, boolean resultStatus) {
        JSONObject response = new JSONObject();
        if (resultStatus) {
            System.out.println("Monitor with the ID [" + monitorId + "] deleted successfully");
            response.put("message", "Successfully deleted monitor");
        } else {
            response.put("message", "Error deleting monitor, SQL Error");
        }
        return response;
    }

}
