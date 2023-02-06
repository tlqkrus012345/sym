package com.sym.post;

import com.sym.post.dto.PostRequestDto;
import com.sym.post.dto.PostWithCommentRequestDto;
import com.sym.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    public void savePost(PostRequestDto dto) {
    }
    public void updatePost(PostRequestDto dto) {
    }
    public void deletePost(Long postId) {
    }
    @Transactional(readOnly = true)
    public Page<PostRequestDto> searchPosts(SearchType searchType, String searchKeyword, Pageable pageable) {
            return Page.empty();
    }

    @Transactional(readOnly = true)
    public PostWithCommentRequestDto getPost(Long postId) {
        return null;
    }
}
