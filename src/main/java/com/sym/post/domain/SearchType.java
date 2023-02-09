package com.sym.post.domain;

import lombok.Getter;

@Getter
public enum SearchType {
    TITLE("제목"),
    TEXT("본문"),
    EMAIL("이메일"),
    NICKNAME("닉네임"),
    HASHTAG("해시태그");

    private final String description;
    SearchType(String description) {
        this.description = description;
    }
}
