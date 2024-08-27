package com.library.jafa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.jafa.entities.ReturnBook;

public interface ReturnBookRepository extends JpaRepository<ReturnBook, String> {
}
