package com.cert;

import com.cert.model.ExecutionStatus;
import com.cert.model.Monitor;
import com.cert.model.MonitorStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;


@Repository
public class CertRepository {

    @Value("${jdbc.url}")
    private String repoUrl;

    @Value("${jdbc.user.name}")
    private String repoUser;

    @Value("${jdbc.user.password}")
    private String repoUserPswd;

    private String driverName = "com.mysql.cj.jdbc.Driver";
    ;

    public String getPasswordOfUser(String userName) throws Exception {
        String query = "SELECT pswd from users where userName='" + userName + "'";
        return executeQuery(query);
    }

    public boolean isAccountAlreadyExist(String email) throws Exception {
        String query = "SELECT userName from users where userName = '" + email + "'";
        String result = executeQuery(query);
        return Objects.nonNull(result);
    }

    public boolean createAccount(String email, String pswd, String groupEmail) throws Exception {
        String query = "INSERT INTO users(userName, pswd, groupId) " +
                "VALUES('" + email + "', '" + pswd + "', '" + groupEmail + "');";
        return executeUpdate(query);
    }

    public boolean isMonitorExist(int id, String monitorName) throws Exception {
        String query;
        if (id == 0) { //for insert flow id will be 0, but need to check using unique monitorName
            query = "SELECT monitorName FROM monitors where monitorName = '" + monitorName + "'";
        } else {
            query = "SELECT monitorName FROM monitors where monitorName = '" + monitorName + "' AND id=" + id;
        }
        String result = executeQuery(query);
        return Objects.nonNull(result);
    }

    public boolean createMonitor(String monitorName, String hostName, String alertDays, String groupEmail, String port) throws Exception {
        String query = "INSERT INTO monitors(monitorName, hostName, alertDays, groupEmail, port) " +
                "VALUES('" + monitorName + "', '" + hostName + "', '" + alertDays + "', '" + groupEmail + "', '" + port + "' );";
        return executeUpdate(query);
    }

    public boolean updateMonitor(int id, String monitorName, String hostName, String alertDays, String groupEmail, String port) throws Exception {
        String query = "update monitors set hostName='" + hostName + "' , alertDays='" + alertDays +
                "', groupEmail='" + groupEmail + "', port='" + port +
                "' where monitorName='" + monitorName + "' and id=" + id + ";";
        return executeUpdate(query);
    }

    public List<Map> getMonitorsForUser(String userId) throws Exception {
        Class.forName(driverName);
        String groupEmail = getGroupEmailOfUser(userId);
        String query = String.format("SELECT * FROM monitors where groupEmail = '%s'", groupEmail);
        List<Map> monitorList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(repoUrl, repoUser, repoUserPswd);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (Objects.requireNonNull(resultSet).next()) {
                Map<String, String> monitor = new HashMap<>();
                monitor.put("id", resultSet.getString("id"));
                monitor.put("monitorName", resultSet.getString("monitorName"));
                monitor.put("enabled", String.valueOf(resultSet.getBoolean("enabled")));
                monitor.put("hostName", resultSet.getString("hostName"));
                monitor.put("alertDays", resultSet.getString("alertDays"));
                monitor.put("groupEmail", groupEmail);
                monitor.put("port", resultSet.getString("port"));
                monitor.put("status", MonitorStatus.valueOf(resultSet.getString("status")).getValue());
                monitorList.add(monitor);
            }
        }
        return monitorList;
    }

    public List<Monitor> getAllMonitors() throws Exception {
        Class.forName(driverName);
        String query = String.format("SELECT * FROM monitors where enabled = true");
        List<Monitor> monitorList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(repoUrl, repoUser, repoUserPswd);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (Objects.requireNonNull(resultSet).next()) {
                Monitor monitor = renderMonitorFromResults(resultSet);
                monitorList.add(monitor);
            }
        }
        return monitorList;
    }


    public Monitor getMonitorFromMonitorName(String monitorName) throws Exception {
        Class.forName(driverName);
        String query = String.format("SELECT * FROM monitors where monitorName = '%s'", monitorName);
        Monitor monitor;
        try (Connection connection = DriverManager.getConnection(repoUrl, repoUser, repoUserPswd);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            Objects.requireNonNull(resultSet).next();
            monitor = renderMonitorFromResults(resultSet);
        }
        return monitor;
    }

    public boolean deleteMonitor(String monitorId) throws Exception {
        String query = "DELETE FROM monitors WHERE id = '" + monitorId + "'";
        return executeUpdate(query);
    }

    public void setMonitorStatus(String monitorName, MonitorStatus status) throws Exception {
        String query = String.format("UPDATE monitors SET status = '%s' WHERE monitorName = '%s'", status.name(), monitorName);
        executeUpdate(query);
    }

    public boolean addExecutionStatus(int monitorId, Date date, ExecutionStatus executionStatus, boolean isSuccess) throws Exception {
        int isSuccessInt = isSuccess ? 1: 0;
        String query = "INSERT INTO monitor_executions(monitorId, executionDate, executionStatus, isSuccess) " +
                "VALUES('" + monitorId + "', '" +  new java.sql.Date(date.getTime()) + "', '" + executionStatus + "', '" + isSuccessInt + "');";
        return executeUpdate(query);
    }


    public JSONArray getMonitorExecutionDetails(String monitorId) throws Exception {
        Class.forName(driverName);
        String query = String.format(
                "SELECT executionDate, AVG(isSuccess)*100 FROM monitor_executions " +
                        "where monitorId = '%s' GROUP BY executionDate " +
                        "ORDER BY executionDate DESC LIMIT 30", monitorId);
        try (Connection connection = DriverManager.getConnection(repoUrl, repoUser, repoUserPswd);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            JSONArray resultArray = new JSONArray();
            while (resultSet.next()) {
                JSONObject result = new JSONObject();
                result.put("Date", resultSet.getDate(1));
                result.put("successPercentage", resultSet.getDouble(2));
                resultArray.put(result);
            }
            return resultArray;
        }
    }

    private Monitor renderMonitorFromResults(ResultSet resultSet) throws SQLException {
        Monitor monitor = new Monitor();
        monitor.id = resultSet.getInt("id");
        monitor.monitorName = resultSet.getString("monitorName");
        monitor.enabled = resultSet.getBoolean("enabled");
        monitor.hostName = resultSet.getString("hostName");
        monitor.alertDays = Long.parseLong(resultSet.getString("alertDays"));
        monitor.groupEmail = resultSet.getString("groupEmail");
        monitor.port =  resultSet.getString("port");
        monitor.status =  MonitorStatus.valueOf(resultSet.getString("status"));
        monitor.alertSent = resultSet.getBoolean("alertSent");
        return monitor;
    }

    private String getGroupEmailOfUser(String userId) throws Exception {
        String query = "SELECT groupId FROM users where userName = '" + userId + "'";
        return executeQuery(query);
    }

    private String executeQuery(String query) throws Exception {
        String result = null;
        Class.forName(driverName);
        try (Connection connection = DriverManager.getConnection(repoUrl, repoUser, repoUserPswd);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result = resultSet.getString(1);
                System.out.println("SQL result : " + result);
            }
        }
        return result;
    }


    private boolean executeUpdate(String query) throws Exception {
        Class.forName(driverName);
        try (Connection connection = DriverManager.getConnection(repoUrl, repoUser, repoUserPswd);
             Statement statement = connection.createStatement()) {
            int resultInt = statement.executeUpdate(query);
            if (resultInt == 1) return true;
        }
        return false;
    }

    public boolean updateAlertSentStatus(int id, boolean alertSent) throws Exception {
        String query = "update monitors set alertSent=" + alertSent + " where id=" + id + ";";
        return executeUpdate(query);
    }
}
