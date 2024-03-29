package com.blog.BlogBackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String profilePicture;

}
