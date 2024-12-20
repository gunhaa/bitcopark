package bitcopark.library.repository;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByMember(Member member);

}
