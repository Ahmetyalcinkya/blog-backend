package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    @Query("SELECT a FROM Authority a WHERE a.authority = :authority")
    Optional<Authority> findByAuthority(String authority);
}
