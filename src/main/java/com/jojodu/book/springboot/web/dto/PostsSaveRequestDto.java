package com.jojodu.book.springboot.web.dto;

import com.jojodu.book.springboot.domain.post.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    /**
     * Entity 클래스와 유사한형태이지만 Dto 클래스를 따로 만들어줌.
     * 그 이유는 Entity를 Request, Response 용 Dto 클래스로 사용해선 안되기 때문.
     * Entity 클래스는 DB와 맞닿은 핵심 클래스로 Entity 클래스를 기준으로 테이블이 생성되고
     * 스키마가 변경됨. 화면 변경을 위해 Entity 클래스가 수정되면 안됨.
     * View Layer와 DB Layer를 철지하게 분리하기위함.
     * Entity 클래스와 컨트롤러에서 쓸 Dto 클래스는 꼭 분리해서 쓴다.
     */

    private String title;
    private String content;
    private String author;
    private Long userId;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, Long userId){
        this.title = title;
        this.content = content;
        this.author = author;
        this.userId = userId;
    }

    //dto로 받은 값들을 DB에 저장하기위해 Entity클래스에 데이터를 입력해야하므로
    //toEntity() 를 통해 Dto의 값들을 Entity에 입력한다.
    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .userId(userId)
                .build();
    }
}
