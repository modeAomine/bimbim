package com.example.demo.Controller;

import com.example.demo.Impl.UserService;
import com.example.demo.Model.User;
import com.example.demo.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.Model.Role;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final UserRepo repo;

    @GetMapping("/users")
    public String usersPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<User> users = userService.searchUser(param, param);
        model.addAttribute("users", users);
        model.addAttribute("param", param);
        return "html/users";
    }

    @GetMapping("/search")
    public String searchUserParam(Model model ,@RequestParam(value = "param", defaultValue = "", required = false) String param, User user) {
        userService.searchUser(param, param);
        model.addAttribute("param", param);
        return "html/profile";
    }

    @GetMapping("/users/add")
    public String pageAdd(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "html/add-users";
    }

    @PostMapping("/add")
    public String add(User user, Role role) {
        userService.newUserAdd(user, role);
        return "redirect:/users";
    }

    @PostMapping("/users/save")
    public String save(@ModelAttribute("user") User user, Role role) {
        userService.save(user, role);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", user);
        return "html/edit-user";
    }

    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Пользователь успешно удален");
        return "redirect:/users";
    }
}
