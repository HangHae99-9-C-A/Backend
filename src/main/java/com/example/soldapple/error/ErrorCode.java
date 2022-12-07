package com.example.soldapple.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //USER
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), "U001", "해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_RECOMMENDER(HttpStatus.NOT_FOUND.value(), "U002", "해당 추천인을 찾을 수 없습니다."),
    CANNOT_RECOMMEND_YOURSELF(HttpStatus.BAD_REQUEST.value(), "U003","자신의 닉네임은 적을 수 없습니다."),
    TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST.value(), "U004","만료된 액세스 토큰 입니다."),
    REFRESH_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST.value(), "U005","만료된 리프레시 토큰 입니다."),
    DONT_USE_THIS_TOKEN(HttpStatus.BAD_REQUEST.value(), "U006","유효하지 않은 토큰 입니다."),
    NOT_MATCHED_PASSWORD(HttpStatus.BAD_REQUEST.value(), "U006","비밀번호가 틀렸습니다."),

    //CHAT-USER
    NOT_FOUND_USER_IN_CHAT(HttpStatus.NOT_FOUND.value(), "CU001","해당 유저를 찾을 수 없습니다."),

    //POST
    CANNOT_FIND_POST_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "P001","해당 게시물을 찾을 수 없습니다."),
    CANNOT_FIND_POST(HttpStatus.NOT_FOUND.value(), "P002","해당 게시물을 찾을 수 없습니다."),
    ONLY_CAN_DO_POST_WRITER(HttpStatus.FORBIDDEN.value(), "P003","작성자만 권한이 있습니다."),

    //ISSUE
    CANNOT_FIND_ISSUE(HttpStatus.NOT_FOUND.value(), "I001","해당 이의제기 글을 찾을 수 없습니다."),
    ONLY_CAN_DO_ISSUE_WRITER(HttpStatus.FORBIDDEN.value(), "I002","작성자만 권한이 있습니다."),

    //CHATROOM
    NOT_FOUND_ANOTHER_USER(HttpStatus.NOT_FOUND.value(), "R001","상대방을 찾을 수 없습니다."),
    UNKNOWN_CHATROOM(HttpStatus.BAD_REQUEST.value(), "R002","알 수 없는 채팅방 입니다."),
    CANNOT_FOUND_CHATROOM(HttpStatus.NOT_FOUND.value(), "R003","존재하지 않는 채팅방입니다."),
    FORBIDDEN_CHATROOM(HttpStatus.FORBIDDEN.value(), "R004","접근 불가능한 채팅방 입니다."),
    CANNOT_MAKE_ROOM_ALONE(HttpStatus.BAD_REQUEST.value(), "R005","자기자신에게 채팅을 신청할 수 없습니다"),
    DOESNT_EXIST_OTHER_USER(HttpStatus.NOT_FOUND.value(), "R006","채팅상대가 존재하지 않습니다"),
    INVALID_MESSAGE(HttpStatus.NOT_FOUND.value(), "R007","메세지를 확인할 수 없습니다."),

    //COMMENT
    DOESNT_EXIST_POST_FOR_WRITE(HttpStatus.NOT_FOUND.value(), "C001","해당하는 게시글이 존재하지 않습니다."),
    ONLY_CAN_DO_COMMENT_WRITER(HttpStatus.FORBIDDEN.value(), "C002","글을 작성한 유저만 권한이 있습니다."),

    //ISSUE COMMENT
    DOESNT_EXIST_ISSUE_FOR_WRITE(HttpStatus.NOT_FOUND.value(), "IC001","해당하는 이의제기 글이 존재하지 않습니다."),
    ONLY_CAN_DO_ISSUE_COMMENT_WRITER(HttpStatus.FORBIDDEN.value(), "IC002","글을 작성한 유저만 권한이 있습니다."),

    //LIKE
    CANNOT_LIKE_BY_POST_WRITER(HttpStatus.NOT_FOUND.value(), "L001","글 작성자는 찜 할 수 없습니다."),
    CANNOT_DISLIKE_BY_POST_WRITER(HttpStatus.NOT_FOUND.value(), "L002","글 작성자는 찜 취소 할 수 없습니다."),

    ;

    private final int httpStatus;
    private final String code;
    private final String message;

    }

