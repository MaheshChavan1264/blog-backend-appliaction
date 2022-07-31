package com.blog.blogbackend.controller;

import com.blog.blogbackend.payloads.ApiResponse;
import com.blog.blogbackend.payloads.CommentDto;
import com.blog.blogbackend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentServiceImpl;

    //GET - get all comments
    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return new ResponseEntity<>(this.commentServiceImpl.getAllComments(), HttpStatus.OK);
    }

    //GET - get comment by id
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "commentId") int commentId) {
        return new ResponseEntity<>(this.commentServiceImpl.getCommentById(commentId), HttpStatus.OK);
    }

    //POST - create a comment
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable int postId, @RequestBody CommentDto commentDto) {
        CommentDto createdComment = this.commentServiceImpl.createComment(commentDto, postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    //PUT - update a comment
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable int commentId, @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = this.commentServiceImpl.updateComment(commentDto, commentId);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }


    //DELETE - delete a comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId) {
        this.commentServiceImpl.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully", true), HttpStatus.OK);
    }

}
