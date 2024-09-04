package com.library.jafa.services.officer;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.dto.officer.RecordLoanReqDto;
import com.library.jafa.dto.officer.RecordLoanResponseDto;
import com.library.jafa.entities.BorrowingBook;

public interface RecordLoanService {
    RecordLoanResponseDto recordLoans(RecordLoanReqDto dto);

        PageResponse<BorrowingBook> findAll(String member, String book, int page, int size, String sortBy,
            String sortOrder);

    // RecordLoanResponseDto updateOfficer(String id, RecordLoanReqDto dto);
}
