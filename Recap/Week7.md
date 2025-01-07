# Week 7 

## CI/CD 과정

1. 로컬 : Docker로 server, db 띄워서 작동되는지/테스트 통과하는지 확인하기
   - images 빌드 방법, 내부 network 설정 스크립트

### Docker MYSQL setting

```shell
# docker hub에서 최신 mysql 이미지 가져오기
docker pull mysql:latest
```

```shell
# 도커 내부 컨테이너들이 네트워크 통신을 위한 네트워크 설정
docker network create springboot-mysql-net
# 설정 확인 방법
docker network ls
```

```shell
# mysql 구동
docker run --name db-mysql -p 3306:3306 --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=helloDev -d mysql:latest
# 만약 켜져있다면(created)
docker rm db-mysql
```
- --name: 컨테이너명 설정
- -p: 포트매핑
- --network: 네트워크 설정
- -e: 환경변수 설정
  - MYSQL_ROOT_PASSWORD : SQL 비밀번호 설정
  - MYSQL_DATABASE : DATABASE 이름 설정
- -d: 백그라운드로 컨테이너 실행


```shell
# Dockerfile, Gradle을 사용한 빌드 방법
./gradlew build
# db가 도커에 있어서 빌드에 실패한다면..
./gradlew build -x test

# Docker 이미지 빌드하기
# -t spring-boot-web는 Docker 이미지에 spring-boot-web라는 태그를 지정하는 것이다.
# `.` 은 현재 디렉토리에서 Dockerfile을 찾고 빌드할 것을 의미한다.
docker build -t spring-boot-web .
```
- COPY build/libs/web-0.0.1-SNAPSHOT.jar helloDev.jar 부분에서 build/libs/web-0.0.1-SNAPSHOT.jar는 실제로 gradlew build가 실행된 후에 생성된 .jar 파일의 경로이다. 이 경로를 Dockerfile에서 지정하여 복사한다.
- 도커 파일 시스템 컨테이너 내부 `/app` 으로 복사한다
```shell
# 만든 이미지를 실행 시키기
docker run -d -p 8080:8080 --name spring-boot-container --network springboot-mysql-net spring-boot-web
```

```shell
# docker-compose.yml 추가 후 실행시키기
docker-compose up -d
```

2. AWS ec2에 젠킨스 세팅하기
    - 젠킨스 스크립트를 통해 빌드하기
    - 어떤 방식을 사용할 것인가
    - Week8 진행 예정

## Bitlibrary project
- 현재까지 진행 상황 정리 후 논의 한것
    - Category 클래스 리팩토링
        - Const, enum 혹은
        - Startegy pattern 변경
    - Thymeleaf Sidemenu 리팩토링
        - 재사용 가능한 구조로 다시 리팩토링하기
    - 범용성있는 구조로 리팩토링
    - 각자 담당부분 정하기, API 재설계
        - RESTful한 설계가 아니여서, 재설계 필요

