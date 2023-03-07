package com.myblogrestapi.service.serviceImpl;

import com.myblogrestapi.entities.Post;
import com.myblogrestapi.exception.ResourceNotFoundException;
import com.myblogrestapi.payload.PostDto;
import com.myblogrestapi.payload.PostResponse;
import com.myblogrestapi.repository.PostRepository;
import com.myblogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;
    private ModelMapper mapper;
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNo,pageSize,sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setLast(posts.isLast());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());

        return postResponse;
    }



    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);
    }

    private PostDto mapToDto(Post post)
    {
        PostDto dto = mapper.map(post, PostDto.class);
        return dto;
    }
    private Post mapToEntity(PostDto postDto)
    {
        Post post = mapper.map(postDto, Post.class);
        return post;
    }
}
