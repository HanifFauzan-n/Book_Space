package com.library.jafa.dto.admin;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {
    private String bookTitle;
    private String author;
    private String descriptionBook;
    private String categoryBook;
    private Integer fill;

}
