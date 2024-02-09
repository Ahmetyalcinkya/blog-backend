package com.blog.BlogBackend.controllers;

import com.blog.BlogBackend.dto.request.PostUpdateRequest;
import com.blog.BlogBackend.dto.response.PostResponse;
import com.blog.BlogBackend.entities.Post;
import com.blog.BlogBackend.services.abstracts.ModelMapperService;
import com.blog.BlogBackend.services.abstracts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;
    private ModelMapperService modelMapperService;

    @Autowired
    public PostController(PostService postService, ModelMapperService modelMapperService) {
        this.postService = postService;
        this.modelMapperService = modelMapperService;
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
    @GetMapping
    public List<PostResponse> getPostsByFilter(@RequestParam(name = "category", required = false) long id,
                                                   @RequestParam(name = "filter", required = false) String title){
        if(title == null && id != 0) {
            return postService.getPostsByCategoryID(id);
        } else if(id == 0 && title != null) {
            return postService.getPostsByTitle(title);
        }else if(id != 0 && title != null) {
            return postService.getFilteredPosts(id, title);
        }else {
            return postService.getAllPosts();
        }
    }
    @GetMapping("/")
    public List<PostResponse> getAllPosts(){
        return postService.getAllPosts();
    }
    @GetMapping("/{id}")
    public PostResponse getPostByID(@PathVariable long id){
        Post post = postService.getPostByID(id);
        return modelMapperService.forResponse().map(post, PostResponse.class);
    }
    @PostMapping("/")
    public PostResponse savePost(@RequestBody PostUpdateRequest postUpdateRequest){
        return postService.saveUpdatePost(postUpdateRequest);
    }
    @DeleteMapping("/deletePostById/{id}")
    public PostResponse deletePost(@PathVariable long id){
        return postService.deletePost(id);
    }
}
