# springboot_aws_01

책 : 스프링부트와 AWS로 혼자 구현하는 웹 서비스

Chapter 01. 인텔리제이로 스프링부트 시작하기
-------------------------------------------------------------------------------------------
2022.01.15

### 1.1 인텔리제이 소개

1.인텔리제이의 장점

* 강력한 추천기능
* 훨씬 더 다양한 리팩토링과 디버깅기능
* 이클립스의 git 에 비해 훨씬 높은 자유도
* 프로젝트 시작할 때 인덱싱을 하여 파일을 비롯한 자원들에 대한 빠른 검색속도


### 1.2 인텔리제이 설치하기

1.젯브레인 툴박스 다운로드 / 설치  / 인텔리제이 커뮤니티(무료) 설치


### 1.3 인텔리제이 커뮤니티에서 프로젝트 생성하기

1.gradle + java 프로젝트 생성


### 1.4 그레이들 프로젝트를 스프링부트 프로젝트로 변경하기

1.build.gradle 파일 수정 > gradle 프로젝트를 spring boot 프로젝트로 변환

2.build.gradle 수정 후 저장 시 아래와 같은 에러 발생

    A problem occurred evaluating root project 'test_01'.
    Could not find method compile() for arguments [org.springframework.boot:spring-boot-starter-web]

원인분석 구글링 :

gradle 버전 7 이상버전에서는 compile()과 testCompile() 대신
implementation()과 testImplementation() 을 사용해야 한다고함.

gradle 버전확인 : gradle > wrapper > gradle-wrapper.properties 에서 확인가능

3.에러 수정 후 저장 시 라이브러리 다운로드시작 > 우측상단 gradle 클릭 시

spring-boot-starter-web / spring-boot-starter-test 가 세팅된것을 확인 할 수 있다.


### 1.5 인텔리제이 에서 깃과 깃허브 사용하기

1.ctrl + shift + a > share project on github > add account > 인텔리제이 깃헙계정연동함.

share 클릭 > 오류 > Cannot Run Git > git is not installed. > download and install git 실행

(git for window 가 설치된다.)

2.git 설치완료 후 재실행

commit 할 경로 설정 > .idea 폴더는 생략함. (인텔리제이프로젝트 생성 시 자동으로 생성되는 파일들이라서)

    can't finish github sharing process 

에러 발생 (userName 과 Email 을 등록하라는 내용)

cmd 실행
    
    git --version (git 설치된 버전확인)
    
    git config --global user.name "ironmask431"
    
    git config --global user.eamil "ironmask431@gmail.com"  (git 설치된 버전확인)
    
    git config --list  (등록된 username과 useremail 을 확인할 수 있다.)

등록 후 다시 share project on github 실행해봄. > Successfully shared project on GitHub 메세지

깃헙에서 확인해보면 정상적으로 소스가 올라간 것을 확인할 수 있다.

3.ignore 플러그인 설치

ctrl + shift + a > plugin > 마켓플레이스에서 .ignore 검색하여 설치 > 인텔리제이 재실행

프로젝트명 우클릭 > new > .ignore File > generate 로 파일생성

.gitignore 파일에 커밋하지 않을 경로 추가

    .idea

커밋하자 > ctrl +k > 커밋완료

깃허브에 푸시하자 > ctrl + shift + k > 푸시완료

깃허브에서 확인하면 .gitignore 파일이 정상적으로 push 된것을 확인할 수있다.

파일명 수정 단축키 : shift + F6

Chapter 02. 스프링 부트에서 테스트 코드를 작성하자
-------------------------------------------------------------------------------------------
2022.01.16

### 2.1 테스트코드 소개

TDD : 테스트가 주도하는 개발
RED - 항상 실패하는 테스트 작성
GREEN - 테스트가 통과하는 프로덕션 코드를 작성
REFECTOR - 테스트가 통과하면 프로덕션 코드를 리팩토링

단위테스트는 TDD 의 첫번째 단계인 기능단위의 테스트코드를 작성하는것을 말함.

단위테스트의 장점 (위키디피아)

* 단위테스트는 개발초기에 문제를 발견하도록 도와줌 
* 단위테스트는 개발자가 나중에 코드를 리팩토링하거나 라이브러리 업그레이드 등에서 기존기능이 올바르게 작동하는지 확인할수있다. 
* 단위테스트는 기능에 대한 불확실성을 감소시킬수있다.
* 단위테스트는 시스템에대한 실제 문서를 제공한다. 단위테스트 자체가 문서로 사용가능하다.

1.첫번째장점. 빠른 피드백

단위테스트가 없을때 개발방법

    1.코드작성
    
    2.tomcat 구동
    
    3.postman 같은 API도구로 HTTP요청
    
    4.요청결과를 print로 확인
    
    5.결과가 다르면 tomcat을 중지하고 코드수정

기존방식은 매번 코드를 수정할때마다 tomcat을 재구동해야함. 구동에만 수시간을 소비할수있음.

단위테스트는 매번 tocmat을 재구동할필요없다.

2.두번째장점. 자동검증 가능. 기존방식은 print 된 요청결과를 눈으로 직접 확인 해야하지만 단위테스트는 수동검증이 필요없게됨.

3.세번째장점. 개발자가 만든기능을 안전하게 보호

b라는 기능을 추가햇더니 기존 a기능에 문제가 생김. 매번 모든 서비스를 테스트 할 순없음.

새로운기능이 추가될때 기존 기능이 잘작동되는것을 보장해준다.

단위테스트는 100%익혀야할 기술이자 습관이다. 

테스트코드 작성을 도와주는 프레임워크들

    java - JUNIT


### 2.2 Hello Controller 테스트코드 작성하기

2022.01.17

* Application.java 작성 (springBoot 내장was 구동 컨트롤러)

* HelloController.java 생성 (간단한 get ('/hello') 요청을 받는 컨트롤러)

* HelloControllerTest.java 생성 (HelloController의 '/hello' get요청 테스트 클래스)

* 테스트 컨트롤러 생성 후 테스트 메소드 실행 > "Tests passed : 1" 테스트통과메세지 확인

* Application.java 톰캣 구동 후 localhost:8080/hello 실제 테스트시

* hello 정상 노출 확인함.


### 2.3 롬복 소개 및 설칙하기




