package com.library.jafa.dto.officer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReturnBookReqDto {
    private String borrowerName;
    private String bookTitle;
    private LocalDate dateReturnBook;
}
