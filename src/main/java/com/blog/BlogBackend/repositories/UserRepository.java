package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = TRUE WHERE u.email = :email")
    int enableUser(String email);

    @Query(value = "SELECT u.* FROM blog.user AS u WHERE u.token_id = :id", nativeQuery = true)
    Optional<User> findUserByTokenId(long id);
}
