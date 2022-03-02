package com.jojodu.book.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojodu.book.springboot.domain.post.Posts;
import com.jojodu.book.springboot.domain.post.PostsRepository;
import com.jojodu.book.springboot.web.dto.PostUpdateRequestDto;
import com.jojodu.book.springboot.web.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest 대신 @SpringBootTest 를 사용한 이유
//@WebMvcTest의 경우 jpa기능이 동작하지 않으므로 jpa가 포함된 기능을 테스트시에는
//@SpringBootTest 와 TestRestTemplate 를 사용하면 된다.
//SpringBootTest.WebEnvironment.RANDOM_PORT : 랜덤포트로 테스트할 시 사용
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before //테스트 시작전 실행
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()).build();
    }

    @After // 테스트 후 실행
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    //게시글 저장 테스트
    @Test
    @WithMockUser(roles = "USER") //테스트시 권한 "UESR" 를 부여하여 api요청할수 있게함.
    //@WithMockUser 는 MockMvc에서만 동작하기 때문에 코드 방식변경이 필요함.
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String author = "";
        Long userId = 0L;

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .userId(userId)
                .build();

        //PostsApiController 에 게시글 저장 url을 설정
        String url = "http://localhost:"+port+"/api/v1/posts";

        //when
        //기존
        //ResponseEntity 를 이용해서 해당url 에 requestDto로 게시글저장 실행
        //ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //@SpringBootTest 에서 MockMvc 사용할 수 있는 방법으로 수정함.
        //생성된 MockMvc를 통해 api를 테스트합니다.
        //본문body 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환합니다.
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        //기존
        //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK); //요청응답 status 정상확인
        //assertThat(responseEntity.getBody()).isGreaterThan(0L); //저장후 키값이 정상리턴되었는지 확인

        List<Posts> all = postsRepository.findAll(); //게시글전체 조회
        assertThat(all.get(0).getTitle()).isEqualTo(title); //게시글 첫번재row의 데이터가 정상인지 확인
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getAuthor()).isEqualTo(author);
        assertThat(all.get(0).getUserId()).isEqualTo(userId);
    }

    //게시글 수정 테스트
    @Test
    @WithMockUser(roles = "USER")
    public void Posts_수정된다() throws Exception {
        //given
        //테스트용 게시글 db 입력
        Posts savedPosts = postsRepository.save(
                Posts.builder().title("title").content("content").author("author").userId(0L).build()
        );

        Long updateId = savedPosts.getId();
        String newTitle = "title2";
        String newContent = "content2";

        //게시글 수정에 사용할 requestDto 생성
        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder().title(newTitle).content(newContent).build();

        //PostsApiController 에 게시글 수정 url을 설정
        String url = "http://localhost:"+port+"/api/v1/posts/"+updateId;

        //게시글 수정 요청을 보낼 requestDto를 담은 requestEntity 생성
        //HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        //ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                        .andExpect(status().isOk());

        //then
        //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(newTitle);
        assertThat(all.get(0).getContent()).isEqualTo(newContent);
    }

}
