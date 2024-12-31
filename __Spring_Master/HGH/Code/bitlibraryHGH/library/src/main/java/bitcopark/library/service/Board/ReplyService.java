package bitcopark.library.service.Board;

import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.Board.Reply;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.Board.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public Reply addReply(String content, Member member, Board board){

        Reply reply = Reply.builder()
                .content(content)
                .member(member)
                .board(board)
                .build();
        replyRepository.save(reply);

        return reply;
    }

}
