package com.jojodu.book.springboot.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {
    @Test
    public void 롬복_기능_테스트(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);

        //assertThat : assertj 라는 테스트검증 라이브러리의 검증메소드
        //검증하고싶은 대상을 인자로 받음
        //isEqualTo : assertj 의 동등 비교 메소드
    }

}
