package com.library.jafa.dto.admin;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookshelfRequestDto {
    private String categoryBook;
    private Integer capacity;
    private String descriptionBookshelf;

}
