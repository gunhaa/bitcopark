package bitcopark.library.entity.Book;

import bitcopark.library.entity.Audit.BaseAuditEntity;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.Audit.CreatedAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BookFavorite extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "bookfavorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
