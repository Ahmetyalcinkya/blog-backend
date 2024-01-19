package com.blog.BlogBackend.controllers;

import com.blog.BlogBackend.dto.request.LoginRequest;
import com.blog.BlogBackend.dto.request.UserSaveRequest;
import com.blog.BlogBackend.dto.response.LoginResponse;
import com.blog.BlogBackend.dto.response.UserResponse;
import com.blog.BlogBackend.services.concretes.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthorizationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/register")
    public UserResponse register(@RequestBody UserSaveRequest userSaveRequest){
        return authenticationService.register(userSaveRequest.getName(), userSaveRequest.getSurname(),
                userSaveRequest.getEmail(), userSaveRequest.getPassword(), userSaveRequest.getProfilePicture());
    }
    @PostMapping("/confirm")
    @ResponseBody
    public String confirm(@RequestParam(name = "emailToken") String emailToken){
        return authenticationService.confirmationEmailToken(emailToken);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return authenticationService.login(loginRequest.email(), loginRequest.password());
    }
}
