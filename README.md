# springboot + AWS + Oauth2 

### 프로젝트 URL :    
http://ec2-3-37-146-110.ap-northeast-2.compute.amazonaws.com:8080/   
(인스턴스 중지됨.)

ec2 서버구동 : 
/home/ec2-user/app/step1/deploy.sh 

### 이 프로젝트로 구현한 것 : 
1. SpringSecurity + OAuth2 를 사용하여 구글,네이버,카카오 소셜 로그인 기능 구현    
2. Java + Springboot + Spring Data Jpa 로 간단한 REST API + CRUD 게시판 구현
3. Github 를 이요하여 AWS EC2 서버에 애플리케이션 배포하기, AWS RDS DB 와 애플리케이션 연동

### 블로그
https://ironmask43.tistory.com/category/Toy%20Project/SpringBoot_%20Oauth_AWS?page=2

#### 이 프로젝트는 아래 기술들을 활용하여 만들었습니다.  
* Java (jdk-12.0.2) 
* SpringBoot 
* Gradle
* JPA
* lombok
* Junit
* mustache(머스테치)
* javascript, jquery, bootstrap
* SpringSecurity + OAuth2
* Git, GitHub
* AWS EC2 (Linux2)
* AWS RDS (MariaDB)

### 웹페이지 screenshot    

* 게시판 리스트 + OAuth 로그인 버튼    

![001](https://user-images.githubusercontent.com/48856906/157448115-de00eda6-5fd6-46d5-9bb9-a9ecf3c347c2.PNG)

* Oauth 로그인페이지 (구글)

![002](https://user-images.githubusercontent.com/48856906/157448178-e42a3dd5-3ba3-4286-955c-2bfbd19d1dec.PNG)

* Oauth 로그인페이지 (네이버)

![003](https://user-images.githubusercontent.com/48856906/157448185-9c135595-c8ba-45a4-b81b-6d909cb62302.PNG)

* Oauth 로그인페이지 (카카오)

![004](https://user-images.githubusercontent.com/48856906/157448196-9aee17ee-5d5c-4701-86b9-18fed135fb8c.PNG)

* Oauth 로그인 후

![로그인](https://user-images.githubusercontent.com/48856906/160970585-757dac81-5acd-4df9-809f-8b2352481f3d.PNG)

* 글 작성, 수정 페이지   

![005](https://user-images.githubusercontent.com/48856906/157448205-2a0f9c0a-e25b-42f3-9e34-1edda227988d.PNG)



