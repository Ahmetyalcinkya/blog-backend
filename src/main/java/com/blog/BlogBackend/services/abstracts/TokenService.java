package com.blog.BlogBackend.services.abstracts;

import com.blog.BlogBackend.entities.Token;

public interface TokenService { //TODO TokenManager

    String generateJwtToken(/*TODO Spring core -> Authentication authentication*/);
    Token saveToken(Token jwtToken);
}
