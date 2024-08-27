package com.library.jafa.dao.book;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.entities.Bookshelf;

public interface BookshelfDao {
        PageResponse<Bookshelf> findAll(String category,Integer capacity ,int page,int size, String sortBy, String sortOrder);

}
