package com.library.jafa.dto.officer;

import lombok.Data;

@Data
public class ReturnBookResponseDto {
    private String borrowerName;
    private String bookName;
    private String status;

    public ReturnBookResponseDto(String borrowerName, String bookName, String status) {
        this.borrowerName = borrowerName;
        this.bookName = bookName;
        this.status = status;
    }
}
