package com.example.soldapple.search.controller;

import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/{keyword}")
    public List<PostResponseDto> searchPost(@PathVariable String keyword){
        System.out.println(keyword);
        return searchService.searchPost(keyword);

    }

}
