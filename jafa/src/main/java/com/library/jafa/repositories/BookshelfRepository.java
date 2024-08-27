package com.library.jafa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.jafa.entities.Bookshelf;

public interface BookshelfRepository extends JpaRepository<Bookshelf,String> {
        @Query
    Bookshelf findByCategoryBook(String name);
}
