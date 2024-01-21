package com.blog.BlogBackend.services.abstracts;

import com.blog.BlogBackend.dto.request.PostUpdateRequest;
import com.blog.BlogBackend.dto.response.PostResponse;
import com.blog.BlogBackend.entities.Post;

import java.util.List;

public interface PostService {

    List<PostResponse> getPostsByTitle(String title);
    List<PostResponse> getUsersPosts(String email);
    List<PostResponse> getPostsByCategoryID(long id);
    List<PostResponse> getAllPosts();
    Post getPostByID(long id);
    PostResponse saveUpdatePost(PostUpdateRequest postUpdateRequest);
    PostResponse deletePost(long id);

}
