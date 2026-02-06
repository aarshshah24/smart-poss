package com.smartpos.backend.service.impl;

import com.smartpos.backend.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private String loadTemplate(String name) throws Exception {
        ClassPathResource resource = new ClassPathResource("email/" + name);

        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }    }

    @Override
    public void sendApprovalMail(String toEmail) {
        sendHtmlMail(toEmail, "Your Smart POSS Account is Approved", "approved.html");
    }

    @Override
    public void sendRejectionMail(String toEmail) {
        sendHtmlMail(toEmail, "Smart POSS Account Update", "rejected.html");
    }

    private void sendHtmlMail(String to, String subject, String template) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreplysmartposs@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);

            String html = loadTemplate(template);
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
