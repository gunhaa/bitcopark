package bitcopark.library.entity.board;


import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.util.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReplyDelFlag replyDelFlag = ReplyDelFlag.N;;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

}
