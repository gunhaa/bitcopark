package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Hello;
import study.querydsl.entity.QHello;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {

		Hello hello = new Hello();
		em.persist(hello);
		// QueryDSL 을 사용하는 방법
		JPAQueryFactory query = new JPAQueryFactory(em);
//		QHello qHello = new QHello("h");
		// 자기 자신을 new로 만들어논 상태이기 때문에 프로퍼티를 통해 부를 수 있다.
		QHello qHello = QHello.hello;
		Hello result = query
				.selectFrom(qHello)
				.fetchOne();
		Assertions.assertThat(result).isEqualTo(hello);
		Assertions.assertThat(result.getId()).isEqualTo(hello.getId());

	}

}
