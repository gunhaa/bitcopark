package bitcopark.library.service;

import bitcopark.library.domain.board.Board;
import bitcopark.library.domain.board.Category;
import bitcopark.library.domain.member.Member;
import bitcopark.library.dto.AddBoardRequest;
import bitcopark.library.dto.UpdateBoardRequest;
import bitcopark.library.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board save(AddBoardRequest request, Member member, Category category) {
        return boardRepository.save(request.toEntity(member, category));
    }

    public List<Board> findAll() {
        return boardRepository.findAllByDeletedAtNull();
    }

    public Board findById(Long id) {
        return boardRepository.findByIdAndDeletedAtNull(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(Long id) {
        Board board = findById(id);
        board.delete();
    }

    @Transactional
    public Board update(Long id, UpdateBoardRequest request, Member member) {
        Board board = findById(id);
        authorizeBoardAuthor(board, member);
        board.update(request.getTitle(), request.getContent(), request.isSecret());
        return board;
    }

    private void authorizeBoardAuthor(Board board, Member member) {
        if (board.getMember().equals(member)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
