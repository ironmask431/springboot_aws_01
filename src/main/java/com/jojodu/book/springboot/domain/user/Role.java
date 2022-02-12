package com.jojodu.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자의 권한관리 클래스(enum)
 */
@Getter
@RequiredArgsConstructor
public enum Role {

    //스프링 시큐리티에서는 권한 코드에 항상 ROLE_ 가 앞에 있어야만합니다.
    GUEST("ROLE_GUEST","손님"),
    USER("ROLE_USER","일반 사용자");

    private final String key;
    private final String title;
}
