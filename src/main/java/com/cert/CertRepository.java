package com.cert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;


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
        String query = "SELECT pswd from Users where userName='" + userName + "'";
        return executeQuery(query);
    }

    public boolean isAccountAlreadyExist(String email) throws Exception {
        String query = "SELECT userName from Users where userName = '" + email + "'";
        String result = executeQuery(query);
        return Objects.nonNull(result);
    }

    public boolean createAccount(String email, String pswd, String groupEmail) throws Exception {
        String query = "INSERT INTO users(userName, pswd, groupId) " +
                "VALUES('" + email + "', '" + pswd + "', '" + groupEmail + "');";
        return executeInsert(query);
    }

    public boolean isMonitorExist(String monitorName) throws Exception {
        String query = "SELECT monitorName FROM monitors where monitorName = '" + monitorName + "'";
        String result = executeQuery(query);
        return Objects.nonNull(result);
    }

    public boolean createMonitor(String monitorName, String hostName, String alertDays, String groupEmail, String port) throws Exception {
        String query = "INSERT INTO monitors(monitorName, hostName, alertDays, groupEmail, port) " +
                "VALUES('" + monitorName + "', '" + hostName + "', '" + alertDays + "', '" + groupEmail +"', '" + port + "' );";
        return executeInsert(query);
    }

    public List<Map> getMonitorsForUser(String userId) throws Exception {
        Class.forName(driverName);
        String groupEmail = getGroupEmailOfUser(userId);
        String query = String.format("SELECT monitorName, enabled, hostname, alertDays, port, status FROM monitors where groupEmail = '%s'", groupEmail);
        List<Map> monitorList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(repoUrl, repoUser, repoUserPswd);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (Objects.requireNonNull(resultSet).next()) {
                Map<String, String> monitor = new HashMap<>();
                monitor.put("monitorName", resultSet.getString("monitorName"));
                monitor.put("enabled", String.valueOf(resultSet.getBoolean("enabled")));
                monitor.put("hostName", resultSet.getString("hostName"));
                monitor.put("alertDays", resultSet.getString("alertDays"));
                monitor.put("groupEmail", groupEmail);
                monitor.put("port", resultSet.getString("port"));
                monitor.put("status", resultSet.getString("status"));
                monitorList.add(monitor);
            }
        }
        return monitorList;
    }

    public boolean deleteMonitor(String monitorName) throws Exception {
        String query = "DELETE FROM monitors WHERE monitorName = '" + monitorName + "'";
        return executeInsert(query);
    }


    public boolean updateUser(String monitorName, String hostName, String alertDays, String groupEmail, String port) throws Exception {
         //  String groupEmail = getGroupEmailOfUser(userId);
           String query = String.format("Update  monitors Set hostname= '"+hostName+"', alertDays= '"+alertDays+"'," +
                   " port='"+port+"', groupEmail ='"+groupEmail+"' where monitorName = '%s'", monitorName);
        return executeInsert(query);

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



    private boolean executeInsert(String query) throws Exception {
        Class.forName(driverName);
        try (Connection connection = DriverManager.getConnection(repoUrl, repoUser, repoUserPswd);
             Statement statement = connection.createStatement()) {
            int resultInt = statement.executeUpdate(query);
            if (resultInt == 1) return true;
        }
        return false;
    }
}
