package com.blog.BlogBackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private long id;
    private String content;
    private LocalDateTime createdAt;
    private String userName;
    private String userSurname;
}
