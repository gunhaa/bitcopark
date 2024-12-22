package bitcopark.library.service;

import bitcopark.library.domain.board.Board;
import bitcopark.library.domain.board.Reply;
import bitcopark.library.domain.member.Member;
import bitcopark.library.dto.AddReplyRequest;
import bitcopark.library.dto.UpdateReplyRequest;
import bitcopark.library.repository.BoardRepository;
import bitcopark.library.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    public Reply save(AddReplyRequest request, Member member) {
        Board board = boardRepository.findByIdAndDeletedAtNull(request.getBoardNo())
                .orElseThrow(() -> new IllegalArgumentException("not found: " + request.getBoardNo()));

        Reply reply = replyRepository.findById(request.getParentNo())
                .orElse(null);

        return replyRepository.save(request.toEntity(board, member, reply));
    }

    public List<Reply> findAllByBoard(Board board) {
        return replyRepository.findAllByBoard(board);
    }

    @Transactional
    public Reply update(Long id, UpdateReplyRequest request, Member member) {
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeReplyAuthor(reply, member);
        reply.update(request.getContent());

        return reply;
    }

    private void authorizeReplyAuthor(Reply reply, Member member) {
        if (reply.getMember().equals(member)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

    @Transactional
    public void delete(Long id, Member member) {
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeReplyAuthor(reply, member);
        reply.delete();
    }
}
