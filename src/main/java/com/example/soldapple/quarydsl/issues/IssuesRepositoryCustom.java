package com.example.soldapple.quarydsl.issues;

import com.example.soldapple.issues.dto.ResponseDto.IssuesResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssuesRepositoryCustom {



    Page<IssuesResponseDto> findMyQuery(Pageable pageable);

    //issues - category 정렬 + 내가 한 좋아요
    Page<IssuesResponseDto> findAllIssuesWithCategory(Pageable pageable, String category);
}