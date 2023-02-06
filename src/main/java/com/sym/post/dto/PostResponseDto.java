package com.sym.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String text;
    private String hashtag;
    private LocalDateTime createDate;
    private String email;
    private String nickName;
    public static PostResponseDto of(Long id, String title, String text, String hashtag, LocalDateTime createDate, String email, String nickName) {
        return new PostResponseDto(id, title, text, hashtag, createDate, email, nickName);
    }
    public static PostResponseDto from(PostRequestDto dto) {
        String nickName = dto.getMemberDto().getNickName();
        if (nickName == null || nickName.isBlank()) {
            nickName = dto.getMemberDto().getEmail();
        }
        return new PostResponseDto(
                dto.getId(),
                dto.getTitle(),
                dto.getText(),
                dto.getHashtag(),
                dto.getCreateDate(),
                dto.getMemberDto().getEmail(),
                nickName
        );
    }
}
