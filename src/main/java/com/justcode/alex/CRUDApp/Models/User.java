package com.justcode.alex.CRUDApp.Models;

import jakarta.persistence.*;

@Entity
@Table(name="users", schema = "public")
public class User {
    
    @Id // Primary DB Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; 
    @Column
    private String name;

    // ID get/set
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // First Name get/set
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}