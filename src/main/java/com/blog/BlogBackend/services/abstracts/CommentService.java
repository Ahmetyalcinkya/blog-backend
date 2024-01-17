package com.blog.BlogBackend.services.abstracts;

import com.blog.BlogBackend.dto.request.CommentRequest;
import com.blog.BlogBackend.dto.response.CommentResponse;
import com.blog.BlogBackend.entities.Comment;

import java.util.List;

public interface CommentService {

    List<CommentResponse> getCommentsByTitle(String title);
    List<CommentResponse> getCommentsByUser(String email);
    Comment getCommentByID(long id); //TODO WARNING!!!!!
    List<CommentResponse> getAllComments();
    CommentResponse saveComment(CommentRequest commentRequest);
    CommentResponse deleteComment(long id);

}
