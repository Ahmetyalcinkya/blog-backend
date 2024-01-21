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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService,UserDetailsService {

    private UserRepository userRepository;
    private UserService userService;
    private ModelMapperService modelMapperService;

    @Autowired
    public UserManager(UserRepository userRepository,ModelMapperService modelMapperService, UserService userService) {
        this.userRepository = userRepository;
        this.modelMapperService = modelMapperService;
        this.userService = userService;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user ->
                modelMapperService.forResponse().map(user, UserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserByID(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("I")); //TODO Throw exception -> User not found I

        return modelMapperService.forResponse().map(user, UserResponse.class);
    }

    @Override
    public void getUserByEmail(String email) {
        boolean userExist = userRepository.findUserByEmail(email).isPresent();
        if(userExist) throw new RuntimeException("J"); //TODO Throw exception -> User already exist J
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
    public UserResponse deleteUser(long id) {
        String email = userService.getAuthenticatedUser();
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()){
            if (optionalUser.get().getAuthority().getAuthority().equals("ADMIN")){
                List<User> users = userRepository.findAll();
                for(User user: users){
                    if (user.getId() == id){
                        userRepository.delete(user);
                        return modelMapperService.forResponse().map(user, UserResponse.class);
                    }
                }
            }
            throw new RuntimeException("S"); //TODO Throw exception -> Access denied S
        }
        throw new RuntimeException("R"); //TODO Throw exception -> User not authenticated R
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
        return userRepository.findUserByEmail(username).orElseThrow(() -> new RuntimeException("K")); //TODO Throw exception -> User not valid K
    }
}
