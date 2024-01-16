package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostRepositoryTest {

    @Autowired //field injection not recommended
    private PostRepository postRepository;

    @Test
    void findPostsByTitle() {
        List<Post> posts = postRepository.findPostsByTitle("AB");
        Post post = posts.get(0);
        assertEquals(post.getRating(), 4.2);
    }
    @Test
    void findPostsByTitleFail() {
        List<Post> posts = postRepository.findPostsByTitle("xq");
        assertEquals(0, posts.size());
    }

    @Test
    void findPostsByUser() {
        List<Post> posts = postRepository.findPostsByUser("x@gmail.com");
        assertEquals(1,posts.get(0).getId());
    }
    @Test
    void findPostsByUserFail() {
        String invalidEmail = "abc.q.com";
        List<Post> posts = postRepository.findPostsByUser(invalidEmail);
        assertEquals(0, posts.size());
    }

    @Test
    void findPostsByCategoryID() {
        List<Post> posts = postRepository.findPostsByCategoryID(1);
        assertEquals("ABC.COM",posts.get(0).getCategory().getImage());
    }
    @Test
    void findPostsByCategoryIDFail() {
        List<Post> posts = postRepository.findPostsByCategoryID(5);
        assertEquals(0, posts.size());
    }

    @Test
    void getAllPosts() {
        List<Post> posts = postRepository.getAllPosts();
        assertEquals(1, posts.size());
    }

    @Test
    void getPostByID() {
        Post post = postRepository.getPostByID(1);
        assertEquals(1, post.getCategory().getId());
    }
}