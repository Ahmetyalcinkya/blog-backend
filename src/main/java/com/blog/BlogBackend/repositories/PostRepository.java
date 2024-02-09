package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p.*, u.name, u.surname, u.profile_picture, c.category_title, c.category_rating FROM blog.post AS p " +
            "JOIN blog.category AS c ON p.category_id = c.id JOIN blog.user AS u " +
            "ON p.user_id = u.id WHERE p.post_title ILIKE %:title%", nativeQuery = true)
    List<Post> findPostsByTitle(String title);

    @Query(value = "SELECT p.*, u.name, u.surname, u.profile_picture, c.category_title, c.category_rating FROM blog.post AS p JOIN " +
            "blog.user AS u ON p.user_id = u.id JOIN blog.category AS c ON p.category_id = c.id WHERE u.email = :email", nativeQuery = true)
    List<Post> findPostsByUser(String email);

    @Query(value = "SELECT p.*, u.name, u.surname, u.profile_picture, c.category_title, c.category_rating FROM blog.post AS p " +
            "JOIN blog.user AS u ON p.user_id = u.id JOIN blog.category AS c ON p.category_id = c.id WHERE p.category_id = :id", nativeQuery = true)
    List<Post> findPostsByCategoryID(long id);

    @Query(value = "SELECT p.*, c.category_title, c.category_rating, u.name, u.surname, u.profile_picture FROM blog.post AS p " +
            "JOIN blog.category AS c ON p.category_id = c.id JOIN blog.user AS u ON p.user_id = u.id", nativeQuery = true)
    List<Post> getAllPosts();

    @Query(value = "SELECT p.*, c.category_title, c.category_rating, u.name, u.surname, u.profile_picture FROM blog.post AS p " +
            "JOIN blog.category AS c ON p.category_id = c.id JOIN blog.user AS u ON p.user_id = u.id WHERE p.id = :id AND p.post_title ILIKE %:title%", nativeQuery = true)
    List<Post> getFilteredPosts(long id, String title);
}
