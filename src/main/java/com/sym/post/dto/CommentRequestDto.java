package com.sym.post.dto;

import com.sym.member.domain.Member;
import com.sym.member.dto.MemberDto;
import com.sym.post.domain.Comment;
import com.sym.post.domain.Post;
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

    public static CommentRequestDto of(Long postId, MemberDto memberDto, String text) {
        return new CommentRequestDto(null,postId,memberDto,text,null,null,null,null);
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
    public Comment toEntity(Post post, Member member) {
        return Comment.of(
                post,
                member,
                text
        );
    }
}
