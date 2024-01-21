package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.entities.Token;
import com.blog.BlogBackend.repositories.TokenRepository;
import com.blog.BlogBackend.services.abstracts.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenManager implements TokenService {

    private JwtEncoder jwtEncoder;
    private TokenRepository tokenRepository;

    @Autowired
    public TokenManager(JwtEncoder jwtEncoder, TokenRepository tokenRepository) {
        this.jwtEncoder = jwtEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String generateJwtToken(Authentication authentication) {

        String authority = authentication.getAuthorities().stream().findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new RuntimeException("H")); //TODO Throw exception -> Authority not found H

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(24, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("authority",authority)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public Token saveToken(Token jwtToken) {
        return tokenRepository.save(jwtToken);
    }
}
