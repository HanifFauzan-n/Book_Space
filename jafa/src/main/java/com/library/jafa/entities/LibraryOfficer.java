package com.library.jafa.entities;

import java.sql.Blob;

import org.hibernate.annotations.UuidGenerator;

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
@Table(name = "library_officer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryOfficer {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "officer_name")
    private String officerName;

    @Column(name = "officer_age")
    private Integer officerAge;

    @Column(name = "officer_address")
    private String officerAddress;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "photo_officer")
    private Blob photoOfficer;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users users;

}
