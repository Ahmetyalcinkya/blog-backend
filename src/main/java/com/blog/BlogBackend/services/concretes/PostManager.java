package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.dto.request.PostSaveRequest;
import com.blog.BlogBackend.dto.request.PostUpdateRequest;
import com.blog.BlogBackend.dto.response.PostResponse;
import com.blog.BlogBackend.dto.response.UserResponse;
import com.blog.BlogBackend.entities.Category;
import com.blog.BlogBackend.entities.Post;
import com.blog.BlogBackend.entities.User;
import com.blog.BlogBackend.repositories.PostRepository;
import com.blog.BlogBackend.repositories.UserRepository;
import com.blog.BlogBackend.services.abstracts.CategoryService;
import com.blog.BlogBackend.services.abstracts.ModelMapperService;
import com.blog.BlogBackend.services.abstracts.PostService;
import com.blog.BlogBackend.services.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PostManager implements PostService {

    private PostRepository postRepository;
    private ModelMapperService modelMapperService;
    private CategoryService categoryService;
    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public PostManager(PostRepository postRepository, ModelMapperService modelMapperService,
                       CategoryService categoryService, UserRepository userRepository, UserService userService) {
        this.postRepository = postRepository;
        this.modelMapperService = modelMapperService;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public List<PostResponse> getPostsByTitle(String title) {
        List<Post> posts = postRepository.findPostsByTitle(title);

        return posts.stream().map(post ->
                modelMapperService.forResponse().map(post, PostResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getUsersPosts(String email) {
        List<Post> posts = postRepository.findPostsByUser(email);

        return posts.stream().map(post ->
                modelMapperService.forResponse().map(post, PostResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getPostsByCategoryID(long id) {
        List<Post> posts = postRepository.findPostsByCategoryID(id);

        return posts.stream().map(post ->
                modelMapperService.forResponse().map(post, PostResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.getAllPosts();

        return posts.stream().map(post ->
                modelMapperService.forResponse().map(post, PostResponse.class)).collect(Collectors.toList());
    }

    @Override
    public PostResponse getPostByID(long id) { //TODO WARNING!!!!!
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("E")); //TODO Throw excepiton E
        return modelMapperService.forResponse().map(post, PostResponse.class);
    }

    @Override
    public PostResponse savePost(PostSaveRequest postSaveRequest) {
        String email =  userService.getAuthenticatedUser();
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()){
            Category category = categoryService.getCategoryByID(postSaveRequest.getCategoryID());

            Post post = modelMapperService.forRequest().map(postSaveRequest, Post.class);
            post.setCategory(category);
            post.setUser(user.get());
            postRepository.save(post);
            return modelMapperService.forResponse().map(post, PostResponse.class);
        }
        throw new RuntimeException("G"); //TODO Throw exception G
    }

    @Override
    public PostResponse updatePost(PostUpdateRequest postUpdateRequest) {
        Post post = modelMapperService.forRequest().map(postUpdateRequest, Post.class);
        postRepository.save(post);
        return modelMapperService.forResponse().map(post, PostResponse.class);
    }

    @Override
    public PostResponse deletePost(long id) { //TODO WARNING!!!!!

        return null;
    }
}
