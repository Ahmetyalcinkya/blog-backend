package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Query("SELECT c FROM ConfirmationToken c WHERE c.emailToken = :emailToken")
    Optional<ConfirmationToken> findByConfirmationToken(String emailToken);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c SET c.confirmedAt = ?2 WHERE c.emailToken = ?1")
    void updateConfirmedAt(String emailToken, LocalDate confirmedAt);
}
