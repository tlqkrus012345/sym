package com.sym.post.dto;

import com.sym.member.dto.MemberDto;
import com.sym.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private Long id;
    private MemberDto memberDto;
    private String title;
    private String text;
    private String hashtag;
    private LocalDateTime createDate;
    private String writer;
    private LocalDateTime modifyDate;
    private String modifier;

    public static PostRequestDto of(Long id, MemberDto memberDto, String title, String text,
                                    String hashtag, LocalDateTime createDate, String writer,
                                    LocalDateTime modifyDate, String modifier) {
        return new PostRequestDto(id, memberDto, title, text, hashtag, createDate, writer, modifyDate, modifier);
    }
    public static PostRequestDto from(Post entity) {
        return new PostRequestDto(
                entity.getId(),
                new MemberDto(entity.getMember()),
                entity.getTitle(),
                entity.getText(),
                entity.getHashtag(),
                entity.getCreateDate(),
                entity.getWriter(),
                entity.getModifyDate(),
                entity.getModifier()
        );
    }
    public Post toEntity() {
        return Post.of(
        memberDto.toEntity(),
        title,
        text,
        hashtag
        );
    }
}

