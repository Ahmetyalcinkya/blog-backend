package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.dto.request.CommentRequest;
import com.blog.BlogBackend.dto.response.CommentResponse;
import com.blog.BlogBackend.entities.Comment;
import com.blog.BlogBackend.entities.Post;
import com.blog.BlogBackend.entities.User;
import com.blog.BlogBackend.repositories.CommentRepository;
import com.blog.BlogBackend.repositories.UserRepository;
import com.blog.BlogBackend.services.abstracts.CommentService;
import com.blog.BlogBackend.services.abstracts.ModelMapperService;
import com.blog.BlogBackend.services.abstracts.PostService;
import com.blog.BlogBackend.services.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentManager implements CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private UserRepository userRepository;
    private PostService postService;
    private ModelMapperService modelMapperService;

    @Autowired
    public CommentManager(CommentRepository commentRepository,ModelMapperService modelMapperService,
                          UserService userService, UserRepository userRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.modelMapperService = modelMapperService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.postService = postService;
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
    public Comment getCommentByID(long id) {
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
        String email = userService.getAuthenticatedUser();
        Post post = postService.getPostByID(commentRequest.getPostID());
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        LocalDateTime now = LocalDateTime.now();

        if(optionalUser.isPresent()){
        Comment comment = modelMapperService.forRequest().map(commentRequest, Comment.class);
        comment.setCreatedAt(now);
        comment.setUser(optionalUser.get());
        comment.setPost(post);
        commentRepository.save(comment);
        return modelMapperService.forResponse().map(comment, CommentResponse.class);
        }
        throw new RuntimeException("L"); //TODO Throw exception -> User not authenticated L
    }

    @Override
    public CommentResponse deleteComment(long id) {
        String email = userService.getAuthenticatedUser();
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()){
            List<Comment> comments = commentRepository.findCommentsByUser(email);
            for(Comment comment: comments){
                if(comment.getId() == id){
                    commentRepository.delete(comment);
                    return modelMapperService.forResponse().map(comment, CommentResponse.class);
                }
            }
            throw new RuntimeException("N"); //TODO Throw exception -> Comment not found N
        }
        throw new RuntimeException("M"); //TODO Throw exception -> User not authenticated M
    }
}
