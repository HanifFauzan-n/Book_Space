package com.library.jafa.services.auth;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.jafa.entities.PasswordResetToken;
import com.library.jafa.entities.Users;
import com.library.jafa.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void forgotPassword(String email) throws Exception {
        Users user = userRepository.findEmailByUserName(email);
        if (user != null) {
            // Create and save password reset token
            PasswordResetToken token = passwordResetTokenService.createToken(user);
            
            // Send reset password email with token
            emailService.sendResetPasswordEmail(user.getUserName(), token.getToken());
        } else {
            throw new Exception("User not found with email: " + email);
        }
    }
    

    public void updatePassword(String token, String newPassword) throws Exception {
        PasswordResetToken resetToken = passwordResetTokenService.findByToken(token);
        if (resetToken != null && resetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            Users user = resetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            passwordResetTokenService.deleteToken(resetToken);
        } else {
            throw new Exception("Invalid or expired token.");
        }
    }
}
