package com.jojodu.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
/*
 * @Target(ElementType.PARAMETER)
 * 이 어노테이션이 생성될 수 있는 위치를 지정합니다.
 * PARAMETER로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있습니다.
 * 이외에도 클래스 선언문에 쓸수있는 TYPE 등이 있습니다.
 *
 *@Retention(RetentionPolicy.RUNTIME)
 * 이 어노테이션 클래스를 어느범위까지 사용하지 설정
 * SOURCES : 컴파일시 사라짐. 사실상 주석
 * CLASS : 컴파일까지 유지, 런타임시 사라짐. 디폴트
 * RUNTIME : 런타임에도 유지. 런타임종료시 사라짐.
 *
 * @interface
 * 이 파일을 어노테이션 클래스로 지정합니다.
 */