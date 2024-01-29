package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "articles")
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articles_id")
    private Long id;
    private String title;
    private String description;
    private String articlesFileName;
    private String links;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
