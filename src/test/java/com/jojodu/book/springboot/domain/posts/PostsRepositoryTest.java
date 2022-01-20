package com.jojodu.book.springboot.domain.posts;

import com.jojodu.book.springboot.domain.post.Posts;
import com.jojodu.book.springboot.domain.post.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

        //postsRepository.save() : 테이블 posts에 insert/update 실행
        //id값이 있다면 update 없다면 insert 실행됨.
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("leesk83@sk.com")
                .build());

        //when
        //postsRepository.findAll() : 테이블 posts에 있는 모든 데이터조회
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = ((List<Posts>) postsList).get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        //테스트 성공
    }
}
