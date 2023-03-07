package com.myblogrestapi.controller;

import com.myblogrestapi.payload.CommentDto;
import com.myblogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //localhost:8080/api/posts/{postId}/comments
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createNewComment(@PathVariable(value = "postId")long postId,
                                                       @RequestBody CommentDto commentDto)
    {
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }
//    localhost:8080/api/posts/{postId}/comments
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId")long postId)
    {
        return new ResponseEntity<>(commentService.getAllCommentsByPostId(postId),HttpStatus.OK);
    }
//  localhost:8080/api/posts/{postId}/comments/{commentId}
    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getOneCommentByCommentId(@PathVariable(value = "postId")Long postId,
                                                               @PathVariable(value = "commentId")Long commentId)
    {
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId),HttpStatus.OK);
    }
    // localhost:8080/api/posts/{postId}/comments/{commentId}
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId")Long postId,
                                                    @PathVariable(value = "commentId")Long commentId)
    {
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }
    // localhost:8080/api/posts/{postId}/comments/{commentId}
    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable(value = "postId")Long postId,
                                                        @PathVariable(value = "commentId")Long commentId,
                                                        @RequestBody CommentDto newComment

    ){
        return new ResponseEntity<>(commentService.updatePostById(postId,commentId,newComment),HttpStatus.OK);
    }
}
