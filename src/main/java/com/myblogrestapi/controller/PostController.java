package com.myblogrestapi.controller;

import com.myblogrestapi.entities.Post;
import com.myblogrestapi.payload.PostDto;
import com.myblogrestapi.payload.PostResponse;
import com.myblogrestapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create a new blog posts
    //localhost:8080/api/posts
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createNewPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    ////localhost:8080/api/posts?pageNo={pageNo}&pageSize={pageSize}&sortBy={sortBy}&sortDir={sortDirection}
    //get all posts
    @GetMapping
    public PostResponse getAllPosts(
          @RequestParam(value = "pageNo",defaultValue ="0",required = false)int pageNo,
          @RequestParam(value = "PageSize",defaultValue = "10",required = false)int pageSize,
          @RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy,
          @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }
    //localhost:8080/api/posts/{id}
    //get a post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id")long id)
    {
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }
    //localhost:8080/api/posts/{id}
    //update a post
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePostById(@PathVariable(name = "id")long id,@RequestBody PostDto postDto)
    {
        return new ResponseEntity<>(postService.updatePost(id,postDto),HttpStatus.OK);
    }
    //localhost:8080/api/posts/{id}
    //delete a post by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePostById(@PathVariable(value = "id")long id)
    {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted Successfully",HttpStatus.OK);
    }


}
