package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.dto.request.UserSaveRequest;
import com.blog.BlogBackend.dto.response.UserResponse;
import com.blog.BlogBackend.entities.Token;
import com.blog.BlogBackend.entities.User;
import com.blog.BlogBackend.repositories.UserRepository;
import com.blog.BlogBackend.services.abstracts.ModelMapperService;
import com.blog.BlogBackend.services.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService,UserDetailsService {

    private UserRepository userRepository;
    private ModelMapperService modelMapperService;

    @Autowired
    public UserManager(UserRepository userRepository,ModelMapperService modelMapperService) {
        this.userRepository = userRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user ->
                modelMapperService.forResponse().map(user, UserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserByID(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("")); //TODO Throw exception

        return modelMapperService.forResponse().map(user, UserResponse.class);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new RuntimeException("")); //TODO Throw exception
        return modelMapperService.forResponse().map(user, UserResponse.class);
    }

    @Override
    public UserResponse saveUser(UserSaveRequest userSaveRequest) {
        User user = modelMapperService.forRequest().map(userSaveRequest, User.class);
        userRepository.save(user);
        return modelMapperService.forResponse().map(user, UserResponse.class);
    }

    @Override
    public void updateUser(Token token, User user) {
        user.setToken(token);
    }

    @Override
    public UserResponse deleteUser(long id) { //TODO WARNING!!!!!
        return null;
    }

    @Override
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    @Override
    public String getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new RuntimeException("")); //TODO Throw exception -> User not valid
    }
}
