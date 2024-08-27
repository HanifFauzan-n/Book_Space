package com.library.jafa.dto.officer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordLoanReqDto {

    private String borrowerName;
    private String borrowedBook; // yyyy - MM - dd ISO_8601
    
}
