package com.myblogrestapi.service;

import com.myblogrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    void deleteCommentById(Long postId, Long commentId);

    CommentDto updatePostById(Long postId, Long commentId,CommentDto newComment);
}
