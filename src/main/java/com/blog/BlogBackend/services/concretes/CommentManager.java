package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.dto.request.CommentRequest;
import com.blog.BlogBackend.dto.response.CommentResponse;
import com.blog.BlogBackend.entities.Comment;
import com.blog.BlogBackend.repositories.CommentRepository;
import com.blog.BlogBackend.services.abstracts.CommentService;
import com.blog.BlogBackend.services.abstracts.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentManager implements CommentService {

    private CommentRepository commentRepository;
    private ModelMapperService modelMapperService;

    @Autowired
    public CommentManager(CommentRepository commentRepository,ModelMapperService modelMapperService) {
        this.commentRepository = commentRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<CommentResponse> getCommentsByTitle(String title) {
        List<Comment> comments = commentRepository.findCommentsByTitle(title);

        return comments.stream().map(comment ->
                modelMapperService.forResponse().map(comment, CommentResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getCommentsByUser(String email) {
        List<Comment> comments = commentRepository.findCommentsByUser(email);

        return comments.stream().map(comment ->
                modelMapperService.forResponse().map(comment, CommentResponse.class)).collect(Collectors.toList());
    }

    @Override
    public Comment getCommentByID(long id) { //TODO WARNING!!!!!
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("C")); //TODO Throw exception C
    }

    @Override
    public List<CommentResponse> getAllComments() {
        List<Comment> comments = commentRepository.getAllComments();

        return comments.stream().map(comment ->
                modelMapperService.forResponse().map(comment, CommentResponse.class)).collect(Collectors.toList());
    }

    @Override
    public CommentResponse saveComment(CommentRequest commentRequest) {
        Comment comment = modelMapperService.forRequest().map(commentRequest, Comment.class);
        commentRepository.save(comment);
        return modelMapperService.forResponse().map(comment, CommentResponse.class);
    }

    @Override
    public CommentResponse deleteComment(long id) {
        Comment comment = getCommentByID(id);
        commentRepository.delete(comment);
        return modelMapperService.forResponse().map(comment, CommentResponse.class);
    }
}
