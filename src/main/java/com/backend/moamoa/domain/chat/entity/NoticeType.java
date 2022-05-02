package com.backend.moamoa.domain.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoticeType {

    COMMENT("COMMENT_UPLOAD", "댓글", "새로운 댓글이 등록되었습니다."),
    DELETE_COMMENT("COMMENT_DELETE", "댓글", "댓글이 삭제되었습니다."),
    POST("POST_UPLOAD", "게시물", "새로운 게시글이 업로드되었습니다."),
    DELETE_POST("POST_DELETE", "게시물", "게시글이 삭제되었습니다."),
    CALLING("CALLING_NOTICE", "대화방 초대", "대화 신청이 도착했습니다."),
    MESSAGE("MESSAGE_NOTICE", "메세지", "새 메세지");

    private final String key;

    private final String title;

    private final String message;
}
