package com.sym.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String text;
    private LocalDateTime createDate;
    private String email;
    private String nickName;

    public static CommentResponseDto of(Long id, String text, LocalDateTime createDate, String email, String nickName) {
        return new CommentResponseDto(id, text, createDate, email, nickName);
    }

    public static CommentResponseDto from(CommentRequestDto dto) {
        String nickName = dto.getMemberDto().getNickName();
        if (nickName == null || nickName.isBlank()) {
            nickName = dto.getMemberDto().getEmail();
        }
        return new CommentResponseDto(
                dto.getId(),
                dto.getText(),
                dto.getCreateDate(),
                dto.getMemberDto().getEmail(),
                nickName
        );
    }
}
