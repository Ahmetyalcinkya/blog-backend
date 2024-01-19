package com.blog.BlogBackend.dto.request;

import com.blog.BlogBackend.entities.Category;
import com.blog.BlogBackend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveRequest { //TODO WARNING!!!!!
    private String title;
    private String content;
    private double rating;
    private List<String> images;
    private long userID;
    private long categoryID;
}
