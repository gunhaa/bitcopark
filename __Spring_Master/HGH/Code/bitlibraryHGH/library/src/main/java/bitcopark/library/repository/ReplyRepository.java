package bitcopark.library.repository;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Optional<List<Reply>> findByBoard(Board board);

}
