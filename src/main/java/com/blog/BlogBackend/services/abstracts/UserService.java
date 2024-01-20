package com.blog.BlogBackend.services.abstracts;

import com.blog.BlogBackend.dto.request.UserSaveRequest;
import com.blog.BlogBackend.dto.response.UserResponse;
import com.blog.BlogBackend.entities.Token;
import com.blog.BlogBackend.entities.User;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();
    UserResponse getUserByID(long id);
    void getUserByEmail(String email);
    UserResponse saveUser(UserSaveRequest userSaveRequest);
    void updateUser(Token token, User user);
    UserResponse deleteUser(long id);
    int enableUser(String email);
    String getAuthenticatedUser();

}
