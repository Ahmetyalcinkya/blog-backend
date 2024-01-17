package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.dto.response.AuthorityResponse;
import com.blog.BlogBackend.repositories.AuthorityRepository;
import com.blog.BlogBackend.services.abstracts.AuthorityService;
import com.blog.BlogBackend.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityManager implements AuthorityService {

    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityManager(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public AuthorityResponse getAuthority() {
        return Converter.getAuthority(authorityRepository.findByAuthority("USER"));
    }
}
