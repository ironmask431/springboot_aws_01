package com.jojodu.book.springboot.domain.user;

import com.jojodu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 사용자 정보 담당 엔티티
 */
@NoArgsConstructor
@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    //JPA로 데이터베이스로 저장할때 Enum값을 어떤형태로 저장할지를 결정합니다.
    //기본적으로는 int로 된 숫자가 저장되자만, 숫자로저장되면 db에서 확인 시 무슨의미인지 알 수없음.
    //그래서 문자열(EnumType.STRING)로 저장될 수 있도록 선언
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
