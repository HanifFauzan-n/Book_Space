package com.library.jafa.entities;

import java.sql.Blob;
import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "author")
    private String author;

    @Column(name = "recording_date")
    private LocalDate recordingDate;

    @Column(name = "photo_book")
    private Blob photoBook;

    @Column(name = "stock_book")
    private Integer stockBook;

    @Column(name = "status_book")
    private String statusBook;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "bookshelf_id", referencedColumnName = "id", nullable = false)
    private Bookshelf bookshelf;

}
