package bitcopark.library.entity.Class;
import bitcopark.library.entity.Audit.BaseAuditEntity;
import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassApplicant extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "classApplicant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ApprovalType approvalType = ApprovalType.N;


}
