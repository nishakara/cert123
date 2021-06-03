package com.cert;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MonitorManager {

    @Autowired
    private CertRepository certRepository;

    public JSONObject createMonitor(String monitorName, String hostName, String alertDays, String groupEmail, String port) throws Exception {
        JSONObject response = new JSONObject();
        if (!certRepository.isMonitorExist(monitorName)) {
            boolean resultStatus = certRepository.createMonitor(monitorName, hostName, alertDays, groupEmail, port);
            renderCreateMonitorResponse(monitorName, response, resultStatus);
        } else {
            System.out.println("Monitor with the name [" + monitorName + "] already exists");
            response.put("message", "Monitor with the name [" + monitorName + "] already exists");
        }
        return response;
    }

    public List<Map> getMonitorsForUser(String userId) throws Exception {
        return certRepository.getMonitorsForUser(userId);
    }

    public JSONObject deleteMonitor(String monitorName) throws Exception {
        boolean resultStatus = certRepository.deleteMonitor(monitorName);
        return renderDeleteMonitorResponse(monitorName, resultStatus);
    }

    private void renderCreateMonitorResponse(String monitorName, JSONObject response, boolean resultStatus) {
        if (resultStatus) {
            System.out.println("Monitor with the name [" + monitorName + "] created successfully");
            response.put("message", "Successfully created monitor");
        } else {
            response.put("message", "Error creating monitor, SQL Error");
        }
    }

    private JSONObject renderDeleteMonitorResponse(String monitorName, boolean resultStatus) {
        JSONObject response = new JSONObject();
        if (resultStatus) {
            System.out.println("Monitor with the name [" + monitorName + "] deleted successfully");
            response.put("message", "Successfully deleted monitor : " + monitorName);
        } else {
            response.put("message", "Error deleting monitor, SQL Error");
        }
        return response;
    }
}
