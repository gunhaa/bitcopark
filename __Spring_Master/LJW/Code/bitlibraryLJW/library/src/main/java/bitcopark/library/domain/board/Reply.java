package bitcopark.library.domain.board;

import bitcopark.library.domain.BaseEntity;
import bitcopark.library.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    private String content;

    private LocalDateTime deletedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Reply parent;

    @OneToMany(mappedBy = "parent")
    private List<Reply> child = new ArrayList<>();

    public static Reply createReply(String content, Board board, Member member, Reply parent) {
        Reply reply = new Reply();

        reply.content = content;
        reply.board = board;
        reply.member = member;

        if (parent != null) {
            parent.addChildReply(reply);
        }

        return reply;
    }

    public void addChildReply(Reply child) {
        this.child.add(child);
        child.parent = this;
    }

    public void update(String content) {
        this.content = content;
    }

    public void delete() {
        deletedAt = LocalDateTime.now();
    }
}
