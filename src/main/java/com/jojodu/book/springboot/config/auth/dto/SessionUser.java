package com.jojodu.book.springboot.config.auth.dto;

import com.jojodu.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 세션에 사용자정보 담기위한 클래스
 */
@Getter
public class SessionUser implements Serializable {
    //session에 담기위해 Serializable를 구현

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
    //SessionUser 에는 인증된 사용자정보만 필요하므로, name,email,picture만 선언합니다.

    /**
     * 세션에 User Entity클래스를 바로 저장하면 안되는 이유.
     * -> User클래스에 직렬화를 구현하지 않았으므로, Failed to Converto from type... 에러발생
     * 엔티티클래스 자체를 직렬화 한다면 여러가지 문제가 생길 수 있음.
     * @OneToMany, @ManyToMany 등 자식엔티티를 가지고있다면 자식까지 직렬화되어 ㅅ
     * 성능이슈, 부수효과가 발생 할 수 있음.
     * 그래서 직렬화 기능을 가진 세션Dto를 따로 만드는것이 운영유지보수에 좋음.
     */
}
