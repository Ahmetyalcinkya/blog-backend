package com.blog.BlogBackend.dto.response;


import java.time.LocalDate;
import java.util.List;

public record PostResponse(long id, String title, String content, LocalDate createdAt, LocalDate updateAt,
                           double rating, List<String> images) {
}
