package com.sym.post.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.sym.post.Post;
import com.sym.post.QPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PostRepository extends
        JpaRepository<Post, Long>,
        QuerydslPredicateExecutor<Post>, // Post Entity 모든 필드에 검색 기능을 추가해준다.
        QuerydslBinderCustomizer<QPost> // 검색 세부 기능 구현을 위해 customize 재정의
{
    Page<Post> findByTitle(String title, Pageable pageable);
    @Override
    default void customize(QuerydslBindings bindings, QPost root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.text , root.hashtag, root.createDate, root.writer);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.text).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createDate).first(DateTimeExpression::eq);
        bindings.bind(root.writer).first(StringExpression::containsIgnoreCase);
    }
}
