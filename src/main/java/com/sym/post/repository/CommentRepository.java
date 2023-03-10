package com.sym.post.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.sym.post.domain.Comment;
import com.sym.post.domain.QComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CommentRepository extends
        JpaRepository<Comment, Long>,
        QuerydslPredicateExecutor<Comment>,
        QuerydslBinderCustomizer<QComment>
{
    List<Comment> findByPost_Id(Long postId);
    @Override
    default void customize(QuerydslBindings bindings, QComment root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.text, root.createDate, root.writer);
        bindings.bind(root.text).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createDate).first(DateTimeExpression::eq);
        bindings.bind(root.writer).first(StringExpression::containsIgnoreCase);
    }
}
