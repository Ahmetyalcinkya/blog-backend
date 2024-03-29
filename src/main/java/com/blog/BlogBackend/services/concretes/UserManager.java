package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.dto.request.UserSaveRequest;
import com.blog.BlogBackend.dto.response.UserResponse;
import com.blog.BlogBackend.entities.Token;
import com.blog.BlogBackend.entities.User;
import com.blog.BlogBackend.exceptions.BlogException;
import com.blog.BlogBackend.repositories.UserRepository;
import com.blog.BlogBackend.services.abstracts.ModelMapperService;
import com.blog.BlogBackend.services.abstracts.UserService;
import com.blog.BlogBackend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        User user = userRepository.findById(id).orElseThrow(() -> new BlogException(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        return modelMapperService.forResponse().map(user, UserResponse.class);
    }

    @Override
    public void getUserByEmail(String email) {
        boolean userExist = userRepository.findUserByEmail(email).isPresent();
        if(userExist) throw new BlogException(Constants.USER_EXIST, HttpStatus.BAD_REQUEST);
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
        String email = getAuthenticatedUser();
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()){
            if (optionalUser.get().getAuthority().getAuthority().equals("ADMIN")){
                List<User> users = userRepository.findAll();
                for(User user: users){
                    if (user.getId() == id){
                        if(user.getAuthority().getAuthority().equals("ADMIN")){
                            throw new BlogException(Constants.CANNOT_DELETE_AN_ADMIN, HttpStatus.FORBIDDEN);
                        }
                        userRepository.delete(user);
                        return modelMapperService.forResponse().map(user, UserResponse.class);
                    }
                }
            }
            throw new BlogException(Constants.ACCESS_DENIED, HttpStatus.FORBIDDEN);
        }
        throw new BlogException(Constants.NOT_AUTHENTICATED, HttpStatus.FORBIDDEN);
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
        return userRepository.findUserByEmail(username).orElseThrow(() -> new BlogException(Constants.USER_NOT_VALID, HttpStatus.BAD_REQUEST));
    }
}
