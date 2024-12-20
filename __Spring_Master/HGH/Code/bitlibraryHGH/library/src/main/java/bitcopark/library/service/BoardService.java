package bitcopark.library.service;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Category;
import bitcopark.library.entity.board.SecretFlag;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board writePost(Member member, String title, String content, SecretFlag secretFlag, Category category){

        Board board = Board.createBoard(member, title, content, secretFlag, category);
        boardRepository.save(board);

        return board;
    }

}
