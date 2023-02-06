package com.sym.post;

import com.sym.member.domain.Member;
import com.sym.member.dto.MemberDto;
import com.sym.member.dto.MemberRegisterRequestDto;

import com.sym.post.dto.CommentRequestDto;
import com.sym.post.dto.PostRequestDto;
import com.sym.post.repository.CommentRepository;
import com.sym.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperties;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
@DisplayName("댓글 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;

    @DisplayName("게시판 id 검색을 통해 해당하는 댓글 리스트를 반환한다.")
    @Test
    void id_search_comments() {

        Long postId = 1L;
        Comment expected = createComment("text");
        given(commentRepository.findByPost_Id(postId)).willReturn(List.of(expected));

        List<CommentRequestDto> actual = commentService.searchComments(postId);

        assertThat(actual)
                .hasSize(1)
                        .first().hasFieldOrPropertyWithValue("text", expected.getText());
        then(commentRepository).should().findByPost_Id(postId);
    }
    @DisplayName("댓글을 저장한다.")
    @Test
    void id_create_saveComment() {
        CommentRequestDto dto = createCommentDto("comment");
        given(postRepository.getReferenceById(dto.getPostId())).willReturn(createPost());
        given(commentRepository.save(any(Comment.class))).willReturn(null);

        commentService.saveComment(dto);

        then(postRepository).should().getReferenceById(dto.getId());
        then(commentRepository).should().save(any(Comment.class));
    }
    @DisplayName("댓글을 수정한다")
    @Test
    void comment_update_updateComment() {
        String preText = "text";
        String nowText = "Spr";
        Comment comment = createComment(preText);
        CommentRequestDto commentRequestDto = createCommentDto(nowText);
        given(commentRepository.getReferenceById(commentRequestDto.getId())).willReturn(comment);

        commentService.updateComment(commentRequestDto);

        assertThat(comment.getText())
                .isNotEqualTo(preText)
                .isEqualTo(nowText);
        then(commentRepository).should().getReferenceById(commentRequestDto.getId());
    }
    @DisplayName("댓글을 삭제한다")
    @Test
    void id_delete_deleteComment() {
        Long commentId = 1L;
        willDoNothing().given(commentRepository).deleteById(commentId);

        commentService.deleteComment(commentId);

        then(commentRepository).should().deleteById(commentId);
    }
    @DisplayName("없는 댓글 정보를 수정하려고 하면, 경고 로그를 찍고 아무 것도 안 한다.")
    @Test
    void noComment_update_warn() {
        CommentRequestDto dto = createCommentDto("text");
        given(commentRepository.getReferenceById(dto.getId())).willThrow(EntityNotFoundException.class);

        commentService.updateComment(dto);

        then(commentRepository).should().getReferenceById(dto.getId());
    }

    private CommentRequestDto createCommentDto(String text) {
        return CommentRequestDto.of(
                1L,
                1L,
                createMemberDto(),
                text,
                LocalDateTime.now(),
                "kyu",
                LocalDateTime.now(),
                "kyu"
        );
    }
    private MemberDto createMemberDto() {
        MemberRegisterRequestDto dto = new MemberRegisterRequestDto(
                "KEY@", "KEY", "KEY"
        );
        return dto.toMemberDto(dto);
    }
    private Comment createComment(String text) {
        return Comment.of(
                Post.of(createMember(), "title", "text", "#"),
                createMember(),
                text
        );
    }
    private Member createMember() {
        return  Member.builder()
                .email("kyu@")
                .nickName("kty")
                .password("kkk")
                .build();
    }
    private Post createPost() {
        return Post.of(
                createMember(),
                "title",
                "text",
                "#jh"
        );
    }
}
