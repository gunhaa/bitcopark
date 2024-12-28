package bitcopark.library.repository;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.BoardImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BoardImgRepository extends JpaRepository<BoardImg , Long> {

    Optional<List<BoardImg>> findByBoard(Board board);

}
