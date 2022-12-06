package com.example.soldapple.issues.responsedto;

import com.example.soldapple.issues.entity.IssuesComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssuesCommentMemberResponseDto {
    private Long commentId;
    private String nickname;
    private String comment;

    public IssuesCommentMemberResponseDto(IssuesComment issuesComment){
        this.commentId = issuesComment.getIssuesCommentId();
        this.nickname = issuesComment.getMember().getNickname();
        this.comment = issuesComment.getIssuesComment();
    }
}
