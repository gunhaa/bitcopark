package bitcopark.library.service.LibrarySeatBooking;

import bitcopark.library.entity.LibrarySeatBooking.SeatReservation;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.LibrarySeatBooking.SeatReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatReservationService {

    private final SeatReservationRepository seatReservationRepository;

    @Transactional
    public SeatReservation registerSeatReservation(Member member, int seatNo){
        SeatReservation seatReservation = SeatReservation.builder()
                .member(member)
                .seatNo(seatNo)
                .build();
        return seatReservationRepository.save(seatReservation);
    }

}
