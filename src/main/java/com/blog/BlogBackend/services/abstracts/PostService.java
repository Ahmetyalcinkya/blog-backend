package com.blog.BlogBackend.services.abstracts;

import com.blog.BlogBackend.dto.request.PostSaveRequest;
import com.blog.BlogBackend.dto.request.PostUpdateRequest;
import com.blog.BlogBackend.dto.response.PostResponse;

import java.util.List;

public interface PostService {

    List<PostResponse> getPostsByTitle(String title);
    List<PostResponse> getUsersPosts(String email);
    List<PostResponse> getPostsByCategoryID(long id);
    List<PostResponse> getAllPosts();
    PostResponse getPostByID(long id);
    PostResponse savePost(PostSaveRequest postSaveRequest);
    PostResponse updatePost(PostUpdateRequest postUpdateRequest);
    PostResponse deletePost(long id);

}
