package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Test
    void userNotFoundByEmail() {
        String nonExistingEmail = "abc@gmail.com";
        Optional<User> optionalUser = userRepository.findUserByEmail(nonExistingEmail);
        assertTrue(optionalUser.isEmpty());
    }
    @Test
    void findUserByEmail(){
        String existingEmail = "x@gmail.com";
        Optional<User> existingUser = userRepository.findUserByEmail(existingEmail);
        assertNotNull(existingUser.get());
    }

    @Test
    void enableUser() {
        String existingEmail = "x@gmail.com";
        Optional<User> existingUser = userRepository.findUserByEmail(existingEmail);

        if(existingUser.isPresent()){
            userRepository.enableUser(existingEmail);
        }
    }
    @Test
    void enableUserFail() {
        String nonExistingEmail = "abc@gmail.com";
        Optional<User> existingUser = userRepository.findUserByEmail(nonExistingEmail);

        if(existingUser.isEmpty()){
            userRepository.enableUser(nonExistingEmail);
        }
    }
}