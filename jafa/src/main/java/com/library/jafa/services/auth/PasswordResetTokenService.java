package com.library.jafa.services.auth;

import com.library.jafa.entities.PasswordResetToken;
import com.library.jafa.entities.Users;

public interface PasswordResetTokenService {

    PasswordResetToken createToken(Users user);

    PasswordResetToken findByToken(String token);

    void deleteToken(PasswordResetToken token);
}
