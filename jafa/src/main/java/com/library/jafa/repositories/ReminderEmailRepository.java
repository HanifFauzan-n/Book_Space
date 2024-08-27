package com.library.jafa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.jafa.entities.ReminderEmail;

@Repository
public interface ReminderEmailRepository extends JpaRepository<ReminderEmail, Long> {
    
}
