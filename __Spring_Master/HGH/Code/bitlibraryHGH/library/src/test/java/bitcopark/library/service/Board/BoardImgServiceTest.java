package bitcopark.library.service.Board;

import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.Board.BoardImg;
import bitcopark.library.entity.Board.Category;
import bitcopark.library.entity.Board.SecretFlag;
import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberGender;
import bitcopark.library.repository.Board.BoardImgRepository;
import bitcopark.library.service.Member.MemberService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class BoardImgServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BoardImgService boardImgService;

    @Autowired
    BoardImgRepository boardImgRepository;

    @Autowired
    EntityManager em;


    private Board board;
    private Member member;

    @Test
    public void 게시글_이미지_추가(){

        //when
        String originalImg = "originalName";
        int orderImg = 1;

        boardImgService.addBoardImg(board, originalImg, orderImg);

        //then

        List<BoardImg> imagesInBoard = boardImgRepository.findByBoard(board);

        Assertions.assertThat(imagesInBoard.size()).isEqualTo(1);

    }


    @Test
    public void 게시글_이미지_삭제(){

        //given
        String originalImg = "originalName";
        int orderImg = 1;

        BoardImg boardImg = boardImgService.addBoardImg(board, originalImg, orderImg);

        //when

        boardImgService.deleteBoardImg(boardImg);

        //then

        //**
//        Optional<List<BoardImg>> findBoard = boardImgRepository.findByBoard(board);
        // optional의 빈 리스트는 isEmpty()가 false가 나온다.
//        System.out.println("findBoard.isEmpty() = " + findBoard.isEmpty());


        List<BoardImg> findImgs = boardImgRepository.findByBoard(board);

        Assertions.assertThat(findImgs.size()).isEqualTo(0);

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