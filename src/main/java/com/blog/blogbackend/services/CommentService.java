package com.blog.blogbackend.services;

import com.blog.blogbackend.payloads.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId);

    CommentDto updateComment(CommentDto commentDto, Integer commentId);

    void deleteComment(Integer commentId);

    List<CommentDto> getAllComments();

    CommentDto getCommentById(Integer commentId);
}
