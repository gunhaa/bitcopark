package study.data_jpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;
import study.data_jpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return  member.getUsername();
    }

    // 위 코드와 같은 기능을한다, Spring data JPA가 이 코드를 가능하도록 만들어준다.
    // 권장되지 않는 방법, 외부에 PK 노출하면 안된다.
    // 간단하게만 쓸 수 있다.
    // 트랜잭션에 없는 것이라 애매해져서, 정말 조회용으로만 사용해야한다.
    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member){
        return  member.getUsername();
    }

    // http://localhost:8080/members?page=0 - 20개가 출력된다 (0 ~20)
    //                               page=1 - 20개가 출력된다 (21 ~ 40)
    // size, sort 등을 파라미터로 받을 수 있다.
    @GetMapping("/members")
                            // 어노테이션을 통한 상세 설정도 가능하다.
    public Page<MemberDto> list(@PageableDefault(size=5, sort="username") Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        //page는 map을 지원한다.(DTO로 변환)
        //엔티티를 밖에 절대 노출하면 안된다.
        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        // dto의 생성자에 Member 관련되게 만들면 해당 방식으로 변경 가능
//        Page<Object> map1 = page.map(MemberDto::new);

        // page를 1부터쓰는 방법
        // 직접 생성한다.
//        PageRequest request = PageRequest.of(1, 2);

        return map;
    }

//    @PostConstruct
    public void init(){

        for(int i=0; i<100; i++){
            memberRepository.save(new Member("user" + i, i));
        }

    }
}
