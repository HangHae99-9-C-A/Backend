package com.example.soldapple.quarydsl.issues;

import com.example.soldapple.issues.responsedto.IssuesResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssuesRepositoryCustom {



    Page<IssuesResponseDto> findAllMyIssues(Pageable pageable);
    Page<IssuesResponseDto> findAllMyIssuesWithSearch(Pageable pageable, String string);

    //issues - category 정렬 + 내가 한 좋아요
    Page<IssuesResponseDto> findAllIssuesWithCategory(Pageable pageable, String category);
   Page<?> findAllIssuesWithCategoryWithSearch(Pageable pageable, String category, String search);
}
