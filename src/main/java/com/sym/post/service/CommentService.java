package com.sym.post.service;

import com.sym.post.dto.CommentRequestDto;
import com.sym.post.repository.CommentRepository;
import com.sym.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<CommentRequestDto> searchComments(Long postId) {
        return List.of();
    }
    public void saveComment(CommentRequestDto dto) {

    }
    public void updateComment(CommentRequestDto dto) {

    }
    public void deleteComment(Long commentId) {

    }
}
