package com.library.jafa.entities;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @UuidGenerator
    @Column(name = "id",length = 36, nullable = false )
    private String id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password",length = 2000)
    private String password;

    @Column(name = "reset_token")
    private String resetToken;

    @ManyToOne
    @JoinColumn(name="role_id",referencedColumnName = "id",nullable = false)
    private Roles role;


}