package com.cert;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class ClientController {

    @Autowired
    private CredentialManager credentialManager;

    @Autowired
    private MonitorManager monitorManager;

    @GetMapping("/")
    public String loadModelPage(Model model) {
        return "dashboard";
    }

    @GetMapping("/login")
    public String loadLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/monitors")
    public String loadMonitorsPage(Model model) {
        return "monitors";
    }

    @PostMapping("SubmitCredentials")
    public ResponseEntity<?> submitCredentials(Model model, @RequestParam String userName, @RequestParam String password) {
        System.out.println("Login request received : " + userName + "|" + password);
        try {
            if (credentialManager.validateCredentials(userName, password)) {
                JSONObject response = new JSONObject();
                response.put("message", "Login Success");
                response.put("username", userName);
                return new ResponseEntity<>(response.toString(), HttpStatus.OK);
            } else
                return new ResponseEntity<>("Invalid Credentials", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Login Failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("CreateAccount")
    public ResponseEntity<?> createAccount(Model model, @RequestParam String email, @RequestParam String pswd,
                                    @RequestParam String groupEmail) {
        System.out.println("Create-Account request received : " + email + "|" + pswd + "|" + groupEmail);
        try {
            if (credentialManager.createAccount(email, pswd, groupEmail)) {
                JSONObject response = new JSONObject();
                response.put("message", "Create Account Success");
                response.put("username", email);
                return new ResponseEntity<>(response.toString(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Account creation failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Account creation failed : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("CreateMonitor")
    public ResponseEntity<?> createMonitor(Model model, @RequestParam String monitorName, @RequestParam String hostName,
                                @RequestParam String alertDays,  @RequestParam String groupEmail, @RequestParam String port) throws Exception {
        System.out.println("Create-Monitor request received : " + monitorName + "|" + hostName + "|" + alertDays + "|" + port);
        try {
            JSONObject response = monitorManager.createMonitor(monitorName, hostName, alertDays, groupEmail, port);
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating monitor [" + monitorName +"], error : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getMonitors/{userId}")
    public ResponseEntity<?> getMonitors(@PathVariable("userId") String userId) {
        System.out.println("Get-Monitors request received for the user with ID : " + userId);
        try {
            List<Map> monitorList = monitorManager.getMonitorsForUser(userId);
            return new ResponseEntity<>(monitorList, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error occurred while retrieving monitors :" + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while retrieving monitors" + e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("RunMonitor")
    public String runMonitor(Model model, @RequestParam String monitorName) {
        System.out.println("Run-Monitor request received for the monitor : " + monitorName);
        try {
            // run monitor
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "monitors";
    }

    @DeleteMapping("DeleteMonitor")
    public ResponseEntity<?> deleteMonitor(Model model, @RequestParam String monitorName) {
        System.out.println("Delete-Monitor request received for the monitor : " + monitorName);
        try {
            JSONObject response = monitorManager.deleteMonitor(monitorName);
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting monitor [" + monitorName +"], error : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
