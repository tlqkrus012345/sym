package com.sym.post.service;

import com.sym.member.domain.Member;
import com.sym.member.repository.MemberRepository;
import com.sym.post.domain.Comment;
import com.sym.post.domain.Post;
import com.sym.post.dto.CommentRequestDto;
import com.sym.post.dto.CommentResponseDto;
import com.sym.post.repository.CommentRepository;
import com.sym.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<CommentRequestDto> searchComments(Long postId) {
        return commentRepository.findByPost_Id(postId)
                .stream()
                .map(CommentRequestDto::from)
                .collect(Collectors.toList());
    }
    public void saveComment(CommentRequestDto dto) {
        try {
            Post post = postRepository.getReferenceById(dto.getPostId());
            Member member = memberRepository.getReferenceById(dto.getMemberDto().getId());
            commentRepository.save(dto.toEntity(post,member));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. {}", e.getLocalizedMessage());
        }

    }
    public void updateComment(CommentRequestDto dto) {
        try {
            Comment comment = commentRepository.getReferenceById(dto.getId());
            if (dto.getText() != null) {
                comment.updateText(dto.getText());
            }
        } catch (EntityNotFoundException e) {
            log.warn("댓글 업데이트 실패. dto : {}", dto);
        }

    }
    public void deleteComment(Long commentId) {
        postRepository.deleteById(commentId);
    }
}
