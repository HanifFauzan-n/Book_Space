package com.library.jafa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.jafa.entities.BorrowingBook;

public interface BorrowingBookRepository extends JpaRepository<BorrowingBook, String> {
    @Query
    BorrowingBook findByDescription(String name);

    BorrowingBook findByDescriptionAndMember_MemberNameAndBook_BookTitle(String description, String memberName, String bookTitle);

}
