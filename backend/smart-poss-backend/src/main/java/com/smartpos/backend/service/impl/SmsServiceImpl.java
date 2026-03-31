package com.smartpos.backend.service.impl;
import com.smartpos.backend.service.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.whatsapp_number}")
    private String fromNumber;

    @Override
    public void sendWhatsAppBill(String toMobile, String customerName, String outletName, double amount, String orderId) {
        try {
            // 1. Initialize Twilio
            Twilio.init(accountSid, authToken);

            // 2. Format the numbers for India (+91)
            String cleanNumber = toMobile.replaceAll("\\D", "");
            if (cleanNumber.length() == 10) {
                cleanNumber = "91" + cleanNumber;
            }
            String to = "whatsapp:+" + cleanNumber;

            // 3. Generate the Bill URL (Use your server IP for mobile testing)
            String billUrl = "http://localhost:8080/view-bill.html?id=" + orderId;

            // 4. Create the Message Body
            String messageBody = String.format(
                    "Hello %s! 👋\n\n" +
                            "Thank you for ordering via *Smart POSS*.\n" +
                            "--------------------------\n" +
                            "Total Amount: *₹%.2f*\n" +
                            "--------------------------\n\n" +
                            "Click the link below to view your digital receipt:\n" +
                            "%s\n\n" +
                            "Have a great day!",
                    customerName,
                    amount,
                    billUrl
            );

            // 5. Send the message
            Message message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(fromNumber),
                    messageBody
            ).create();

            System.out.println("WhatsApp Sent! SID: " + message.getSid());

        } catch (Exception e) {
            System.err.println("Twilio Error: " + e.getMessage());
        }
    }
}