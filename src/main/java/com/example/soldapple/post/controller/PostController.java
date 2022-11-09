package com.example.soldapple.post.controller;


import com.example.soldapple.post.dto.PostReqDto;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.PostRepository;
import com.example.soldapple.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping("")
    public String postCreate(@RequestBody @Valid PostReqDto postReqDto) {
        return postService.postCreate(postReqDto);
    }

    @GetMapping("/get")
    public List<Post> getPosts(){
        return postRepository.findAll();

    }

    @GetMapping("/detail/{postId}")
    public Optional<Post> detailPost(@PathVariable Long postId){
        return postRepository.findById(postId);

    }

    @GetMapping("/category/{category}")
    public Optional<List<Post>> categoryPost(@PathVariable String category){
        return postRepository.findAllByCategory(category);

    }

    @PutMapping("/{postId}")
    public String postEdit(@PathVariable Long postId, @RequestBody @Valid PostReqDto postReqDto){
        return postService.postEdit(postId, postReqDto);

    }

    @DeleteMapping("{postId}")
    public String postDelete(@PathVariable Long postId){
        return postService.postDelete(postId);

    }

}
