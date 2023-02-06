package com.sym.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostWithCommentResponseDto {
    private Long id;
    private String title;
    private String text;
    private String hashtag;
    private LocalDateTime createDate;
    private String email;
    private String nickName;
    private Set<CommentResponseDto> commentResponseDto;

    public static PostWithCommentResponseDto of(Long id,String title, String text,
                                               String hashtag, LocalDateTime createDate, String email, String nickName,
                                               Set<CommentResponseDto> commentResponseDto) {
        return new PostWithCommentResponseDto(id, title, text, hashtag, createDate, email, nickName, commentResponseDto);
    }
    public static PostWithCommentResponseDto from(PostWithCommentRequestDto dto) {
        String nickName = dto.getMemberDto().getNickName();
        if (nickName == null || nickName.isBlank()) {
            nickName = dto.getMemberDto().getEmail();
        }

        return new PostWithCommentResponseDto(
                dto.getId(),
                dto.getTitle(),
                dto.getText(),
                dto.getHashtag(),
                dto.getCreateDate(),
                dto.getMemberDto().getEmail(),
                nickName,
                dto.getCommentRequestDto().stream()
                        .map(CommentResponseDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}
