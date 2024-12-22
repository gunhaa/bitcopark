package bitcopark.library.domain.book;

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
public class Borrow {

    @Id @GeneratedValue
    @Column(name = "borrow_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Book book;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @Column(updatable = false)
    private LocalDate startDate = LocalDate.now();

    private LocalDate returnDate;
    private LocalDate expectedReturnDate = startDate.plusWeeks(2);
}
