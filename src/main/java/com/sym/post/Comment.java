package com.sym.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Table(indexes = {
        @Index(columnList = "text"),
        @Index(columnList = "writer"),
        @Index(columnList = "createDate"),
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Post post;
    @Column(nullable = false, length = 500)
    private String text;

    @CreatedBy
    @Column(nullable = false, length = 100)
    private String writer;
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createDate;
    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifier;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifyDate;

    private Comment(Post post, String text) {
        this.post = post;
        this.text = text;
    }
    public static Comment of(Post post, String text) {
        return new Comment(post, text);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return id != null && id.equals(comment.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
