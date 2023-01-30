package com.sym.post;

import java.time.LocalDateTime;

public class Comment {
    private Long id;
    private Post post;
    private String text;

    private String writer;
    private LocalDateTime createDate;
    private String modifier;
    private LocalDateTime modifyDate;
}
