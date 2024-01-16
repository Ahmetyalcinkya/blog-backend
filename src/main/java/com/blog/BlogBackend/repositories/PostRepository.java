package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p.id, p.title, p.content, p.created_at, p.rating, u.name, u.surname, u.profile_picture, c.title, c.id, c.rating FROM blog.post AS p " +
            "JOIN blog.category AS c ON p.category_id = c.id JOIN blog.user AS u " +
            "ON p.user_id = u.id WHERE p.title ILIKE %:title%", nativeQuery = true)
    List<Post> findPostsByTitle(String title);

    @Query(value = "SELECT p.id, p.title, p.content, p.created_at, p.update_at, p.rating, u.name, u.surname, c.title, c.id, c.rating FROM blog.post AS p " +
            "JOIN blog.user AS u ON p.user_id = u.id JOIN blog.category AS c ON p.category_id = c.id WHERE u.email = :email", nativeQuery = true)
    List<Post> findPostsByUser(String email);

    @Query(value = "SELECT p.id, p.title, p.content, p.created_at, p.update_at, p.rating, u.name, u.surname, u.profile_picture, c.title, c.id, c.rating FROM blog.post AS p " +
            "JOIN blog.user AS u ON p.user_id = u.id JOIN blog.category AS c ON p.category_id = c.id WHERE c.id = :id", nativeQuery = true)
    List<Post> findPostsByCategoryID(long id);

    @Query(value = "SELECT p.id, p.title, p.content, p.created_at, p.images, p.rating, c.id, c.title, c.rating, u.id, u.name, u.surname, u.profile_picture " +
            "FROM blog.post AS p JOIN blog.category AS c ON p.category_id = c.id JOIN blog.user AS u ON p.user_id = u.id", nativeQuery = true)
    List<Post> getAllPosts();

    @Query(value = "SELECT p.id, p.title, p.content, p.created_at, p.update_at, p.rating, p.images, c.title, c.id, c.rating, u.name, u.surname," +
            " u.profile_picture FROM blog.post AS p JOIN blog.category AS c ON p.category_id = c.id JOIN blog.comment AS co ON p.id = co.post_id" +
            " JOIN blog.user AS u ON p.user_id = u.id WHERE p.id = :id", nativeQuery = true)
    Post getPostByID(long id);
}
