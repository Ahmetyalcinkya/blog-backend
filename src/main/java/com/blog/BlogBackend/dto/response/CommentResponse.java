package com.blog.BlogBackend.dto.response;

import java.time.LocalDate;

public record CommentResponse(long id, String content, LocalDate createdAt) {
}
