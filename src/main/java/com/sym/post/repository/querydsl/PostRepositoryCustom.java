package com.sym.post.repository.querydsl;

import java.util.List;

public interface PostRepositoryCustom {
    List<String> findAllDistinctHashtags();
}
