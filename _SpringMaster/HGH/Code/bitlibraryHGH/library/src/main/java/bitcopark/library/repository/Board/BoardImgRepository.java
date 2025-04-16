package bitcopark.library.repository.Board;

import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.Board.BoardImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImgRepository extends JpaRepository<BoardImg , Long>{

    List<BoardImg> findByBoard(Board board);

}
