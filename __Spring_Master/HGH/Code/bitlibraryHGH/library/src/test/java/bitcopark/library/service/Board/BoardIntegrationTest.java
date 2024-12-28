package bitcopark.library.service.Board;

import bitcopark.library.entity.board.*;
import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberGender;
import bitcopark.library.repository.BoardRepository;
import bitcopark.library.service.BoardService;
import bitcopark.library.service.CategoryService;
import bitcopark.library.service.MemberService;
import bitcopark.library.service.ReplyService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BoardIntegrationTest {

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ReplyService replyService;

    @Autowired
    BoardRepository boardRepository;



    private Board board;
    private Member member;
    private Reply reply;

    @Test
    public void 게시글_삭제시_댓글_삭제_테스트(){

        //when
        BoardDelFlag boardDelFlag = boardService.deletePost(member, board);

        //then
        List<Board> boardList = boardRepository.findByMemberAndBoardDelFlag(member, boardDelFlag)
                .orElseThrow(()->new IllegalArgumentException("error in test"));

        assertThat(BoardDelFlag.Y).isEqualTo(boardDelFlag);
        assertThat(boardList.size()).isEqualTo(1);

    }
    
    @BeforeEach
    public void 멤버_게시글_카테고리_생성(){
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

        //댓글 생성
        String replyContent = "댓글작성";

        reply = replyService.addReply(replyContent, member, board);

    }

}
