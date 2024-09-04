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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "member_name")   
    private String memberName;

    @Column(name = "member_age")
    private Integer memberAge;

    @Column(name = "gender",length = 25)
    private String gender;

    @Column(name = "address")
    private String address;

    
    @Column(name = "photo_member")
    private Blob photoMember;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id" , nullable = false)
    private Users user;

    


}
