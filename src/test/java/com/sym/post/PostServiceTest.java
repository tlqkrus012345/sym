package com.sym.post;

import com.sym.member.domain.Member;
import com.sym.member.dto.MemberDto;
import com.sym.member.dto.MemberRegisterRequestDto;
import com.sym.post.domain.Post;
import com.sym.post.domain.SearchType;
import com.sym.post.dto.PostRequestDto;
import com.sym.post.dto.PostWithCommentRequestDto;
import com.sym.post.repository.PostRepository;
import com.sym.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@DisplayName("게시글 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;

    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다")
    @Test
    void noType_search_postPageReturn() {
        Pageable pageable = Pageable.ofSize(20);
        given(postRepository.findAll(pageable)).willReturn(Page.empty());

        Page<PostRequestDto> posts = postService.searchPosts(null, null, pageable);

        assertThat(posts).isEmpty();
        then(postRepository).should().findAll(pageable);
    }

    @DisplayName("검색어를 통해 게시글을 검색하면, 게시글 페이지를 반환한다")
    @Test
    void searchParam_search_postPageReturn() {
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(postRepository.findByTitle(searchKeyword, pageable)).willReturn(Page.empty());

        Page<PostRequestDto> posts = postService.searchPosts(searchType, searchKeyword, pageable);

        assertThat(posts).isEmpty();
        then(postRepository).should().findByTitle(searchKeyword, pageable);
    }
    @DisplayName("게시글을 조회하면, 게시글을 반환한다")
    @Test
    void id_search_post() {
        Long postId = 1L;
        Post post = createPost();
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        PostWithCommentRequestDto dto = postService.getPost(postId);

        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", post.getTitle())
                .hasFieldOrPropertyWithValue("text", post.getText())
                .hasFieldOrPropertyWithValue("hashtag", post.getHashtag());
        then(postRepository).should().findById(postId);
    }
    @DisplayName("없는 게시글을 조회하면, 예외를 던진다")
    @Test
    void noPost_search_throwException() {
        Long postId = 0L;
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        Throwable t = catchThrowable(() -> postService.getPost(postId));

        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("게시글이 없습니다 : " + postId);
        then(postRepository).should().findById(postId);
    }

    @DisplayName("게시글을 생성한다")
    @Test
    void post_create_savePost() {

        LocalDateTime now = LocalDateTime.of(2023,2,5,9,17);
        PostRequestDto dto = createPostDto();
        given(postRepository.save(any(Post.class))).willReturn(createPost());

        postService.savePost(dto);

        then(postRepository).should().save(any(Post.class));
    }
    @DisplayName("게시글 내용을 통해 게시글을 수정한다")
    @Test
    void idAndPost_update_updatePost() {
        LocalDateTime now = LocalDateTime.of(2023,2,5,9,17);
        Post post = createPost();
        PostRequestDto dto = createPostDto("title~", "text~", "#sp");
        given(postRepository.getReferenceById(dto.getId())).willReturn(post);

        postService.updatePost(dto);

        assertThat(post)
                .hasFieldOrPropertyWithValue("title",dto.getTitle())
                .hasFieldOrPropertyWithValue("text",dto.getText())
                .hasFieldOrPropertyWithValue("hashtag",dto.getHashtag());
        then(postRepository).should().getReferenceById(dto.getId());
    }
    @DisplayName("없는 게시글의 정보를 수정하면, 경고 로그를 찌고 아무 것도 안 한다.")
    @Test
    void noPost_update_warn() {
        PostRequestDto dto = createPostDto("title~","text~","#s");
        given(postRepository.getReferenceById(dto.getId())).willThrow(EntityNotFoundException.class);

        postService.updatePost(dto);

        then(postRepository).should().getReferenceById(dto.getId());
    }
    @DisplayName("게시글 id를 통해 게시글을 삭제한다")
    @Test
    void id_delete_deletePost() {
        LocalDateTime now = LocalDateTime.of(2023,2,5,9,17);
        Long postId = 1L;
        willDoNothing().given(postRepository).deleteById(postId);

        postService.deletePost(1L);

        then(postRepository).should().deleteById(postId);
    }
    private PostRequestDto createPostDto() {
        return createPostDto("title", "text", "#java");
    }
    private PostRequestDto createPostDto(String title, String text, String hashtag) {
        return PostRequestDto.of(1L,
                createMemberDto(),
                title,
                text,
                hashtag,
                LocalDateTime.now(),
                "kyu",
                LocalDateTime.now(),
                "kyu");
    }
    private MemberDto createMemberDto() {
        MemberRegisterRequestDto dto = new MemberRegisterRequestDto(
                "KEY@", "KEY", "KEY"
        );
        return dto.toMemberDto(dto);
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
