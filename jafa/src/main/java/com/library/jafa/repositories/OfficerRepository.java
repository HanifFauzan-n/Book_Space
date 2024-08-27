package com.library.jafa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.jafa.entities.LibraryOfficer;

public interface OfficerRepository extends JpaRepository<LibraryOfficer,String> {
    
}
