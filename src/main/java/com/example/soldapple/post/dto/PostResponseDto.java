package com.example.soldapple.post.dto;

import com.example.soldapple.post.entity.Comment;
import com.example.soldapple.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String title;
    private String nickname;
    private String category;
    private Long expectPrice;
    private Long userPrice;
    private String content;

    private List<Comment> comments;
    private Boolean isLike;

    public PostResponseDto(Post post, Boolean isLike){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.nickname =post.getMember().getNickname();
        this.category = post.getCategory();
        this.expectPrice = post.getExpectPrice();
        this.userPrice = post.getUserPrice();
        this.content = post.getContent();
        this.comments = post.getComments();
        this.isLike = isLike;
    }
}
