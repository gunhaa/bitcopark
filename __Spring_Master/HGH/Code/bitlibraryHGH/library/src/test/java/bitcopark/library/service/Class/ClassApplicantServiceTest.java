package bitcopark.library.service.Class;

import bitcopark.library.entity.Class.ClassApplicant;
import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.Board.Category;
import bitcopark.library.entity.Board.SecretFlag;
import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberGender;
import bitcopark.library.repository.Class.ClassApplicantRepository;
import bitcopark.library.service.Board.BoardService;
import bitcopark.library.service.Board.CategoryService;
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
class ClassApplicantServiceTest {

    @Autowired
    ClassApplicantService classApplicantService;

    @Autowired
    ClassApplicantRepository classApplicantRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Autowired
    CategoryService categoryService;

    private Member member;
    private Board board;


    @Test
    @Commit
    public void 클래스_신청자_등록(){

        //when
        classApplicantService.registerClassApplicant(board, member);


        //then
        List<ClassApplicant> classApplicantList = classApplicantRepository.findByMember(member);

        Assertions.assertThat(classApplicantList.size()).isEqualTo(1);

    }

    @BeforeEach
    public void 멤버_게시글_카테고리_생성() {
        //given

        //멤버 생성

        String email = "test@email.com";
        String name = "member1";
        String phoneNumber = "01012345678";
        MemberGender gender = MemberGender.MALE;
        int birth = 911111;
        String zipcode = "12345";
        String detailed = "D동";
        Address address = new Address(zipcode, detailed);
        member = memberService.joinMember(email, name, phoneNumber, gender, birth, address);

        //카테고리 생성
        Category category = categoryService.createNewCategory("역사");

        //게시글 생성
        String title = "글 제목1";
        String content = "글 내용1";
        SecretFlag secretFlag = SecretFlag.N;

        board = boardService.writePost(member, title, content, secretFlag, category);

    }

}