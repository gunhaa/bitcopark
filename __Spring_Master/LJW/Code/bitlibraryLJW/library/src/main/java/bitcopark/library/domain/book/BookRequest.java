package bitcopark.library.domain.book;

import bitcopark.library.domain.BaseTimeEntity;
import bitcopark.library.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookRequest extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "book_request_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String author;
    private String publisher;
    private LocalDate publishDate;
    private String opinion;
}
