package bitcopark.library.service.Board;

import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.Board.BoardImg;
import bitcopark.library.repository.Board.BoardImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardImgService {

    private final BoardImgRepository boardImgRepository;

    @Transactional
    public BoardImg addBoardImg(Board board, String originalImg, int orderImg){
        BoardImg boardImg = BoardImg.builder()
                .originalImg(originalImg)
                .orderImg(orderImg)
                .board(board)
                .build();

        boardImgRepository.save(boardImg);
        return boardImg;
    }

    @Transactional
    public void deleteBoardImg(BoardImg boardImg){
        boardImgRepository.delete(boardImg);
    }

}
