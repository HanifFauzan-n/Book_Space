package com.library.jafa.entities;

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
@Table(name = "borrowing_book")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingBook {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "loan_date")
    private LocalDate loanDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "book_id",referencedColumnName = "id" , nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id",referencedColumnName = "id" , nullable = false)
    private Member member;
}
