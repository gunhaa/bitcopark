package bitcopark.library.repository;

import bitcopark.library.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByIdAndDeletedAtNull(Long id);
    List<Board> findAllByDeletedAtNull();
}
