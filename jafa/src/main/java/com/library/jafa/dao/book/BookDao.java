package com.library.jafa.dao.book;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.entities.Book;

public interface BookDao {
        PageResponse<Book> findAll(String author,String statusBook, String category,int page,int size, String sortBy, String sortOrder);

}
