package com.example.soldapple.member.entity;

import com.example.soldapple.like.entity.Like;
import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.personal.MyInfoRequestDto;
import com.example.soldapple.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String nickname;

    private String password;
    @NotBlank
    private String domain;

    private String avatarUrl;

    private String avatarUrlKey;

    @OneToMany(mappedBy = "member")
    List<Like> like = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<Post> post = new ArrayList<>();

    public Member(MemberReqDto memberReqDto) {
        this.email = memberReqDto.getEmail();
        this.nickname = memberReqDto.getNickname();
        this.password = memberReqDto.getPassword();
        this.domain = "Sold Apple";
        this.avatarUrl = null;
    }

    public Member(String email, String nickname, String domain) {
        this.email = email;
        this.nickname = nickname;
        this.password = null;
        this.domain = domain;
        this.avatarUrl = "https://s3.ap-northeast-2.amazonaws.com/myawsbucket.refined-stone/default/photoimg.png";
    }

    public Member(String email, String nickname, String domain, String avatarUrl) {
        this.email = email;
        this.nickname = nickname;
        this.password = null;
        this.domain = domain;
        this.avatarUrl = avatarUrl;
    }

    //myinfo 를 위해 생성하였습니다.
    public void update(MyInfoRequestDto myInfoRequestDto) {
        //   this.email = memberReqDto.getEmail();
        this.nickname = myInfoRequestDto.getNickname();
        //     this.password = memberReqDto.getPassword();
    }

    public void update(MyInfoRequestDto myInfoRequestDto, Map<String, String> imgUrl) {
//        this.email = myInfoRequestDto.getEmail();
        this.nickname = myInfoRequestDto.getNickname();
//        this.password = myInfoRequestDto.getPassword();
        this.avatarUrl = imgUrl.get("url");
        this.avatarUrlKey = imgUrl.get("key");
    }
}
