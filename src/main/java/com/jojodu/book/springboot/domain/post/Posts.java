package com.jojodu.book.springboot.domain.post;

import com.jojodu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/* Entity 클래스에는 setter 를 만들지않는다. setter가 있는 경우
 * 인스턴스 값들이 언제 어디서 변하는지 코드상으로 명확히 구분이 어려워 복잡해짐
 * 그럼 어떻게 값을 채우는가?
 * 생성자를 통해 최종값을 채운 후 db에 삽입하고,
 * update는 해당이벤트에 맞는 public 메소드를 호출하여 변경
 * 여기서는 생성자 대신에 @Builder 클래스를 통해 값을 채워준다.
 * 생성자보다 빌더패턴을 사용 시 어떤 필드에 어떤값을 채워야 할지 명확하게 인지 할 수 있어서 좋음.
 * */

@Entity //db테이블과 링크될 class임을 표시
@Getter //클래스내의 모든 필드에 getter 메소드 자동생성
@NoArgsConstructor //기본 생성자 자동 추가 (인자값이 없는 생성자)
public class Posts extends BaseTimeEntity {

    @Id //해당 테이블의 pk필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk생성규칙 (auto increment)
    private Long id;

    @Column(length = 500, nullable = false) //테이블의 컬럼
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder //해당클래스의 빌더패턴 생성. 생성자 대신에 @Builder으로 값을 채워주는게 더 좋음.
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}

