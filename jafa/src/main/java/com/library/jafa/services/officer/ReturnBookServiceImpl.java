package com.library.jafa.services.officer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.library.jafa.dto.officer.ReturnBookReqDto;
import com.library.jafa.dto.officer.ReturnBookResponseDto;
import com.library.jafa.entities.BorrowingBook;
import com.library.jafa.entities.ReturnBook;
import com.library.jafa.repositories.BorrowingBookRepository;
import com.library.jafa.repositories.ReturnBookRepository;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ReturnBookServiceImpl implements ReturnBookService {

    @Autowired
    ReturnBookRepository returnBookRepository;

    @Autowired
    BorrowingBookRepository borrowingBookRepository;

    @Override
    @Transactional
    public ReturnBookResponseDto returnBook(ReturnBookReqDto dto) {
        if(dto.getBookTitle() == null || dto.getBookTitle().isEmpty() || dto.getBookTitle().isBlank()){
            throw new IllegalArgumentException("Book name cannot be empty.");
        }
        if(dto.getBorrowerName() == null || dto.getBorrowerName().isEmpty() || dto.getBorrowerName().isBlank()){
            throw new IllegalArgumentException("Borrower name cannot be empty.");
        }
        
        // Find the book to be returned
        BorrowingBook borrowingBook = borrowingBookRepository.findByDescriptionAndMember_MemberNameAndBook_BookTitle("Borrowed", dto.getBorrowerName(), dto.getBookTitle());
        if (borrowingBook == null) {
            throw new IllegalArgumentException("Borrowing data not found.");
        }
        
        // Update the borrowing status of the book to "Returned"
        borrowingBook.setDescription("Returned");
        borrowingBook.setReturnDate(LocalDate.now());
        borrowingBookRepository.save(borrowingBook);
        
        // Save return book data
        ReturnBook returnBook = new ReturnBook();
        returnBook.setId(UUID.randomUUID().toString()); 
        returnBook.setReturnDate(LocalDate.now());
        returnBook.setBook(borrowingBook.getBook());
        returnBook.setMember(borrowingBook.getMember());
        returnBookRepository.save(returnBook);
        
        return new ReturnBookResponseDto(dto.getBorrowerName(), dto.getBookTitle(), "Book has been returned successfully.");
    }        
}
