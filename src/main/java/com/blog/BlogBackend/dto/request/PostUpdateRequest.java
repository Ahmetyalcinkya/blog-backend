package com.blog.BlogBackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequest {
    private long id;
    private String title;
    private String content;
    private List<String> images;
    private long categoryID;
}
