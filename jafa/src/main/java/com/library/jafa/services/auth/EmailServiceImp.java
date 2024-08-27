package com.library.jafa.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@library.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // Set to true for HTML content
            emailSender.send(message);
        } catch (MailException | MessagingException me) {
            me.printStackTrace();
        }
    }

    @Override
    public void sendResetPasswordEmail(String to, String resetToken) {
        try {
            String subject = "Reset Password";
            String text =  "<html>"
            + "<body style='background-color: #ADD8E6; padding: 20px;'>"
            + "<div style='background-color: #FFFFFF; padding: 20px; border-radius: 10px;'>"
            + "<p>Dear User,</p>"
            + "<p>You have requested to reset your password. Please input this token to reset your password:</p>"
            + "<p style='color: blue; font-weight: bold;'>Your token :</p>"
            + "<p style='color: blue; font-weight: bold;'>" + resetToken + "</p>"
            + "<p>If you did not request this, please ignore this email.</p>"
            + "<p>Regards,<br/>Your App Team</p>"
            + "</div>"
            + "</body>"
            + "</html>";

            sendSimpleMessage(to, subject, text);
        } catch (MailException e) {
            e.printStackTrace(); // Log the exception or handle it as needed
            throw new RuntimeException("Failed to send reset password email to " + to);
        }
    }
}
