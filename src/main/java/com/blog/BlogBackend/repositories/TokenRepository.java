package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = "SELECT t.* FROM blog.token AS t WHERE t.token = :token", nativeQuery = true)
    Token findByToken(String token);
}
