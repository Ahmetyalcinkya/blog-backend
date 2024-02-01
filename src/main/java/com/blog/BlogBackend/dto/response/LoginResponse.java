package com.blog.BlogBackend.dto.response;

import java.time.LocalDateTime;

public record LoginResponse(String name, String surname, String email, String profilePicture, String token, String role, long id, LocalDateTime registrationDate) {
}
