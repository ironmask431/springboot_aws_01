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

        //registrationId = 로그인 서비스 구분 코드
        //String = userNameAttributeName 은 사용자정보 조회 응답 json에서 유니크키 값.
        //구글은 "sub"로 자동
        //카카오 네이버는 application-oauth.properties 에 설정한 user-name-attribute 값
        if("naver".equals(registrationId)){
            return ofNaver(userNameAttributeName, attributes);
        }else if("kakao".equals(registrationId)){
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    //google
    public static OAuthAttributes ofGoogle(String userNameAttributeName,Map<String,Object> attributes){
        //OAuth2User 에서 반환하는 구글 사용자정보는 Map이기 때문에 하나하나 변환해야합니다.
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes) //user 정보 map
                .nameAttributeKey(userNameAttributeName) // user정보map의 pk필드 (구글 = sub)
                .build();
    }

    //naver
    public static OAuthAttributes ofNaver(String userNameAttributeName,Map<String,Object> attributes){
        //네이버의 경우 회원정보로 리턴받은 json형태가 response 내부에 유저정보가 들어있으므로 아래 코드추가함.
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");
        //네이버의 경우 userNameAttributeName 이 "response"로 되어있는데 실제 키값은 response안에 id 이므로, id라고 변경해줌.
        userNameAttributeName = "id";
        return OAuthAttributes.builder()
                .name((String)response.get("name"))
                .email((String)response.get("email"))
                .picture((String)response.get("profile_image"))
                .attributes(response) //user 정보 map
                .nameAttributeKey(userNameAttributeName) // user정보map의 pk필드 (네이버 = id)
                .build();
    }

    //kakao
    public static OAuthAttributes ofKakao(String userNameAttributeName,Map<String,Object> attributes){
        //카카오 회원정보 조회 api 리턴 json 형태를 보고 코드 작성.
        Map<String, Object> kakao_account = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>)kakao_account.get("profile");
        //profile 에 유저정보가 들어있지만, email과 id(pk)는 밖에있으므로, profile에 추가해준다.
        profile.put("email",kakao_account.get("email"));
        profile.put("id",attributes.get("id"));
        return OAuthAttributes.builder()
                .name((String)profile.get("nickname"))
                .email((String)profile.get("email"))
                .picture((String)profile.get("profile_image_url"))
                .attributes(profile) //user 정보 map
                .nameAttributeKey(userNameAttributeName) // user정보map의 pk필드 (카카오 = id)
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
