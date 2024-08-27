package com.library.jafa.entities;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Bookshelf")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookshelf {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "category_book")
    private String categoryBook;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "fill_bookshelf")
    private Integer fillBookshelf;

    @Column(name = "description_bookshelf")
    private String descriptionBookshelf;

    
}
