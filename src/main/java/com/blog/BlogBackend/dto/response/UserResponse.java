package com.blog.BlogBackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String profilePicture;
    private LocalDateTime registrationDate;
}
