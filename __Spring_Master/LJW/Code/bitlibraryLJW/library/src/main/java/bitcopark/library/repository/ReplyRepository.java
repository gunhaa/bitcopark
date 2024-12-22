package bitcopark.library.repository;

import bitcopark.library.domain.board.Board;
import bitcopark.library.domain.board.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByBoard(Board board);
}
