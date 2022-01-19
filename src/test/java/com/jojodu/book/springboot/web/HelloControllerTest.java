package com.jojodu.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

//@RunWith(SpringRunner.class) = 테스트를 진행할 Junit에 내장된 실행자
@RunWith(SpringRunner.class)
//@WebMvcTest = 여러 스프링테스트 어노테이션중 web mvc에 집줄할 수 있는 어노테이션
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    //@Autowired = spring이 관리자는 bean을 주입
    @Autowired
    private MockMvc mvc; //웹api를 테스트할 때 사용, 스프림mvc테스트의 시작점. get,post등에대한 api테스트 가능

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        //MockMvc를 통해 /hello 주소로 get요청을함.
        mvc.perform(get("/hello"))
                .andExpect(status().isOk()) //mvc.perform 의 결과검증. (http헤더의 status가 200 인지 확인)
                .andExpect(content().string(hello)); //mvc.perform 의 결과검증. 응답본문의 내용이 hello가 맞는지 검증
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto").param("name",name).param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name)))
                .andExpect(jsonPath("$.amount",is(amount)));
        //mvc.perform(get("/hello/dto").param("name",name) : api테스트시 사용될 요청파라미터 설정 (String만 허용)
        //.andExpect(jsonPath("$.name",is(name))) : api의 json응답값을 필드별로 검증
    }

}
