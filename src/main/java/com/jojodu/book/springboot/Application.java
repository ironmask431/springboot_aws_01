package com.jojodu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Application : 메인클래스
//@SpringBootApplication : 스프링부트의 자동설정, 스프링 bean 읽기와 생성 자동설정
//이 어노테이션이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트 최상단에 위치해야함.
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        //스프링부트 내장was 실행
        SpringApplication.run(Application.class, args);
    }
}

