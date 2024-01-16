package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
