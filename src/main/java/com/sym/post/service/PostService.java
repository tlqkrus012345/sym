package com.sym.post.service;

import com.sym.post.domain.Post;
import com.sym.post.domain.SearchType;
import com.sym.post.dto.PostRequestDto;
import com.sym.post.dto.PostWithCommentRequestDto;
import com.sym.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    public void savePost(PostRequestDto dto) {
        postRepository.save(dto.toEntity());
    }
    public void updatePost(PostRequestDto dto) {
        try {
            Post post = postRepository.getReferenceById(dto.getId());
            if (dto.getTitle() != null) post.updateTitle(dto.getTitle());
            if (dto.getText() != null) post.updateText(dto.getText());
            post.updateHashtag(dto.getHashtag());
        } catch (EntityNotFoundException e) {
            log.warn("게시판 수정 실패. 게시판을 찾을 수 없습니다. dto: {}", dto);
        }
    }
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
    @Transactional(readOnly = true)
    public Page<PostRequestDto> searchPosts(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return postRepository.findAll(pageable).map(PostRequestDto::from);
        }

        switch (searchType) {
            case TITLE: return postRepository.findByTitleContaining(searchKeyword, pageable).map(PostRequestDto::from);
            case TEXT: return postRepository.findByTextContaining(searchKeyword, pageable).map(PostRequestDto::from);
            case EMAIL: return postRepository.findByMember_EmailContaining(searchKeyword, pageable).map(PostRequestDto::from);
            case NICKNAME: return postRepository.findByMember_NickNameContaining(searchKeyword,pageable).map(PostRequestDto::from);
            case HASHTAG: return postRepository.findByHashtag("#"+ searchKeyword, pageable).map(PostRequestDto::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public PostWithCommentRequestDto getPost(Long postId) {
        return postRepository.findById(postId)
                .map(PostWithCommentRequestDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - postId: " + postId));
    }
    public long getPostCount() {
        return postRepository.count();
    }
    @Transactional(readOnly = true)
    public Page<PostRequestDto> searchPostHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }
        return postRepository.findByHashtag(hashtag, pageable)
                .map(PostRequestDto::from);
    }
    public List<String> getHastags() {
        return postRepository.findAllDistinctHashtags();
    }
}
