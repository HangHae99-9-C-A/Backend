package com.example.soldapple.issues.dto.ResponseDto;

import com.example.soldapple.global.TimeConverter;
import com.example.soldapple.issues.entity.IssuesComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssuesCommentResponseDto {
    private Long issuesCommentId;
    private String nickname;
    private String comment;
    private String createdAt;

    private String modifiedAt;

    public IssuesCommentResponseDto(IssuesComment issuesComment){
        this.issuesCommentId = issuesComment.getIssuesCommentId();
        this.nickname = issuesComment.getMember().getNickname();
        this.comment = issuesComment.getIssuesComment();
        this.createdAt = TimeConverter.convertTime ( issuesComment.getCreatedAt () );
        this.modifiedAt = TimeConverter.convertTime ( issuesComment.getModifiedAt () );
    }
}
