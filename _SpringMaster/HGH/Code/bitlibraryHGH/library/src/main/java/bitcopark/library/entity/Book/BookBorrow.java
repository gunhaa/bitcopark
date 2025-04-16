package bitcopark.library.entity.Book;

import bitcopark.library.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BookBorrow {

    @Id
    @GeneratedValue
    @Column(name = "BookBorrow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    private LocalDate borrowDate = LocalDateTime.now().toLocalDate();

    @Builder.Default
    private LocalDate returnDueDate = LocalDateTime.now().plusDays(14).toLocalDate();

    private LocalDateTime returnDate;

}
