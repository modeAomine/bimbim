package com.example.demo.Impl;

import com.example.demo.Model.Articles;
import com.example.demo.Model.User;
import com.example.demo.Repo.ArticlesRepo;
import com.example.demo.Repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticlesImpl {

    private final ArticlesRepo articlesRepo;

    private final UserRepo userRepo;

    public Articles save(Articles articles, User user) {
        User existingUser = userRepo.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            articles.setUser(existingUser);
            return articlesRepo.save(articles);
        } else {
            return null;
        }
    }

}
