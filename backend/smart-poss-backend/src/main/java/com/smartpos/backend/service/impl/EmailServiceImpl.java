package com.smartpos.backend.service.impl;

import com.smartpos.backend.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Load email template from resources/email folder
    private String loadTemplate(String fileName) throws Exception {

        ClassPathResource resource = new ClassPathResource("email/" + fileName);

        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    // Send approval email
    @Async
    @Override
    public void sendApprovalMail(String toEmail) {

        System.out.println("Sending approval email to: " + toEmail);

        sendHtmlMail(
                toEmail,
                "Your Smart POSS Account is Approved",
                "approved.html"
        );
    }

    // Send rejection email
    @Async
    @Override
    public void sendRejectionMail(String toEmail) {

        System.out.println("Sending rejection email to: " + toEmail);

        sendHtmlMail(
                toEmail,
                "Smart POSS Account Update",
                "rejected.html"
        );
    }

    // Common method for sending HTML email
    private void sendHtmlMail(String to, String subject, String templateName) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("noreplysmartposs@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);

            String htmlContent = loadTemplate(templateName);

            helper.setText(htmlContent, true);

            mailSender.send(message);

            System.out.println("Email sent successfully to: " + to);

        } catch (Exception e) {

            System.out.println("Email sending failed for: " + to);
            e.printStackTrace();
        }
    }
}