package com.rares.articlehub.service;

import com.rares.articlehub.model.User;
import com.rares.articlehub.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Page<User> findPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void updateUser(int id, String username, String email, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("user does not exist"));

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
