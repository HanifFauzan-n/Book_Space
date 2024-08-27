package com.library.jafa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.jafa.entities.Users;

public interface UserRepository extends JpaRepository<Users,String> {

    Optional<Users> findByUserName(String username);
    Users findEmailByUserName(String email);
    Optional<Users> findByResetToken(String resetToken);

}
