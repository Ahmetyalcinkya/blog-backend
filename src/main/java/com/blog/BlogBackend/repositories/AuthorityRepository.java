package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    @Query(value = "SELECT a.* FROM blog.authority AS a WHERE a.authority = :authority", nativeQuery = true)
    Authority findByAuthority(String authority);
}
