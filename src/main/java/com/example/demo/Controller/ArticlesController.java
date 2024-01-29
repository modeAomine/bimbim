package com.example.demo.Controller;

import com.example.demo.Impl.ArticlesImpl;
import com.example.demo.Impl.UserService;
import com.example.demo.Model.Articles;
import com.example.demo.Model.User;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping
public class ArticlesController {

    private final UserService userService;

    private final ArticlesImpl articlesService;

    @GetMapping("/articles")
    public String articles(Model model, Articles articles) {
        model.addAttribute("user", userService.authUser());
        model.addAttribute("artic", articles);
        return "html/articles";
    }
    @PostMapping("/artic/save")
    public String save(Articles articles, User user) {
        Articles savedArticle = articlesService.save(articles, user);
        if (savedArticle != null) {
            return "redirect:/articles";
        } else {
            return "redirect:/articles";
        }
    }

}
