package com.library.jafa.services.auth;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendResetPasswordEmail(String to, String resetToken);

}
