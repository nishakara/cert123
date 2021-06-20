package com.cert;

import com.cert.model.ExecutionStatus;
import com.cert.model.Monitor;
import com.cert.model.MonitorOpResultWrapper;
import com.cert.model.MonitorOpResultWrapper.Status;
import com.cert.model.MonitorStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class CertProcessor {

    @Autowired
    private CertRepository certRepository;


    public MonitorOpResultWrapper getCertInfo(String monitorName) throws Exception {
        Monitor monitor = certRepository.getMonitorFromMonitorName(monitorName);
        return getCertInfo(monitor);
    }

    public MonitorOpResultWrapper getCertInfo(Monitor monitor) throws Exception {
        JSONObject response = new JSONObject();
        Status status;
        String monitorName = monitor.monitorName;
        try {
            X509Certificate certificate = getCertificateOfMonitor(monitor);
            Date expDate = certificate.getNotAfter();
            response.put("valid_from", certificate.getNotBefore().toString());
            response.put("valid_to", expDate.toString());
            response.put("issuer_dn", certificate.getIssuerDN());
            long differenceInMillis = expDate.getTime() - new Date().getTime();
            long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
            response.put("diff_in_days", differenceInDays);
            response.put("status", "Success");
            response.put("execution_status", ExecutionStatus.SUCCESS);
            certRepository.setMonitorStatus(monitorName, MonitorStatus.OK);
            status = Status.SUCCESS;
        } catch (SSLHandshakeException e) {
            response.put("status", "Failed");
            response.put("message", "Certificate Already Expired");
            response.put("execution_status", ExecutionStatus.EXPIRED);
            System.out.println("Certificate Already Expired");
            certRepository.setMonitorStatus(monitorName, MonitorStatus.FAILED);
            status = Status.ERROR;
        } catch (Exception e) {
            response.put("status", "Failed");
            response.put("message", e.getMessage());
            response.put("execution_status", ExecutionStatus.FAILED);
            certRepository.setMonitorStatus(monitorName, MonitorStatus.FAILED);
            status = Status.ERROR;
        }
        System.out.println("Cert-Info response : " + response);
        return new MonitorOpResultWrapper(status, response);
    }

    public X509Certificate getCertificateOfMonitor(Monitor monitor) throws Exception {
        URL destinationURL = new URL(monitor.generateUrl());
        HttpsURLConnection conn = (HttpsURLConnection) destinationURL.openConnection();
        conn.connect();
        Certificate[] certs = conn.getServerCertificates();
        Certificate cert = certs[0];
        if (cert instanceof X509Certificate) {
            return (X509Certificate) cert;
        } else {
            System.err.println("Unidentified Certificate type");
            throw new Exception("Unidentified Certificate type");
        }
    }

}
