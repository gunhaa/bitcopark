package bitcopark.library.domain.board;

import bitcopark.library.domain.BaseTimeEntity;
import bitcopark.library.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;

    private int readCount;

    private boolean isSecret;

    private LocalDateTime deletedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "board")
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public Board(String title, String content, boolean isSecret, Member member, Category category) {
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
        this.member = member;
        this.category = category;
    }

    public void update(String title, String content, boolean isSecret) {
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
    }

    public void delete() {
        deletedAt = LocalDateTime.now();
    }
}
