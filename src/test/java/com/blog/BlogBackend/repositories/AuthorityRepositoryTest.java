package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuthorityRepositoryTest {

    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityRepositoryTest(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Test
    void findByAuthority() {
        Authority authority = authorityRepository.findByAuthority("USER");
        assertNotNull(authority);
        System.out.println(authority.getAuthority());
    }
    @Test
    void findByAuthorityFail() {
        Authority authority = authorityRepository.findByAuthority("STORE");
        assertNull(authority);
    }
}