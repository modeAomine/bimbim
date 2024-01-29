package com.example.demo.Impl;

import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    public User save(User user, Role roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationDate(user.getRegistrationDate());
        user.setRoles(Collections.singleton(roles));

        return userRepo.save(user);
    }

    public void newUserAdd(User user, Role roles) {
        User maymay = userRepo.findByUsername(user.getUsername());
        if (maymay != null) {
            throw new IllegalArgumentException("Пользователь с таким логином уже зарегистрирован!");
        }
        user.setRegistrationDate(new Date());
        user.setRoles(Collections.singleton(roles));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }



    public void updateUser(User user) {
        User searchUserId = userRepo.findById(user.getId())
                        .orElse(null);
        searchUserId.setUsername(user.getUsername());
        searchUserId.setDiscord(user.getDiscord());
        searchUserId.setEmail(user.getEmail());
        searchUserId.setName(user.getName());
        searchUserId.setFilename(user.getFilename());
        userRepo.save(searchUserId);
    }



    public void updateUserAvatar(User user, MultipartFile file) {
        User searchUserId = userRepo.findById(user.getId())
                .orElse(null);
        if (file != null && !file.isEmpty()) {
            String filename = fileStorageService.storeFile(file);
            String oldFilename = searchUserId.getFilename();
            if (oldFilename != null) {
                fileStorageService.deleteFile(oldFilename);
            }
            searchUserId.setFilename(filename);
            searchUserId.setUsername(user.getUsername());
            searchUserId.setPassword(user.getPassword());
            searchUserId.setEmail(user.getEmail());
            searchUserId.setName(user.getName());
            searchUserId.setDiscord(user.getDiscord());
            searchUserId.setRegistrationDate(user.getRegistrationDate());
        }
        userRepo.save(user);
    }

    public void updateAvatar(Long id, MultipartFile file) {
        User search = userRepo.findById(id)
                .orElse(null);
        if (file != null && !file.isEmpty()) {
            String filename = fileStorageService.storeFile(file);
            String oldFilename = search.getFilename();
            if (oldFilename != null) {
                fileStorageService.deleteFile(oldFilename);
            }
            search.setFilename(filename);
        }
        userRepo.save(search);
    }




    public void someMethodToUpdateLastActive(User user) {
        user.setLastActive(new Date());
        userRepo.save(user);
    }


    public void delete(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        userRepo.delete(user);
    }


    public List<User> findAll() {
        return userRepo.findAll();
    }


    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }



    public User findByUsername(String username) { return userRepo.findByUsername(username); }


    public User findByLoginAndPassword(String username, String password) { return userRepo.findByUsernameAndPassword(username, password); }



    public User updateUserLogin(Long id, String username) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        user.setUsername(username);
        return userRepo.save(user);
    }


    public User updateUserPassword(Long id, String newPassword) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id:" + id));
        user.setPassword(newPassword);
        return userRepo.save(user);
    }


    public User updateConfirmPassword(Long id, String newConfirmPassword) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        user.setConfirmPassword(passwordEncoder.encode(newConfirmPassword));
        return userRepo.save(user);
    }


    public User updateUserDiscord(Long id, String newDiscord) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id:" + id));
        user.setDiscord(newDiscord);
        return userRepo.save(user);
    }


    public User updateUserEmail(Long id, String newEmail) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id:" + id));
        user.setEmail(newEmail);
        return userRepo.save(user);
    }


    public User updateUserName(Long id, String newName) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id:" + id));
        user.setName(newName);
        return userRepo.save(user);
    }


    public void registerAuthUser(User user) {
        User existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Пользователь с таким логином уже зарегистрирован!");
        }
        user.setRegistrationDate(new Date());
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public User authUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        User user = userRepo.findByUsername(userDetails.getUsername());
        return user;
    }

    public List<User> searchUser(String param, String paramTwo) {
        if (param != null && !param.isEmpty()) {
            return userRepo.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(param, paramTwo);
        } else {
            return findAll();
        }
    }
}
