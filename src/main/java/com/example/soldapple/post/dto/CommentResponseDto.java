package com.example.soldapple.post.dto;

import com.example.soldapple.global.TimeConverter;
import com.example.soldapple.post.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String nickname;
    private String avatarUrl;
    private String comment;
    private String createdAt;
    private String modifiedAt;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getId();
        this.nickname = comment.getMember().getNickname();
        this.avatarUrl = comment.getMember().getAvatarUrl();
        this.comment = comment.getComment();
        this.createdAt= TimeConverter.convertTime ( comment.getCreatedAt() );
        this.modifiedAt = TimeConverter.convertTime ( comment.getModifiedAt () );
    }
}