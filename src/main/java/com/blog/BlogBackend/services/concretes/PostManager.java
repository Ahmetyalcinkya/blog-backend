package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.dto.request.PostUpdateRequest;
import com.blog.BlogBackend.dto.response.PostResponse;
import com.blog.BlogBackend.entities.Category;
import com.blog.BlogBackend.entities.Post;
import com.blog.BlogBackend.entities.User;
import com.blog.BlogBackend.exceptions.BlogException;
import com.blog.BlogBackend.repositories.PostRepository;
import com.blog.BlogBackend.repositories.UserRepository;
import com.blog.BlogBackend.services.abstracts.CategoryService;
import com.blog.BlogBackend.services.abstracts.ModelMapperService;
import com.blog.BlogBackend.services.abstracts.PostService;
import com.blog.BlogBackend.services.abstracts.UserService;
import com.blog.BlogBackend.utils.BlogValidations;
import com.blog.BlogBackend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Post getPostByID(long id) {
        return postRepository.findById(id).orElseThrow(() -> new BlogException(Constants.POST_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public PostResponse saveUpdatePost(PostUpdateRequest postUpdateRequest) {
        BlogValidations.checkString(postUpdateRequest.getTitle(), "Title", 200);
        BlogValidations.checkContent(postUpdateRequest.getContent());

        LocalDateTime now = LocalDateTime.now();
        String email =  userService.getAuthenticatedUser();
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()){
            Category category = categoryService.getCategoryByID(postUpdateRequest.getCategoryID());
            Post post = modelMapperService.forRequest().map(postUpdateRequest, Post.class);
            post.setId(postUpdateRequest.getId());
            // find post by id if post not exist set created date if exist set update date
            post.setCategory(category);
            if (post.getCreatedAt() == null){
                post.setCreatedAt(now);
            }else { //TODO UpdateAt not work !!!
                post.setUpdateAt(now);
            }
            post.setUser(user.get());
            postRepository.save(post);
            return modelMapperService.forResponse().map(post, PostResponse.class);
        }
        throw new BlogException(Constants.NOT_AUTHENTICATED, HttpStatus.FORBIDDEN);
    }

    @Override
    public PostResponse deletePost(long id) {
        String email = userService.getAuthenticatedUser();
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()){
            List<Post> posts = postRepository.findPostsByUser(email);
            for(Post post: posts){
                if(post.getId() == id){
                    postRepository.delete(post);
                    return modelMapperService.forResponse().map(post, PostResponse.class);
                }
            }
            throw new BlogException(Constants.POST_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        throw new BlogException(Constants.NOT_AUTHENTICATED, HttpStatus.FORBIDDEN);
    }

    @Override
    public List<PostResponse> getFilteredPosts(long id, String title) {
        List<Post> posts = postRepository.getFilteredPosts(id, title);

        return posts.stream().map(post ->
                modelMapperService.forResponse().map(post, PostResponse.class)).collect(Collectors.toList());
    }
}
