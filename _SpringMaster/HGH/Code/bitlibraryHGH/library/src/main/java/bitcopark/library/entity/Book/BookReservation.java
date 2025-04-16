package bitcopark.library.entity.Book;

import bitcopark.library.entity.Audit.CreatedAuditEntity;
import bitcopark.library.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BookReservation {

    @Id
    @GeneratedValue
    @Column(name = "bookReservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder.Default
    private LocalDate reservationDate = LocalDateTime.now().toLocalDate();
}
