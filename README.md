# springboot_aws_01

<img src="https://user-images.githubusercontent.com/48856906/150992374-338afd7c-5b5c-49eb-97c1-51033a87593e.PNG"/>

"스프링부트와 AWS로 혼자 구현하는 웹 서비스" 라는 책을 공부하며 만들어본 코드입니다.    

아래는 학습과정 입니다.   

Chapter 01. 인텔리제이로 스프링부트 시작하기
-------------------------------------------------------------------------------------------
2022.01.15(토)

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

>gradle 이란 기본적으로 빌드도구이다. gradle은 maven보다 더 간결하게 작성 할 수있고 빌드속도도 더 빠르다. 


### 1.4 그레이들 프로젝트를 스프링부트 프로젝트로 변경하기

1.build.gradle 파일 수정 > gradle 프로젝트를 spring boot 프로젝트로 변환

2.build.gradle 수정 후 저장 시 아래와 같은 에러 발생

    A problem occurred evaluating root project 'test_01'   
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
2022.01.16(일)

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

>java - JUNIT

### 2.2 Hello Controller 테스트코드 작성하기

2022.01.17(월)

* Application.java 작성 (springBoot 내장was 구동 컨트롤러)

      @SpringBootApplication : 스프링부트의 자동설정, 스프링 bean 읽기와 생성 자동설정,    
      이 어노테이션이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트    
      최상단에 위치해야함.   

* HelloController.java 생성 (간단한 get ('/hello') 요청을 받는 컨트롤러)

      @RestController = 컨트롤러를 json을 반환하는 컨트롤러로 만들어줍니다.   
      예전에는 @ResponseBody를 각 메소드마다 선언했던것을 한번에 사용 할 수 있도록 해줍니다.   
      @GetMapping = http method인 get의 요청을 받을 수 있는 api를 만들어줍니다.   
      예전에는 @RequestMapping(method=RequestMethod.GET) 으로 쓰이던것.   

* HelloControllerTest.java 생성 (HelloController의 '/hello' get요청 테스트 클래스)

      @RunWith(SpringRunner.class) = 테스트를 진행할 Junit에 내장된 실행자   
      @WebMvcTest = 여러 스프링테스트 어노테이션중 web mvc에 집줄할 수 있는 어노테이션   

* 테스트 컨트롤러 생성 후 테스트 메소드 실행  > "Tests passed : 1" 테스트통과메세지 확인

* Application.java 톰캣 구동 후 localhost:8080/hello 실제 테스트시 hello 정상 노출 확인함.


### 2.3 롬복 소개 및 설치하기

2022.01.19(수)

자바개발자의 필수라이브러리 롬복   
롬복은 getter, setter 등을 어노테이션으로 자동생성해줌   

1.build.gradle 에 롬복추가

    dependencies 에 implementation('org.projectlombok:lombok')

저장 후 gradle 탭에서 rombok 추가된것 확인

2.lombok 플러그인설치 

확인해보니 이미 설치되어있음. (저번에 설치한듯)


### 2.4 HelloController 코드를 롬복으로 전환하기 

1.HelloResponseDto.java 클래스 생성 

    @Getter : 선언된 모든 필드의 get메소드를 생성해줍니다.    
    @RequiredArgsConstructor : 선언된 모든 final 필드가 포함된 생성자를 생성해줍니다.   
    final이 없는 필드는 생성자에 포함되지 않습니다.         

2.HelloResponseDtoTest 테스트 클래스 생성  / 테스트 코드 실행 

    assertThat : assertj 라는 테스트검증 라이브러리의 검증메소드   
    검증하고싶은 대상을 인자로 받음          
    isEqualTo : assertj 의 동등 비교 메소드            


    error: variable name not initialized in the default constructor 에러발생   

생성자를 못찾고 있다고함.
구글링해보니 gradle 버전 5.x 부터는 build.gradle 에 추가설정을 해줘야한다고함  
 
    build.gradle > dependencies 에 추가    
    annotationProcessor('org.projectlombok:lombok')   
    testAnnotationProcessor('org.projectlombok:lombok')

3.Test passed: 1 테스트 정상 실행 확인

4.HelloController 에 "/hello/dto" mapping 추가 / name, amount를 param으로 받아서 HelloResponseDto를 return 

    @RequestParam("name") String name : request로 받은 param 값 name을 인자로 받음.

5.HelloControllerTest 에 테스트메소드 추가

    mvc.perform(get("/hello/dto").param("name",name) : api테스트시 사용될 요청파라미터 설정 (String만 허용)   
    .andExpect(jsonPath("$.name",is(name))) : api의 json응답값을 필드별로 검증

6.Test passed: 1 테스트 정상 실행 확인

Chapter 03. 스프링 부트에서 JPA로 데이터베이스를 다뤄보자
-------------------------------------------------------------------------------------------
2022.01.20(목)

### 3.1 JPA소개

ORACLE, MYSQL 등 관계형데이터베이스 필수요소이고 웹서비스의 중심이 SQL중심이됨.    
SQL은 객체지향 프로그래밍과 맞지않는 부분이 있음.   
기존 관계형 데이터베이스방식은 객체모델링보다는 테이블모델링에 집중하게되고    
객체를 단순히 테이블형식에 맞추어 데이터 전달역할만 하는 기형적인 형태임.    
JPA(자바 표준 ORM)는 SQL에 종속적인 개발을 하지않도록 할 수 있게하여, 객체지향 프로그래밍을 추구하게 해줌.    
JPA를 사용하기 위해서는 Hibernate 같은 구현체가 필요함.    
구현체들을 좀더 쉽게 사용할 수 있는 Spring Data JPA라는 모듈을 사용함.    

* Spring Data JPA 의 장점 

>구현체 교체의 용이성   
>저장소 교체의 용이성 

새로운 구현체나 새로운 db 로 교체가 용이함. 

### 3.2 프로젝트에 Spring Data Jpa 적용하기 

1.build.gradle 에 jpa, h2 라이브러리 등록 

dependencies 에 추가 

    //springboot용 spring data jpa 추상화 라이브러리   
    implementation('org.springframework.boot:spring-boot-starter-data-jpa')   
    //인메모리 관계형 데이터베이스, 별도의 설치없이 사용가능한 db, 애플리케이션 재시작마다 초기화   
    implementation('com.h2database:h2')   

2.domain.post 패키지 생성 / posts 패키지와 Posts 클래스 생성 

    @Entity //db테이블과 링크될 class임을 표시   
    @Getter //클래스내의 모든 필드에 getter 메소드 자동생성   
    @NoArgsConstructor //기본 생성자 자동 추가(인자값 없는 생성자) 

    @Id //해당 테이블의 pk필드   
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk생성규칙 (auto increment)   
    @Column(length = 500, nullable = false) //테이블의 컬럼   
    @Builder //해당클래스의 빌더패턴 생성. 생성자 대신에 @Builder으로 값을 채워주는게 더 좋음. 

Entity 클래스에는 setter 를 만들지않는다. setter가 있는 경우    
인스턴스 값들이 언제 어디서 변하는지 코드상으로 명확히 구분이 어려워 복잡해짐     
그럼 어떻게 값을 채우는가?     
생성자를 통해 최종값을 채운 후 db에 삽입하고,     
update는 해당이벤트에 맞는 public 메소드를 호출하여 변경      
여기서는 생성자 대신에 @Builder 클래스를 통해 값을 채워준다.      
생성자보다 빌더패턴을 사용 시 어떤 필드에 어떤값을 채워야 할지 명확하게 인지 할 수 있어서 좋음.     

3.interface PostsRepository 생성

보통 mybatis 에서 dao 라고 불리는 db layer 접근자      
jpa에서는 Repository 라고 부르며 인터페이스로 생성함.      
생성 후 JpaRepository<Entity클래스, pk타입> 을 상속하면      
기본적인 crud 메소드가 자동으로 생성됨.     
Entity클래스 와 Repository 는 밀접한 관계이므로  같은곳에 위치해야함.       

### 3.3 Spring Data JPA 테스트코드 작성하기  

1.PostsRepositoryTest 생성, 테스트실행

    @SpringBootTest //SpringBootTest 를 사용할 경우 h2 데이터베이스를 자동실행해줌   
    @After //junit 에서 단위테스트가 끝날때마다 수행되는 메소드, 여러테스트가 동시   
    수행될때 h2에 데이터가 남아잇으면 다음테스트시 테스트사 실패할 수 있음.   

    postsRepository.save() : 테이블 posts에 insert/update 실행   
    id값이 있다면 update 없다면 insert 실행됨.   

테스트 정상 확인

2.테스트시 콘솔에서 실제 쿼리 확인하기 

src/main/resources/ 에 파일생성 - application.properties , 내용추가

    spring.jpa.show_sql=true   
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect   

테스트 실행시 콘솔에서 실제 create, insert sql문을 볼 수 있다. 


### 3.4 등록/수정/조회 API 만들기 

2022.01.21(금)

1.api를 만들기위해선 총 3개의 클래스가 필요함

* Request 데이터를 받을 dto
* api 요청을 받을 controller
* 트랜잭션,도메인 기능간의 순서를 보장하는 service   

2.Spring WEB 계층

* Web Layer
    * 흔히 사용하는 컨트롤러 @Controller 와 jsp등 뷰템플릿영역
* Service Layer
    * @Service 영역 Controller 와 Dao의 중간영역 @Transactional 이 사용되어야함.   
* Repository Layer
    * DB에 접근하는 영역 예전 DAO 영역 
* Dtos
    * 계층간에 데이터전달을 위한 객체 
* Domain model
    * @Entity 등을 의미 , 비즈니스 처리를 담당해야 하는곳.

기존 spring 방식은 모든 로직이 서비스클래스에서만 처리됨. (컨트롤러나 service)   
그러다보니 서비스계층을 나눠놓은것이 무의미함.    
그러나 이것을 도메인모델에서 처리하면 단순하게 처리 할수있음.   

예시)

    derivery.cancel();   
    order.cancel();   
    billing.cancel();   

각자가 본인의 취소이벤트를 하며, 서비스메소드는 이들의 순서만 정해줌. 

3.Controller, Dto, Service 를 만들어보자.

* PostsSaveRequestDto.java
* PostsService.java
* PostsApiController.java 

기존 스프링에서는 Controller 와 Service에서 @Autowired 를 통해서 bean을 주입받으나 여기선 안씀.   
대신 생성자를 통해 주입받는다. 생성자는    
@RequiredArgsConstructor 롬복 어노테이션을 통해서 자동으로 생성된다.    
(클래스에서 final 이 선언된 모든 필드를 인자값으로 하는 생성자를 생성해줌.)   

Entity 클래스와 유사한형태이지만 Dto 클래스를 따로 만들어줌.   
그 이유는 Entity를  Request, Response 용 Dto 클래스로 사용해선 안되기 때문.    
Entity 클래스는 DB와 맞닿은 핵심 클래스로 Entity 클래스를 기준으로 테이블이 생성되고   
스키마가 변경됨. 화면 변경을 위해 Entity 클래스가 수정되면 안됨.    
View Layer와 DB Layer를 철지하게 분리하기위함.    
Entity 클래스와 컨트롤러에서 쓸 Dto 클래스는 꼭 분리해서 쓴다.   




