package com.jojodu.book.springboot.web;

import com.jojodu.book.springboot.service.posts.PostsService;
import com.jojodu.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    /**
     * 기존 스프링에서는 Controller 와 Service에서 @Autowired 를 통해서 bean을 주입받으나 여기선 안씀.
     * 대신 생성자를 통해 주입받는다. 생성자는
     * @RequiredArgsConstructor 롬복 어노테이션을 통해서 자동으로 생성된다.
     * (클래스에서 final 이 선언된 모든 필드를 인자값으로 하는 생성자를 생성해줌.)
     */

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }
}
