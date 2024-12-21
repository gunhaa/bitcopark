package bitcopark.library.entity.Book;

import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.util.CreatedAuditEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookFavorite extends CreatedAuditEntity {

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
