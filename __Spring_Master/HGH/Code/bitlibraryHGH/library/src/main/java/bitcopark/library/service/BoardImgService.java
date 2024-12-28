package bitcopark.library.service;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.BoardImg;
import bitcopark.library.repository.BoardImgRepository;
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

}
