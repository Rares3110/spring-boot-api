package com.rares.articlehub.mapper;

import com.rares.articlehub.dto.UserRequest;
import com.rares.articlehub.dto.UserResponse;
import com.rares.articlehub.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse convertUserToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getUserRole()
        );
    }

    public User convertRequestToUser(UserRequest userRequest) {
        return new User(
                userRequest.getUsername(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getUserRole()
        );
    }
}
