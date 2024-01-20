package com.blog.BlogBackend.controllers;

import com.blog.BlogBackend.dto.request.PostSaveRequest;
import com.blog.BlogBackend.dto.request.PostUpdateRequest;
import com.blog.BlogBackend.dto.response.PostResponse;
import com.blog.BlogBackend.services.abstracts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/title")
    @ResponseBody
    public List<PostResponse> getPostsByTitle(@RequestParam(name = "filter", required = false) String title){
        return postService.getPostsByTitle(title);
    }
    @GetMapping("/user")
    @ResponseBody
    public List<PostResponse> getUsersPosts(@RequestParam(name = "posts", required = false) String email){
        return postService.getUsersPosts(email);
    }
    @GetMapping("/{id}")
    public List<PostResponse> getPostsByCategoryID(@PathVariable long id){
        return postService.getPostsByCategoryID(id);
    }
    @GetMapping
    public List<PostResponse> getAllPosts(){
        return postService.getAllPosts();
    }
    @GetMapping("/admin/getPostsById/{id}")
    public PostResponse getPostByID(@PathVariable long id){
        return postService.getPostByID(id);
    }
    @PostMapping("/")
    public PostResponse savePost(@RequestBody PostSaveRequest postSaveRequest){
        return postService.savePost(postSaveRequest);
    }
    @PutMapping("/")
    public PostResponse updatePost(@RequestBody PostUpdateRequest postUpdateRequest){
        return postService.updatePost(postUpdateRequest);
    }
    @DeleteMapping("/{id}")
    public PostResponse deletePost(@PathVariable long id){
        return postService.deletePost(id);
    }
}
