package com.library.jafa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.library.jafa.entities.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {

    PasswordResetToken findByToken(String token);

    
}
