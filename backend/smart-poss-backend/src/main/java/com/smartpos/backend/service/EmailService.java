package com.smartpos.backend.service;

public interface EmailService {
    void sendApprovalMail(String toEmail);
    void sendRejectionMail(String toEmail);
}
