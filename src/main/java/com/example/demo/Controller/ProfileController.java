package com.example.demo.Controller;

import com.example.demo.Impl.UserService;
import com.example.demo.Model.User;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping
public class ProfileController {
    private final UserService userService;
    @GetMapping("/profile")
    public String profilePage(Model model) {
        User user = userService.authUser();
        model.addAttribute("user", user);
        return "html/profile";
    }
}
