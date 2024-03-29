package com.blog.BlogBackend.controllers;

import com.blog.BlogBackend.dto.request.CommentRequest;
import com.blog.BlogBackend.dto.response.CommentResponse;
import com.blog.BlogBackend.services.abstracts.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping("/title")
    @ResponseBody //POST TITLE
    public List<CommentResponse> getCommentsByTitle(@RequestParam(name = "filter", required = false) String postTitle){
        return commentService.getCommentsByTitle(postTitle);
    }
    @GetMapping("/post/{id}")
    public List<CommentResponse> getCommentsByPostId(@PathVariable long id){
        return commentService.findCommentsByPostId(id);
    }
    @GetMapping("/admin/getUsersComment")
    @ResponseBody
    public List<CommentResponse> getCommentByUser(@RequestParam(name = "comments", required = false) String email){
        return commentService.getCommentsByUser(email);
    }
    @GetMapping("/admin/getAllComments")
    public List<CommentResponse> getAllComments(){
        return commentService.getAllComments();
    }
    @PostMapping("/")
    public CommentResponse saveComment(@RequestBody CommentRequest commentRequest){
        return commentService.saveComment(commentRequest);
    }
    @DeleteMapping("/deleteComment/{id}")
    public CommentResponse deleteComment(@PathVariable long id){
        return commentService.deleteComment(id);
    }

}
