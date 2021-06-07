package com.cert;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CredentialManager {

    @Autowired
    private CertRepository certRepository;

    public boolean validateCredentials(String userName, String password) throws Exception {
        String userPassword = certRepository.getPasswordOfUser(userName);
        if (Objects.nonNull(userPassword) && userPassword.equals(password)) {
            System.out.println("Credential validation successful for the UserName : " + userName);
            return true;
        } else {
            System.out.println("Invalid Credentials.!");
            return false;
        }
    }

    public boolean createAccount(String email, String pswd, String groupEmail) throws Exception {
        if (!certRepository.isAccountAlreadyExist(email)) {
            System.out.println("Creating new account for the user ID : " + email);
            return certRepository.createAccount(email, pswd, groupEmail);
        } else {
            System.out.println("User account already exists");
            throw new Exception("User account already exists");
        }
    }

    public boolean update(String monitorName, String hostName, String alertDays, String groupEmail, String port) throws Exception {
        if (!certRepository.isMonitorExist(monitorName)) {
            System.out.println("Creating new account for the user ID : " + monitorName);
            return certRepository.updateUser(monitorName,hostName,alertDays, groupEmail, port);
        } else {
            System.out.println("User account already exists");
            throw new Exception("User account already exists");
        }
    }



}
