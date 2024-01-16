package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT co.id, co.post_id, co.comment_content, co.comment_created_at, p.user_id, p.post_title, p.post_rating," +
            " c.category_title, c.category_rating, u.name, u.surname, u.email FROM blog.comment AS co JOIN blog.post AS p " +
            "ON co.post_id = p.id JOIN blog.user AS u ON co.user_id = u.id JOIN blog.category as c ON p.category_id = c.id " +
            "WHERE p.post_title ILIKE %:title%", nativeQuery = true)
    List<Comment> findCommentsByTitle(String title);
    @Query(value = "SELECT co.id, co.post_id, co.comment_content, co.comment_created_at, p.user_id, p.post_title, p.post_rating, " +
            "c.category_title, c.category_rating, u.name, u.surname, u.email FROM blog.comment AS co JOIN blog.post AS p ON co.post_id = p.id " +
            "JOIN blog.user AS u ON co.user_id = u.id JOIN blog.category as c ON p.category_id = c.id WHERE u.email = :email", nativeQuery = true)
    List<Comment> findCommentsByUser(String email);
    @Query(value = "SELECT co.id, co.post_id, co.comment_content, co.comment_created_at, p.user_id, p.post_title, p.post_rating, " +
            "c.category_title, c.category_rating, u.name, u.surname, u.email FROM blog.comment AS co JOIN blog.post AS p ON co.post_id = p.id " +
            "JOIN blog.user AS u ON co.user_id = u.id JOIN blog.category as c ON p.category_id = c.id", nativeQuery = true)
    List<Comment> getAllComments();
}
