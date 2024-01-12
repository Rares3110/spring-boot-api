package com.rares.articlehub.controller;

import com.rares.articlehub.dto.UserRequest;
import com.rares.articlehub.dto.UserResponse;
import com.rares.articlehub.mapper.UserMapper;
import com.rares.articlehub.model.User;
import com.rares.articlehub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getStudentById(@PathVariable int id) {
        Optional<User> userOptional = userService.findUserById(id);

        return userOptional.map(user -> ResponseEntity.ok().body(userMapper.convertUserToResponse(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getStudentById(@PathVariable String email) {
        Optional<User> userOptional = userService.findUserByEmail(email);

        return userOptional.map(user -> ResponseEntity.ok().body(userMapper.convertUserToResponse(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
        User user = userService.saveUser(userMapper.convertRequestToUser(userRequest));
        return ResponseEntity.ok().body(userMapper.convertUserToResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable int id,
                                           @RequestParam(required = false) String username,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String password) {
        Optional<User> userOptional = userService.findUserById(id);
        if(userOptional.isEmpty())
            return ResponseEntity.notFound().build();

        userService.updateUser(id, username, email, password);
        userOptional = userService.findUserById(id);
        return userOptional.map(user -> ResponseEntity.ok().body(userMapper.convertUserToResponse(user)))
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }
}
