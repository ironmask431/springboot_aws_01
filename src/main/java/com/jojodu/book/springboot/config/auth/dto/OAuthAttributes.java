package com.jojodu.book.springboot.config.auth.dto;

import com.jojodu.book.springboot.domain.user.Role;
import com.jojodu.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * OAuth2UserService 를 통해서 가져온 oAuth2User의 attribute를 담을 클래스
 */
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey
            ,String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this. email = email;
        this. picture = picture;
    }

    //OAuth2User 정보를 OAuthAttributes에 입력
    public static OAuthAttributes of(String registrationId, String userNameAttributeName,Map<String,Object> attributes){
        return ofGoogle(userNameAttributeName, attributes);
    }

    //OAuth2User 에서 반환하는 사용자정보는 Map이기 때문에 하나하나 변환해야합니다.
    public static OAuthAttributes ofGoogle(String userNameAttributeName,Map<String,Object> attributes){
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //User Entity를 생성합니다.
    //OAuthAttributes 에서 엔티티를 생성하는 시점(DB insert)은 처음 로그인할때입니다.
    //최초 생성할때의 기본 권한을 GUEST로 줍니다.
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }


}
