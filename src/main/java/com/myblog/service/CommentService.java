package com.myblog.service;

import com.myblog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postId,CommentDto commentRequest);

    List<CommentDto> getCommentByPostId(long postId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

    void deleteComment(long postId, long commentId);
}
