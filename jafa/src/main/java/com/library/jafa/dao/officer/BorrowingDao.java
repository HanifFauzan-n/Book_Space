package com.library.jafa.dao.officer;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.entities.BorrowingBook;

public interface  BorrowingDao {
        PageResponse<BorrowingBook> findAll(String member,String book,int page,int size, String sortBy, String sortOrder);

}
