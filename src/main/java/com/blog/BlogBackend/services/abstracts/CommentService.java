package com.blog.BlogBackend.services.abstracts;

import com.blog.BlogBackend.dto.request.CommentRequest;
import com.blog.BlogBackend.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {

    List<CommentResponse> getCommentsByTitle(String title);
    List<CommentResponse> getCommentsByUser(String email);
    List<CommentResponse> getAllComments();
    CommentResponse saveComment(CommentRequest commentRequest);
    CommentResponse deleteComment(long id);

}
