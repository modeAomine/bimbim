package com.example.demo.Repo;

import com.example.demo.Model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlesRepo extends JpaRepository<Articles, Long> {
    Articles findByTitle (String title);
}
