package com.projectment.rest.controller;


import com.projectment.repository.UserRepository;
import com.projectment.security.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<String> getUserMails() {
        return userRepository.findAll().stream().map(User::getEmail).toList();
    }

}
