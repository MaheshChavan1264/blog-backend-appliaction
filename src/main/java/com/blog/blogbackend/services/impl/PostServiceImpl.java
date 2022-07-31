package com.blog.blogbackend.services.impl;

import com.blog.blogbackend.entities.Category;
import com.blog.blogbackend.entities.Post;
import com.blog.blogbackend.entities.User;
import com.blog.blogbackend.exceptions.ResourceNotFoundException;
import com.blog.blogbackend.payloads.PostDto;
import com.blog.blogbackend.payloads.PostResponse;
import com.blog.blogbackend.repositories.CategoryRepository;
import com.blog.blogbackend.repositories.PostRepository;
import com.blog.blogbackend.repositories.UserRepository;
import com.blog.blogbackend.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepository.findAll(p);
        List<Post> allPosts = pagePost.getContent();
        return getPostResponse(pagePost, allPosts);
    }


    @Override
    public PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", " Id: ", categoryId));
        Pageable p = PageRequest.of(pageNumber, pageSize);
        Page<Post> pagePost = this.postRepository.findAll(p);
        List<Post> filteredPosts = pagePost.stream().filter(post -> post.getCategory().getId() == categoryId).collect(Collectors.toList());
        return getPostResponse(pagePost, filteredPosts);
    }


    @Override
    public PostResponse getPostsByUser(int userId, int pageNumber, int pageSize) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id: ", userId));
        Pageable p = PageRequest.of(pageNumber, pageSize);
        Page<Post> pagePost = this.postRepository.findAll(p);
        List<Post> filteredPosts = pagePost.stream().filter(post -> post.getUser().getId() == userId).collect(Collectors.toList());
        return getPostResponse(pagePost, filteredPosts);
    }


    private PostResponse getPostResponse(Page<Post> pagePost, List<Post> posts) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(posts.stream().map(this::postToPostDto).collect(Collectors.toList()));
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements((int) pagePost.getTotalElements());
        postResponse.setTotalPages((int) pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(int postId) {
        return this.postToPostDto(this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post ", "Id: ", postId)));
    }

    @Override
    public List<PostDto> searchPost(String query) {
        List<Post> posts = this.postRepository.findByTitleContaining(query);
        return posts.stream().map((this::postToPostDto)).collect(Collectors.toList());
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id: ", userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", " Id: ", categoryId));
        Post post = this.postDtoToPost(postDto);
        post.setCreatedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post newPost = this.postRepository.save(post);
        return this.postToPostDto(newPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id: ", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setCreatedDate(new Date());
        Post updatedPost = this.postRepository.save(post);
        return this.postToPostDto(updatedPost);
    }

    @Override
    public void deletePost(int postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", " Id: ", postId));
        this.postRepository.delete(post);
    }


    public Post postDtoToPost(PostDto postDto) {
        return this.modelMapper.map(postDto, Post.class);
    }

    public PostDto postToPostDto(Post post) {
        return this.modelMapper.map(post, PostDto.class);
    }
}
