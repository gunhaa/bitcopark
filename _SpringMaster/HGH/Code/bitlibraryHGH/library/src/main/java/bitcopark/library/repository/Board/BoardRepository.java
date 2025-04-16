package bitcopark.library.repository.Board;

import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.Board.BoardDelFlag;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<List<Board>> findByMember(Member member);

    Optional<List<Board>> findByMemberAndBoardDelFlag(Member member, BoardDelFlag boardDelFlag);

    Optional<Board> findByMemberAndId(Member member, Long boardId);

}
