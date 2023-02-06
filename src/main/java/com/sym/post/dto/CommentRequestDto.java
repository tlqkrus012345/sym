package com.sym.post.dto;

import com.sym.member.dto.MemberDto;
import com.sym.post.Comment;
import com.sym.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private Long id;
    private Long postId;
    private MemberDto memberDto;
    private String text;
    private LocalDateTime createDate;
    private String writer;
    private LocalDateTime modifyDate;
    private String modifier;

    public static CommentRequestDto of(Long id, Long postId, MemberDto memberDto, String text, LocalDateTime createDate,
                                       String writer, LocalDateTime modifyDate, String modifier) {
        return new CommentRequestDto(id,postId,memberDto,text,createDate,writer,modifyDate,modifier);
    }
    public static CommentRequestDto from(Comment entity) {
        return new CommentRequestDto(
                entity.getId(),
                entity.getPost().getId(),
                new MemberDto(entity.getMember()),
                entity.getText(),
                entity.getCreateDate(),
                entity.getWriter(),
                entity.getModifyDate(),
                entity.getModifier()
        );
    }
    public Comment toEntity(Post entity) {
        return Comment.of(
                entity,
                memberDto.toEntity(),
                text
        );
    }
}
