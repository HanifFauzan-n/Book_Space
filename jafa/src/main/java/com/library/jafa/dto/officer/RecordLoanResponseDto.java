package com.library.jafa.dto.officer;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordLoanResponseDto {

    private String borrowerName;
    private String borrowedBook;
    private String categoryBook;
    private LocalDate loanDate;// yyyy - MM - dd ISO_8601
    private LocalDate returnDate;
    private String status;
    private String penalties;
    private String note;

}
