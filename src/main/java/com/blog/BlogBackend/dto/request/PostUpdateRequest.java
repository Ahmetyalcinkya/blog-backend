package com.blog.BlogBackend.dto.request;

import com.blog.BlogBackend.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequest { //TODO WARNING!!!!!
    private long id;
    private String title;
    private String content;
    private List<String> images;
    private long categoryID;
}
