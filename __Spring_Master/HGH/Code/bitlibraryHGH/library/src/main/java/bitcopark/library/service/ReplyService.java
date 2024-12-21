package bitcopark.library.service;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Reply;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.ReplyRepository;
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

        Reply reply = Reply.createReply(content, member, board);
        replyRepository.save(reply);

        return reply;
    }

}
