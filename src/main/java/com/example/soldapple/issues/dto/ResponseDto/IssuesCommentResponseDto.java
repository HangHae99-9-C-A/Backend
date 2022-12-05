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
    private Long commentId;
    private String nickname;
    private Long memberId;
    private String avatarUrl;
    private String comment;
    private String createdAt;
    private String modifiedAt;

    private Boolean myComment;

    public IssuesCommentResponseDto(IssuesComment issuesComment, Boolean myComment){
        this.commentId = issuesComment.getIssuesCommentId();
        this.nickname = issuesComment.getMember().getNickname();
        this.memberId = issuesComment.getMember().getId();
        this.avatarUrl = issuesComment.getMember().getAvatarUrl();
        this.comment = issuesComment.getIssuesComment();
        this.createdAt = TimeConverter.convertTime ( issuesComment.getCreatedAt () );
        this.modifiedAt = TimeConverter.convertTime ( issuesComment.getModifiedAt () );
        this.myComment = myComment;
    }
}