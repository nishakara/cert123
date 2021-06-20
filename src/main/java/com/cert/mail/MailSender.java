package com.cert.mail;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class MailSender {

    public static final String SYSTEM_ID = "CertMonitor";

    private Session session;
    private final String username;
    private final String password;
    private final String messageTemplate;
    private final String messageTemplateExpired;

    public MailSender(
            @Value("${mail.host}") String host,
            @Value("${mail.port}") String port,
            @Value("${mail.username}") String username,
            @Value("${mail.password}") String password,
            @Value("${mail.message.template}") String messageTemplate,
            @Value("${mail.message.template.expired}") String messageTemplateExpired) {

        this.username = username;
        this.password = password;
        this.messageTemplate = messageTemplate;
        this.messageTemplateExpired = messageTemplateExpired;

        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        createSession();
    }

    private void createSession(){
        Properties props = System.getProperties();
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void sendMail(String sender, String receiver, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        if (session == null){
            createSession();
        }
        try {
            System.out.println("Sending email :" + sender + ":" + receiver + ":" + subject + ":" + content);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, sender));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (AddressException|UnsupportedEncodingException e) {
            System.out.println("Error occurred with parsing email addresses" + e);
            throw e;
        } catch (MessagingException e) {
            System.out.println("Error occurred while sending email" + e);
            throw e;
        }
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public String getMessageTemplateExpired() {
        return messageTemplateExpired;
    }
}
