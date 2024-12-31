package bitcopark.library.service.Board;

import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.Board.BoardDelFlag;
import bitcopark.library.entity.Board.Category;
import bitcopark.library.entity.Board.SecretFlag;
import bitcopark.library.entity.member.Member;
import bitcopark.library.exception.BoardNotFoundException;
import bitcopark.library.repository.Board.BoardRepository;
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
        Board board = Board.builder()
                .member(member)
                .title(title)
                .content(content)
                .secretFlag(secretFlag)
                .category(category)
                .build();
        boardRepository.save(board);
        return board;
    }

    // AOP같은 로직을 통한 인증 필요
    @Transactional
    public BoardDelFlag deletePost(Member member, Board board){
        Board findBoard = boardRepository.findByMemberAndId(member, board.getId())
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다."));

        return findBoard.changeBoardDelFlag();

    }

}
