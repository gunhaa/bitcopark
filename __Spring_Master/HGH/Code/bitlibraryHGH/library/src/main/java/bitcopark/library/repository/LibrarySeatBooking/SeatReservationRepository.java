package bitcopark.library.repository.LibrarySeatBooking;

import bitcopark.library.entity.LibrarySeatBooking.SeatReservation;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatReservationRepository extends JpaRepository<SeatReservation , Long> {

    List<SeatReservation> findByMember(Member member);

}
