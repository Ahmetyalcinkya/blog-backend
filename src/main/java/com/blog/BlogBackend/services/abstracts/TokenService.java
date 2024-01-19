package com.blog.BlogBackend.services.abstracts;

import com.blog.BlogBackend.entities.Token;
import org.springframework.security.core.Authentication;

public interface TokenService {

    String generateJwtToken(Authentication authentication);
    Token saveToken(Token jwtToken);
}
