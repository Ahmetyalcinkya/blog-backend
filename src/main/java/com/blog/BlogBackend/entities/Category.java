package com.blog.BlogBackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category", schema = "blog")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "category_title")
    private String title;

    @Column(name = "category_image")
    private String image;

    @Column(name = "category_rating")
    private double rating;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<Post> posts;

    public void addPosts(Post post){
        if(posts == null){
            posts = new ArrayList<>();
        }
        posts.add(post);
    }
}
