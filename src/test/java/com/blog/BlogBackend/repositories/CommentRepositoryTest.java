package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommentRepositoryTest {

    private CommentRepository commentRepository;

    @Autowired
    public CommentRepositoryTest(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Test
    void findCommentsByTitle() {
        List<Comment> comments = commentRepository.findCommentsByTitle("B");
        Comment comment = comments.get(0);
        assertEquals(comment.getContent(), "XXXXXXXXXXX");
    }
    @Test
    void findCommentsByTitleFail() {
        List<Comment> comments = commentRepository.findCommentsByTitle("Q");
        assertEquals(true,comments.size() == 0);
    }

    @Test
    void findCommentsByUser() {
        List<Comment> userComments = commentRepository.findCommentsByUser("x@gmail.com");
        assertNotNull(userComments.get(0));
    }
    @Test
    void findCommentsByUserFail() {
        List<Comment> userComments = commentRepository.findCommentsByUser("y@gmail.com");
        assertEquals(true,userComments.size() == 0);
    }

    @Test
    void getAllComments() {
        List<Comment> allComments = commentRepository.getAllComments();
        assertNotNull(allComments);
    }
}