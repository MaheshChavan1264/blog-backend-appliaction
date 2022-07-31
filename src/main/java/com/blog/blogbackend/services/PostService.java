package com.blog.blogbackend.services;

import com.blog.blogbackend.payloads.PostDto;
import com.blog.blogbackend.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //GET - get all posts by userid
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //GET - get single post by post id
    PostDto getPostById(int postId);

    //GET - get all posts by category
    PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize);

    //GET - get all posts by user id
    PostResponse getPostsByUser(int userId, int pageNumber, int pageSize);

    //GET - search post
    List<PostDto> searchPost(String query);

    //CREATE - create post
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //UPDATE - update post
    PostDto updatePost(PostDto postDto, Integer postId);

    //DELETE - delete post
    void deletePost(int postId);
}
