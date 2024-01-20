package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.entities.ConfirmationToken;
import com.blog.BlogBackend.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }
    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }
    public Optional<ConfirmationToken> getConfirmationToken(String emailToken){
        return confirmationTokenRepository.findByConfirmationToken(emailToken);
    }
    public void setConfirmedAt(String emailToken){
        confirmationTokenRepository.updateConfirmedAt(emailToken, LocalDateTime.now());
    }
}
