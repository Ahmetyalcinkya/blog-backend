package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.dto.request.PostSaveRequest;
import com.blog.BlogBackend.dto.request.PostUpdateRequest;
import com.blog.BlogBackend.dto.response.PostResponse;
import com.blog.BlogBackend.entities.Post;
import com.blog.BlogBackend.repositories.PostRepository;
import com.blog.BlogBackend.services.abstracts.ModelMapperService;
import com.blog.BlogBackend.services.abstracts.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class PostManager implements PostService {

    private PostRepository postRepository;
    private ModelMapperService modelMapperService;

    @Autowired
    public PostManager(PostRepository postRepository, ModelMapperService modelMapperService) {
        this.postRepository = postRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<PostResponse> getPostsByTitle(String title) {
        List<Post> posts = postRepository.findPostsByTitle(title);

        return posts.stream().map(post ->
                modelMapperService.forResponse().map(post, PostResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getUsersPosts(String email) {
        List<Post> posts = postRepository.findPostsByUser(email);

        return posts.stream().map(post ->
                modelMapperService.forResponse().map(post, PostResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getPostsByCategoryID(long id) {
        List<Post> posts = postRepository.findPostsByCategoryID(id);

        return posts.stream().map(post ->
                modelMapperService.forResponse().map(post, PostResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.getAllPosts();

        return posts.stream().map(post ->
                modelMapperService.forResponse().map(post, PostResponse.class)).collect(Collectors.toList());
    }

    @Override
    public PostResponse getPostByID(long id) { //TODO WARNING!!!!!
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("")); //todo throw excepiton
        return modelMapperService.forResponse().map(post, PostResponse.class);
    }

    @Override
    public PostResponse savePost(PostSaveRequest postSaveRequest) {
        Post post = modelMapperService.forRequest().map(postSaveRequest, Post.class);
        return modelMapperService.forResponse().map(post, PostResponse.class);
    }

    @Override
    public PostResponse updatePost(PostUpdateRequest postUpdateRequest) {
        Post post = modelMapperService.forRequest().map(postUpdateRequest, Post.class);
        return modelMapperService.forResponse().map(post, PostResponse.class);
    }

    @Override
    public PostResponse deletePost(long id) { //TODO WARNING!!!!!

        return null;
    }
}
