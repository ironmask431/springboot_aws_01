package com.jojodu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
/*
BaseTimeEntity 클래스는 모든 Entity의 상위클래스가 되어 Entity들의
CreatedDate, LastModifiedDate를 자동으로 관리해준다.

@MappedSuperclass : jpa Entity 클래스들이 BaseTimeEntity를 상속할 경우
필드들 (createDate, modifiedDate)도 컬럼으로 인식하게 합니다.

@EntityListeners(AuditingEntityListener.class) :
BaseTimeEntity 에 Auditing 기능 부여 (Entity 생성,수정 시간저장기능)

@CreatedDate : Entity 가 생성되어 저장될때 시간이 자동저장됨.
@LastModifiedDate : 조회한 Entity의 값을 변경할 때 시간이 자동저장됨.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
