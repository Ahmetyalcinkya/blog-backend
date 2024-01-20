package com.blog.BlogBackend.controllers;

import com.blog.BlogBackend.dto.request.UserSaveRequest;
import com.blog.BlogBackend.dto.response.UserResponse;
import com.blog.BlogBackend.services.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public UserResponse getUserByID(@PathVariable long id){
        return userService.getUserByID(id);
    }
    @PostMapping("/admin/saveUser")
    public UserResponse saveUser(@RequestBody UserSaveRequest userSaveRequest){
        return userService.saveUser(userSaveRequest);
    }
    @DeleteMapping("/admin/deleteUser/{id}")
    public UserResponse deleteUser(@PathVariable long id){
        return userService.deleteUser(id);
    }

}
