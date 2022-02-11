package com.jojodu.book.springboot.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    /*보통 mybatis 에서 dao 라고 불리는 db layer 접근자
    * jpa에서는 Repository 라고 부르며 인터페이스로 생성함.
    * 생성 후 JpaRepository<Entity클래스, pk타입> 을 상속하면
    * 기본적인 crud 메소드가 자동으로 생성됨.
    * Entity클래스 와 Repository 는 같은곳에 위치해야함. 밀접한 관계
    * */

    //findAll()은 jpa기본제공이지만, SpringDataJpa 에서 제공하지 않는 메소드는
    //아래처럼 @Query로 실제쿼리로 작성하는예시를 보여줌.
    //구동시 Validation failed for query for method public abstract java.util.List .. 에러 > nativeQuery = true 를 추가해줘야함.
    @Query(value = "SELECT * FROM Posts p ORDER BY p.ID DESC", nativeQuery = true)
    List<Posts> findAllDesc();
}
