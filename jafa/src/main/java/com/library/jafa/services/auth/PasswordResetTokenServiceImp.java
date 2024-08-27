package com.library.jafa.services.auth;

import com.library.jafa.entities.PasswordResetToken;
import com.library.jafa.entities.Users;
import com.library.jafa.repositories.PasswordResetTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImp implements PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;


    @Override
    public PasswordResetToken createToken(Users user) {
        // validasi(user);

        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusHours(1)); // Token expires in 1 hours
        return tokenRepository.save(token);
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void deleteToken(PasswordResetToken token) {
        tokenRepository.delete(token);
    }
}
