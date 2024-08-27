package com.library.jafa.services.admin;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.dto.admin.BookRequestDto;
import com.library.jafa.entities.Book;

public interface BookService {
    Book addBook(BookRequestDto dto);
    String removeBook(String id);
    Book updateBook(String id, BookRequestDto dto);
    void uploadBookPhoto(String id, MultipartFile photo) throws IOException, SQLException;
    PageResponse<Book> findAll(String author,String statusBook,String category ,int page,int size, String sortBy, String sortOrder);

}
