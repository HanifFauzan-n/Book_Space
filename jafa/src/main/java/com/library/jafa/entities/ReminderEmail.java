package com.library.jafa.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "reminder_emails")
public class ReminderEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")   
    private String email;

    @Column(name = "message")   
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReminderEmail(Long id, String email, String message) {
        this.id = id;
        this.email = email;
        this.message = message;
    }

    
}
