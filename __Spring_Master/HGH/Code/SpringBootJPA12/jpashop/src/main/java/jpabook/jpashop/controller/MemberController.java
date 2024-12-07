package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {


    private final MemberService memberService;

    @GetMapping("/members/new")
    public String joinMember(Model model){

        model.addAttribute("memberForm", new MemberForm());

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String joinMember2(@Valid MemberForm memberForm, BindingResult result){

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode()));

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model){

        List<Member> members = memberService.findMembers();

        model.addAttribute("members", members);

        return "members/memberList";
    }

}
