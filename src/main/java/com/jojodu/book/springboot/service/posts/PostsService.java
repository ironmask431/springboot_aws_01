package com.jojodu.book.springboot.service.posts;

import com.jojodu.book.springboot.domain.post.PostsRepository;
import com.jojodu.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    //postsRepository 를 통해서 insert를 하고, pk id를 리턴받음
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
