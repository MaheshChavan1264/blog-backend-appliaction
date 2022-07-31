package com.blog.blogbackend.services.impl;

import com.blog.blogbackend.entities.Comment;
import com.blog.blogbackend.entities.Post;
import com.blog.blogbackend.exceptions.ResourceNotFoundException;
import com.blog.blogbackend.payloads.CommentDto;
import com.blog.blogbackend.repositories.CommentRepository;
import com.blog.blogbackend.repositories.PostRepository;
import com.blog.blogbackend.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", " Id: ", postId));
        Comment comment = this.commentDtoToComment(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return this.commentToCommentDto(savedComment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id: ", commentId));
        comment.setContent(commentDto.getContent());
        return this.commentToCommentDto(this.commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", " Id: ", commentId));
        this.commentRepository.delete(comment);

    }

    @Override
    public List<CommentDto> getAllComments() {
        return this.commentRepository.findAll().stream().map(this::commentToCommentDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Integer commentId) {
        return this.commentToCommentDto(this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId)));
    }

    public CommentDto commentToCommentDto(Comment comment) {
        return this.modelMapper.map(comment, CommentDto.class);
    }

    public Comment commentDtoToComment(CommentDto commentDto) {
        return this.modelMapper.map(commentDto, Comment.class);
    }

}
