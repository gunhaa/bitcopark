package bitcopark.library.service.Board;

import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.Board.Category;
import bitcopark.library.entity.Board.Reply;
import bitcopark.library.entity.Board.SecretFlag;
import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberGender;
import bitcopark.library.repository.Board.ReplyRepository;
import bitcopark.library.service.Member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
class ReplyServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BoardService boardService;

    @Autowired
    ReplyService replyService;

    @Autowired
    ReplyRepository replyRepository;

    private Board board;
    private Member member;

    @Test
    public void 댓글작성_게시글댓글조회_테스트(){
        //given
        String replyContent = "댓글작성";

        Reply reply = replyService.addReply(replyContent, member, board);

        //when
        List<Reply> replyInBoard = replyRepository.findByBoard(board)
                .orElseThrow(() -> new IllegalArgumentException("no reply in test"));

        //then
        Assertions.assertThat(reply).isEqualTo(replyInBoard.get(0));

    }

    @BeforeEach
    public void 멤버_게시글_카테고리_생성(){
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