package com.myblogrestapi.service;

import com.myblogrestapi.payload.PostDto;
import com.myblogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostDto getPostById(long id);

    PostDto updatePost(long id, PostDto postDto);

    void deletePost(long id);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);


}
