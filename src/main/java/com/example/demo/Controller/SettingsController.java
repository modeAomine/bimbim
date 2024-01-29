package com.example.demo.Controller;

import com.example.demo.Impl.UserService;
import com.example.demo.Model.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
@RequestMapping
public class SettingsController {

    private final UserService userService;


    @GetMapping("/settings")
    public String userSettings(Model model) {
        User user = userService.authUser();
        model.addAttribute("user", user);
        return "html/setting";
    }

    @PostMapping("/settings/updateUser")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/profile";
    }

    @PostMapping("/settings/updateAvatar")
    @ResponseBody
    public void updateAvatar(@RequestParam("id") Long id, @RequestParam("filename") MultipartFile filename) {
            userService.updateAvatar(id, filename);
    }


    @PostMapping("/settings/updateDiscord")
    @ResponseBody
    public ResponseEntity<User> updateDiscord(@RequestBody User user) {
        try {
            Long userId = user.getId();
            String newDiscord = user.getDiscord();
            userService.updateUserDiscord(userId, newDiscord);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/settings/updateLogin")
    @ResponseBody
    public ResponseEntity<User> updateLogin(@RequestBody User user) {
        try {
            Long userId = user.getId();
            String newLogin = user.getUsername();
            User updatedUser = userService.updateUserLogin(userId, newLogin);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/settings/updatePassword")
    @ResponseBody
    public ResponseEntity<User> updatePassword(@RequestBody User user) {
        try {
            Long userId = user.getId();
            String newPassword = user.getPassword();
            String newConfirmPassword = user.getConfirmPassword();
            User updatedUser = userService.updateUserPassword(userId, newPassword);
            User updateUserConfirmPassword = userService.updateConfirmPassword(userId, newConfirmPassword);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/settings/updateEmail")
    @ResponseBody
    public ResponseEntity<User> updateEmail(@RequestBody User user) {
        try {
            Long userId = user.getId();
            String newEmail = user.getEmail();
            User updatedUser = userService.updateUserEmail(userId, newEmail);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
