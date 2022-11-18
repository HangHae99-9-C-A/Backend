package com.example.soldapple.quarydsl.post;

import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/test")
@RequiredArgsConstructor
@RestController
public class PostControllerTest {

    private final PostRepository postRepository;


    @GetMapping
    public Page<PostResponseDto> testPaging(Pageable pageable) {
        return postRepository.findMyQuery(pageable);

    }

}
