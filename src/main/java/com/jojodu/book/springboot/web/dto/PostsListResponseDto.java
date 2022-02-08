package com.jojodu.book.springboot.web.dto;

import com.jojodu.book.springboot.domain.post.Posts;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getTitle();
        this.modifiedDate = entity.getModifiedDate();
    }
}
