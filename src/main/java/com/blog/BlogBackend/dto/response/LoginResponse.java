package com.blog.BlogBackend.dto.response;

public record LoginResponse(String name, String surname, String email, String profilePicture, String token, String role) {
}
