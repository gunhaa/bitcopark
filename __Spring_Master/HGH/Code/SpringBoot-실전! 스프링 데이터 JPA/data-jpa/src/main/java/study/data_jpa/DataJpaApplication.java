package study.data_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	// 등록자 / 수정자
	// 실제로는 세션같은것을 사용하면 된다.
	@Bean
	public AuditorAware<String> auditorProvider(){
		// 인터페이스에서 메서드가 하나면 람다로 바꿀 수 있다
		return ()-> Optional.of(UUID.randomUUID().toString());
	}

}
