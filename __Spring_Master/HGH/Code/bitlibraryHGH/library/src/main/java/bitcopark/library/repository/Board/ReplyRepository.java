package bitcopark.library.repository.Board;

import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.Board.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Optional<List<Reply>> findByBoard(Board board);

}
