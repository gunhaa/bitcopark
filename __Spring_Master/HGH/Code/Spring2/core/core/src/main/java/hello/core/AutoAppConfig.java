package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core.member", // 이 패키지를 포함한 하위패키지를 탐색한다.
        basePackageClasses = AutoAppConfig.class, // 클래스가 있는 패키지를 전부 찾는다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes =
        Configuration.class))
// 컴포넌트 스캔 사용시 Configuration이 자동으로 등록되기 때문에 이전 코드의 Configuration때문에 넣은 조건
// @Configuration을 세부적으로 보면 @Component가 들어있다.

// 아무것도 작성되지 않았을때의 기본 값 : @Component이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
public class AutoAppConfig {



}
