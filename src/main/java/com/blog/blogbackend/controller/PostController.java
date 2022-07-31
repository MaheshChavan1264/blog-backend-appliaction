package com.blog.blogbackend.controller;

import com.blog.blogbackend.payloads.ApiResponse;
import com.blog.blogbackend.payloads.PostDto;
import com.blog.blogbackend.payloads.PostResponse;
import com.blog.blogbackend.services.impl.FileServiceImpl;
import com.blog.blogbackend.services.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.blog.blogbackend.config.AppConstants.*;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostServiceImpl postServiceImpl;

    @Autowired
    private FileServiceImpl fileServiceImpl;

    @Value("${project.image}")
    private String path;

    //GET - get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) int pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) int pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = SORT_DIR, required = false) String sortDir
    ) {
        return new ResponseEntity<>(this.postServiceImpl.getAllPosts(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostsById(@PathVariable(name = "postId") int postId) {
        return new ResponseEntity<>(this.postServiceImpl.getPostById(postId), HttpStatus.OK);
    }

    //GET - get all posts by userid
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUserId(@PathVariable(name = "userId") int userId,
                                                         @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) int pageNumber,
                                                         @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) int pageSize
    ) {
        return new ResponseEntity<>(this.postServiceImpl.getPostsByUser(userId, pageNumber, pageSize), HttpStatus.OK);
    }

    //GET - get all posts by categoryId
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable(name = "categoryId") int categoryId,
                                                           @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) int pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) int pageSize
    ) {
        return new ResponseEntity<>(this.postServiceImpl.getPostsByCategory(categoryId, pageNumber, pageSize), HttpStatus.OK);
    }

    //CREATE - create post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable(name = "userId") int userId, @PathVariable(name = "categoryId") int categoryId) {
        PostDto newPost = this.postServiceImpl.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    //UPDATE - update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "postId") int postId) {
        PostDto updatedPost = this.postServiceImpl.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    //DELETE - delete post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable(name = "postId") int postId) {
        this.postServiceImpl.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
    }

    //SEARCH - search posts
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable(name = "keywords") String title) {
        List<PostDto> result = this.postServiceImpl.searchPost(title);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //UPLOAD Image
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable(name = "postId") int postId) throws IOException {
        PostDto postDto = this.postServiceImpl.getPostById(postId);
        String fileName = this.fileServiceImpl.uploadImage(path, image);
        postDto.setImageName(fileName);
        System.out.println(postDto.getImageName());
        PostDto updatedPost = this.postServiceImpl.updatePost(postDto, postId);
        System.out.println("updated post" + updatedPost.toString());
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    //Serve the image
    @GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable(name = "imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileServiceImpl.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }

}
