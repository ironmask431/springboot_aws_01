package com.jojodu.book.springboot.web;

import com.jojodu.book.springboot.domain.post.Posts;
import com.jojodu.book.springboot.domain.post.PostsRepository;
import com.jojodu.book.springboot.web.dto.PostUpdateRequestDto;
import com.jojodu.book.springboot.web.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@WebMvcTest 대신 @SpringBootTest 를 사용한 이유
//@WebMvcTest의 경우 jpa기능이 동작하지 않으므로 jpa가 포함된 기능을 테스트시에는
//@SpringBootTest 와 TestRestTemplate 를 사용하면 된다.
//SpringBootTest.WebEnvironment.RANDOM_PORT : 랜덤포트로 테스트할 시 사용
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    //게시글 저장 테스트
    @Test
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        //PostsApiController 에 게시글 저장 url을 설정
        String url = "http://localhost:"+port+"/api/v1/posts";

        //when
        //ResponseEntity 를 이용해서 해당url 에 requestDto로 게시글저장 실행
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK); //요청응답 status 정상확인
        assertThat(responseEntity.getBody()).isGreaterThan(0L); //저장후 키값이 정상리턴되었는지 확인

        List<Posts> all = postsRepository.findAll(); //게시글전체 조회
        assertThat(all.get(0).getTitle()).isEqualTo(title); //게시글 첫번재row의 데이터가 정상인지 확인
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    //게시글 수정 테스트
    @Test
    public void Posts_수정된다() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(
                Posts.builder().title("title").content("content").author("author").build()
        );

        Long updateId = savedPosts.getId();
        String newTitle = "title2";
        String newContent = "content2";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder().title(newTitle).content(newContent).build();

        //PostsApiController 에 게시글 수정 url을 설정
        String url = "http://localhost:"+port+"/api/v1/posts/"+updateId;

        HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(newTitle);
        assertThat(all.get(0).getContent()).isEqualTo(newContent);
    }

}
