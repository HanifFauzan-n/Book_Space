package com.library.jafa.services.officer;

import com.library.jafa.dto.officer.RecordLoanReqDto;
import com.library.jafa.dto.officer.RecordLoanResponseDto;

public interface RecordLoanService {
    RecordLoanResponseDto recordLoans(RecordLoanReqDto dto);

    // RecordLoanResponseDto updateOfficer(String id, RecordLoanReqDto dto);
}
