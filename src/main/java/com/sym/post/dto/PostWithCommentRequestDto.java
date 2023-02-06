package com.sym.post.dto;

import com.sym.member.dto.MemberDto;
import com.sym.post.domain.Post;
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
public class PostWithCommentRequestDto {
    private Long id;
    private MemberDto memberDto;
    private Set<CommentRequestDto> commentRequestDto;
    private String title;
    private String text;
    private String hashtag;
    private LocalDateTime createDate;
    private String writer;
    private LocalDateTime modifyDate;
    private String modifier;
    public static PostWithCommentRequestDto of(Long id, MemberDto memberDto, Set<CommentRequestDto> commentRequestDto,String title, String text,
                                    String hashtag, LocalDateTime createDate, String writer,
                                    LocalDateTime modifyDate, String modifier) {
        return new PostWithCommentRequestDto(id, memberDto, commentRequestDto, title, text, hashtag, createDate, writer, modifyDate, modifier);
    }
    public static PostWithCommentRequestDto from(Post entity) {
        return new PostWithCommentRequestDto(
                entity.getId(),
                new MemberDto(entity.getMember()),
                entity.getComments().stream()
                        .map(CommentRequestDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getTitle(),
                entity.getText(),
                entity.getHashtag(),
                entity.getCreateDate(),
                entity.getWriter(),
                entity.getModifyDate(),
                entity.getModifier()
        );
    }

}
