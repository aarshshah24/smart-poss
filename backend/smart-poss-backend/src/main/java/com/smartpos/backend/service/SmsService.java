package com.smartpos.backend.service;

public interface SmsService {
    void sendWhatsAppBill(String toMobile, String customerName, String outletName, double amount, String orderId);
}