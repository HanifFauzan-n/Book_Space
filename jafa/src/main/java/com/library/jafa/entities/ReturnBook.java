package com.library.jafa.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "return_book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBook {
    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private Book book;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;
}
