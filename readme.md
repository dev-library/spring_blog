## 마이바티스를 활용한 블로그 생성 프로젝트

### 초기세팅

의존성 추가<br>

프로젝트 생성시

- Lombok
- MyBatis Framework
- Spring Web <br>
  이상 3개 선택

그리고 mvnrepository에서 DB에 맞는 의존성 추가

현 교안은 mysql 기준으로 작성됨.


```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.2'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	implementation 'com.mysql:mysql-connector-j:8.0.32'
}
```

---
## 설정파일 세팅하기
application.properties
```
# JSP 설정하기
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

# MySQL 설정하기
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/DB명
spring.datasource.username=계정 이름
spring.datasource.password=계정 비밀번호
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# mybatis(자동 의존성 주입을 사용하지 않는 경우)
mybatis.mapper-locations=classpath:mybatis/mapper/**/**.xml
```
application.yml
```
spring: 
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/DB명
    username: 계정 이름
    password: 계정 비밀번호
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis:
  mapper-locations: classpath:mybatis/mapper/**/**.xml
```
---
## mapper 폴더 만들기
classpath라는 옵션으로 경로 설정시 기본적으로<br>
src/main/resources<br>
폴더를 잡습니다.

그래서 해당 폴더 아래에 mybatis 폴더를 만들고 그 아래에 mapper폴더를 만듭니다.<br>

이 폴더에 쿼리문과 java 메서드를 연동해줄 xml파일을 생성할 수 있게 됩니다.


---
## mapper xml파일 초기설정하기.

resources 폴더 아래 생성된 마이바티스 쿼리문 매핑용 xml의 경우

사전 양식이 존재하기 때문에 아래 구문을 사용하면 됩니다.

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
	PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	
<mapper namespace="패키지명 처음부터 끝까지 .으로 구분해서 집어넣기.연동할자바클래스">

</mapper>
```

일반적으로 XXXrepository.java로 만든 인터페이스 파일에 매핑시키는 경우가 많습니다.


---
## 테스트 클래스

테스트시는 반드시 클래스 위에

```
@SpringBootTest
public class 테스트코드작성을위한 클래스명 {
```

형식으로 작성해주세요.