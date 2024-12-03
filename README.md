# BookSpud Server
> 2024-2 CAU CapstoneDesign
<br/>


## 🐥 Service Info



<br/>

#### ✨ 나만의 이야기를 만들고, 친구들과 함께 롤플레잉을 해봐요! ✨

<br/>



<br/>

## 👩‍💻 Contributors

| 이수은 |
|:--:|
|<img width="325" alt="image" src="https://github.com/24-2-CapstoneDesign/Back_Spring/assets/80567210/55cd2230-cfda-46f5-892a-8e2908b3c5c1" />|
|Server Developer|
|- 프로젝트 구조 설계 <br/>- 프로젝트 배포 <br/>- 로그인 및 유저 관련 기능 구현 <br/>- 책, 롤플레잉 관련 기능 구현|

<br/>


## ⚒️ Tech Stack
- Java, Spring Boot
- MySQL, Spring Data JPA
- AWS : EC2, RDS, S3
- Swagger
- ChatGPT API
- Naver CLOVA API (CSR)

<br/>


## 🌻 Database Architecture

![image](https://github.com/user-attachments/assets/8ee41572-feeb-4d86-81d0-eb99bb066e4a)


<br/>


## 🍀 Infra Architecture

![image](https://github.com/user-attachments/assets/e30ec1f3-9a98-47f5-9ecf-edf0e11a95aa)

<br/>

## 📝 Project Architecture

BookSpud 서버는 MVC 패턴을 기반으로 개발했습니다.
<br/><br/>

![image](https://github.com/24-2-CapstoneDesign/Back_Spring/assets/80567210/c9a04da8-8cab-48ee-89a7-e72496e512fb)


### Controller
- 사용자의 요청이 진입하는 지점
- 클라이언트가 API로 요청을 보내면 서버에서 요청을 처리한 후 API를 통해 결과를 반환합니다.

### Service
- 비즈니스 로직을 수행하는 계층
- Repository에서 받아온 데이터를 가공하여 Controller에 반환합니다.

### Repository
- Entity에 의해 생성된 DB에 접근하는 메서드들을 이용하기 위한 인터페이스
- JPA interface method를 활용하여 기본적인 CRUD 연산을 수행합니다.

<br/>
