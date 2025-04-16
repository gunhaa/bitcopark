package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data_jpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
