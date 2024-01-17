package com.blog.BlogBackend.dto.response;

import java.time.LocalDate;

public record UserResponse(long id, String name, String surname, String email, String profilePicture, LocalDate registrationDate) {
}
