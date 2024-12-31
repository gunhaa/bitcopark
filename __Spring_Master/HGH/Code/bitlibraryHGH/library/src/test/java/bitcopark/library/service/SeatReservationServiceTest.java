package bitcopark.library.service;

import bitcopark.library.entity.LibrarySeatBooking.SeatReservation;
import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberGender;
import bitcopark.library.repository.LibrarySeatBooking.SeatReservationRepository;
import bitcopark.library.service.LibrarySeatBooking.SeatReservationService;
import bitcopark.library.service.Member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class SeatReservationServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SeatReservationService seatReservationService;

    @Autowired
    private SeatReservationRepository seatReservationRepository;

    private Member member;

    @Test
    @Commit
    public void 자리_예약(){
        //when
        int seatNo = 11;
        seatReservationService.registerSeatReservation(member, seatNo);

        //then
        List<SeatReservation> seatReservationList = seatReservationRepository.findByMember(member);

        Assertions.assertThat(seatReservationList.size()).isEqualTo(1);

    }

    @BeforeEach
    public void 회원등록() {
        //given
        String email = "test@email.com";
        String name = "member1";
        String phoneNumber = "01012345678";
        MemberGender gender = MemberGender.MALE;
        int birth = 911111;
        String zipcode = "12345";
        String detailed = "D동";
        Address address = new Address(zipcode, detailed);
        member = memberService.joinMember(email, name, phoneNumber, gender, birth, address);
    }

}