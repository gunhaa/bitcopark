package jpabook.jpashop.Controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        // 필요 없지않나?
        // 필요하다, 이를 이용하여 name이랑 id 값을 넣는다
        // Validation을 위해서 사용, 폼 객체를 만들어내서 @valid 어노테이션을 이용하는게 좋다
        // 일반적인 객체로 쓴다고하면, 너무 어노테이션이 많아서 관리하기 어려워진다.
        // 그래서 Form을 제출하기위한 memberForm class를 새롭게 만드는 것이다.

        model.addAttribute("memberForm" , new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }

}
