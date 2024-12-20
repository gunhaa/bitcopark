package bitcopark.library.entity.board;

import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title;
    private String content;
    private Long count;

    @Enumerated(EnumType.STRING)
    private BoardDelFlag flag;

    @Enumerated(EnumType.STRING)
    private SecretFlag secret;

    @JoinColumn(name = "category_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Category category;

    public static Board createBoard(Member member, String title, String content, SecretFlag secretFlag,Category category){
        Board board = new Board();
        board.member = member;
        board.title = title;
        board.content = content;
        board.count = 0L;
        board.flag = BoardDelFlag.N;
        board.secret = secretFlag;
        board.category = category;

        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id) && Objects.equals(member, board.member) && Objects.equals(title, board.title) && Objects.equals(content, board.content) && Objects.equals(count, board.count) && flag == board.flag && secret == board.secret && Objects.equals(category, board.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member, title, content, count, flag, secret, category);
    }
}
