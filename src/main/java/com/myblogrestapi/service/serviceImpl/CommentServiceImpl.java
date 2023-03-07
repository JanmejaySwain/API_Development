package com.myblogrestapi.service.serviceImpl;

import com.myblogrestapi.entities.Comment;
import com.myblogrestapi.entities.Post;
import com.myblogrestapi.exception.BlogApiException;
import com.myblogrestapi.exception.ResourceNotFoundException;
import com.myblogrestapi.payload.CommentDto;
import com.myblogrestapi.repository.CommentRepository;
import com.myblogrestapi.repository.PostRepository;
import com.myblogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private ModelMapper mapper;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository,ModelMapper mapper,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.mapper=mapper;
        this.postRepository=postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId) {
        postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"This comment does not belongs to this post");
        }
        return mapToDto(comment);
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to this post");
        }
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updatePostById(Long postId, Long commentId,CommentDto newComment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to this post");
        }
        comment.setBody(newComment.getBody());
        comment.setName(newComment.getName());
        comment.setEmail(newComment.getEmail());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    private CommentDto mapToDto(Comment comment)
    {
        CommentDto dto = mapper.map(comment, CommentDto.class);
        return dto;
    }
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment= mapper.map(commentDto, Comment.class);
        return comment;
    }
}
