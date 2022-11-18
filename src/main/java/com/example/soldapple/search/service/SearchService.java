package com.example.soldapple.search.service;

import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;

    @Transactional
    public List<PostResponseDto> searchPost(String keyword){
        List<PostResponseDto> postResponseDtos = searchRepository.findByTitleContainingOrContentContaining(keyword, keyword);

        return postResponseDtos;
    }
}
