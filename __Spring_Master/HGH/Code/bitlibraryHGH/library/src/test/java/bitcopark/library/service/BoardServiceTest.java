package bitcopark.library.service;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Category;
import bitcopark.library.entity.board.SecretFlag;
import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberGender;
import bitcopark.library.repository.BoardRepository;
import bitcopark.library.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;


    @Test
    public void 게시글_생성(){
        Member member = createTestMember();

        String title = "글 제목1";
        String content = "글 내용1";
        SecretFlag secretFlag = SecretFlag.N;
        Category category = categoryService.createCategoryObject("역사");

        Board post = boardService.writePost(member, title, content, secretFlag, category);

        List<Board> memberPosts = boardRepository.findByMember(member);

        assertThat(post).isEqualTo(memberPosts.get(0));
    }

    private Member createTestMember() {
        String email = "test@email.com";
        String name = "member1";
        String phoneNumber = "01012345678";
        MemberGender gender = MemberGender.MALE;
        int birth = 911111;
        String zipcode = "12345";
        String detailed = "D동";
        Address address = new Address(zipcode, detailed);
        return memberService.joinMember(email, name, phoneNumber, gender, birth, address);
    }

    @BeforeEach
    public void 카테고리_생성(){
        categoryService.createNewCategory("역사");
    }

}