package com.sym.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createDate"),
        @Index(columnList = "writer"),
})
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 2000)
    private String text;
    private String hashtag;

    @OrderBy("id")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final Set<Comment> comments = new LinkedHashSet<>();

    @CreatedBy @Column(nullable = false, length = 100)
    private String writer;
    @CreatedDate @Column(nullable = false)
    private LocalDateTime createDate;
    @LastModifiedBy @Column(nullable = false, length = 100)
    private String modifier;
    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime modifyDate;

    private Post(String title, String text, String hashtag) {
        this.title = title;
        this.text = text;
        this.hashtag = hashtag;
    }
    public static Post of(String title, String text, String hashtag) {
        return new Post(title, text, hashtag);
    }
    public void updateHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
    /**
     * Post를 컬렉션에서 사용한다면?
     * 서로 다른 엔티티가 같은 조건이 무엇인가
     * 리스트에 넣거나 리스트에서 중복 요소를 제거 또는 정렬할 때 비교할 수 있어야 한다.
     * 따라서 equals와 hashcode를 정의해야 한다.
     * 동등성 검사할 때 데이터베이스와 연동된 엔티티가 두 객체 검사 기준은 ID !!
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return id != null && id.equals(post.id); // 아직 영속화되지 않은 데이터는 동등성 검사 탈락한다.
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
