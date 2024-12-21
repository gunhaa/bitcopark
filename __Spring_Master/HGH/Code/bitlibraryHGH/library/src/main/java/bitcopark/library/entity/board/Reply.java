package bitcopark.library.entity.board;


import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.util.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private ReplyDelFlag flag;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    public static Reply createReply(String content, Member member, Board board){
        Reply reply = new Reply();
        reply.content = content;
        reply.member = member;
        reply.board = board;
        reply.flag = ReplyDelFlag.N;
        return reply;
    }

}
