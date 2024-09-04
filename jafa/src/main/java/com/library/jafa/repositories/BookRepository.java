package com.library.jafa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.jafa.entities.Book;

public interface BookRepository extends JpaRepository<Book, String> {
    
    @Query
    Book findByBookTitle(String name);



}
