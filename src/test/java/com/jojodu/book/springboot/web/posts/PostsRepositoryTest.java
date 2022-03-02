package com.jojodu.book.springboot.web.posts;

import com.jojodu.book.springboot.domain.post.Posts;
import com.jojodu.book.springboot.domain.post.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest //SpringBootTest 를 사용할 경우 h2 데이터베이스를 자동실행해줌
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After //junit 에서 단위테스트가 끝날때마다 수행되는 메소드, 여러테스트가 동시
    //수행될때 h2에 데이터가 남아잇으면 다음테스트시 테스트사 실패할 수 있음.
    public void cleanup(){
        postsRepository.deleteAll(); //전체 삭제
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String author = "leesk83@sk.com";
        Long userId = 0L;

        //postsRepository.save() : 테이블 posts에 insert/update 실행
        //id값이 있다면 update 없다면 insert 실행됨.
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .userId(userId)
                .build());

        //when
        //postsRepository.findAll() : 테이블 posts에 있는 모든 데이터조회
        List<Posts> postsList = postsRepository.findAll();

        //then
        //조회한 데이터의 첫번째 row의 title과 content가 제대로 저장,조회되었는지 확인
        Posts posts = ((List<Posts>) postsList).get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getAuthor()).isEqualTo(author);
        assertThat(posts.getUserId()).isEqualTo(userId);
        //테스트 성공
    }

    @Test
    public void BastTimeEntity_등록(){

        //given
        //오늘 날짜 생성
        LocalDateTime now = LocalDateTime.of(2022,2,5,0,0,0);

        //신규 Posts 저장
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .userId(0L)
                .build());
        //when
        List<Posts> list = postsRepository.findAll();

        //then
        Posts posts = list.get(0);
        //저장된 date값 확인
        System.out.println(">>>>creatDate = "+posts.getCreateDate()+"/modifiedDate = "+posts.getModifiedDate());

        //post의 date값들이 설정한 날짜보다 미래인지 확인
        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
