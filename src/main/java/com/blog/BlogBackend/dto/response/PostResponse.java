package com.blog.BlogBackend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private double rating;
    private List<String> images;
    private long categoryID;
    private long userID;
    private String userName;
    private String userSurname;
    private String userPicture;
    private String categoryTitle;
    private String categoryRating;
    private String categoryImage;
}
