package com.library.jafa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.jafa.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin,String> {
    
}
