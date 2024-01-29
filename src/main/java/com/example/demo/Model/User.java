package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    @Column(name = "filename")
    private String filename;
    private String email;
    private String name;
    private String discord;
    private String subscribers;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date")
    private Date registrationDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_active")
    private Date lastActive;
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private List<Articles> articles;
    private String customNickname;
    private String favoriteColor;
    private String effects;
}

