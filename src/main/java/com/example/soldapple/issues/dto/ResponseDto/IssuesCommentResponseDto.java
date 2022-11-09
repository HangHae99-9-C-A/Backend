package com.example.soldapple.issues.dto.ResponseDto;

import com.example.soldapple.issues.entity.IssuesComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssuesCommentResponseDto {
    private String nickname;
    private String comment;

    public IssuesCommentResponseDto(IssuesComment issuesComment){
        this.nickname = issuesComment.getMember().getNickname();
        this.comment = issuesComment.getIssuesComment();
    }
}
