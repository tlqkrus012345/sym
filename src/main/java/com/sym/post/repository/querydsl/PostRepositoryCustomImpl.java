package com.sym.post.repository.querydsl;

import com.sym.post.domain.Post;
import com.sym.post.domain.QPost;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class PostRepositoryCustomImpl extends QuerydslRepositorySupport implements  PostRepositoryCustom {

    public PostRepositoryCustomImpl() {
        super(Post.class);
    }
    @Override
    public List<String> findAllDistinctHashtags() {
        QPost post = QPost.post;

        return from(post)
                .distinct()
                .select(post.hashtag)
                .where(post.hashtag.isNotNull())
                .fetch();
    }
}
