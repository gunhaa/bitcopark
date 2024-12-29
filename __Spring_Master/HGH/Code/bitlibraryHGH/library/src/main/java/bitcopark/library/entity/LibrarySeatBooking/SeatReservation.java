package bitcopark.library.entity.LibrarySeatBooking;

import bitcopark.library.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SeatReservation {

    @Id
    @GeneratedValue
    @Column(name = "seatReservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int seatNo;

    @Builder.Default
    private LocalDateTime reservationStart = LocalDateTime.now();

    private LocalDateTime reservationEnd;

}
