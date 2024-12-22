package bitcopark.library.dto;

import bitcopark.library.domain.board.Board;
import bitcopark.library.domain.board.Reply;
import bitcopark.library.domain.member.Member;
import lombok.Getter;

@Getter
public class AddReplyRequest {

    private String content;
    private Long boardNo;
    private Long parentNo;

    public Reply toEntity(Board board, Member member, Reply parent) {
        return Reply.createReply(content, board, member, parent);
    }
}
