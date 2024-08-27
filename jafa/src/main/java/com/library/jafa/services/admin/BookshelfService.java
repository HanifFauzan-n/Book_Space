package com.library.jafa.services.admin;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.dto.admin.BookshelfRequestDto;
import com.library.jafa.entities.Bookshelf;

public interface BookshelfService {
    Bookshelf addBookshelf(BookshelfRequestDto dto);

    String removeBookshelf(String id);

    Bookshelf updateBookshelf(String id, BookshelfRequestDto dto);

    PageResponse<Bookshelf> findAll(String category, Integer capacity, int page, int size, String sortBy, String sortOrder);

}
