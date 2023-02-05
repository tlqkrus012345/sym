package com.sym;

import com.sym.config.JpaConfig;
import com.sym.member.domain.Member;
import com.sym.member.repository.MemberRepository;
import com.sym.post.Post;
import com.sym.post.repository.CommentRepository;
import com.sym.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class) // Auditing 떄문에 넣는다
@DataJpaTest // 생성자 주입 가능하게 한다. 각 테스트 메소드마다 트랜잭션이 걸려서 전부 롤백처리 된다. 그래서 update 쿼리 안나간다
public class JpaRepositoryTest {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    public JpaRepositoryTest(
            @Autowired PostRepository postRepository,
            @Autowired CommentRepository commentRepository,
            @Autowired MemberRepository memberRepository
    ) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void crud_select() {

        List<Post> posts = postRepository.findAll();

        assertThat(posts)
                .isNotNull()
                .hasSize(3);
    }

    @DisplayName("insert 테스트")
    @Test
    void crud_insert() {
        long preCount = postRepository.count();
        Member member = memberRepository.save(Member.builder()
                        .email("kyu@")
                        .nickName("kyu")
                        .password("123")
                        .build());
        Post post = Post.of(member,"JPA Title", "Hello JPA", "Black");

        Post savedPost = postRepository.save(post);

        assertThat(postRepository.count()).isEqualTo(preCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void crud_update() {
        Post post = postRepository.findById(1L).orElseThrow();
        String updatedHashtag = "sky";
        post.updateHashtag(updatedHashtag);

        Post savedPost = postRepository.save(post);
        //postRepository.flush()
        //postRepository.saveAndFlush() 하이버네이트가 쿼리를 생성하고 db에 반영하기 위해 update 쿼리를 생성한다 하지만 롤백

        assertThat(savedPost).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }
    @DisplayName("delete 테스트")
    @Test
    void crud_delete() {
        Post post = postRepository.findById(1L).orElseThrow();
        long prePostCount = postRepository.count();
        long preCommentCount = commentRepository.count();
        int deleteCommentSize = post.getComments().size();

        postRepository.delete(post);

        assertThat(postRepository.count()).isEqualTo(prePostCount - 1);
        assertThat(commentRepository.count()).isEqualTo(preCommentCount - deleteCommentSize);
    }
}
