package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.services.abstracts.AuthorityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuthorityManagerTest {

    private AuthorityService authorityService;

    @Autowired
    public AuthorityManagerTest(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @Test
    void getAuthority() {
    }
}