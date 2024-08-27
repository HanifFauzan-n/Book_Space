package com.library.jafa.services.officer;

import com.library.jafa.dto.officer.ReturnBookReqDto;
import com.library.jafa.dto.officer.ReturnBookResponseDto;

public interface ReturnBookService {
    ReturnBookResponseDto returnBook(ReturnBookReqDto dto);
}
