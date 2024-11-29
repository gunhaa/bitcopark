package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {

//        AppConfig appConfig = new AppConfig();
        // AppConfig에서 memberService를 요청한다
//        MemberService memberService = appConfig.memberService();

        // 스프링을 호출한다. AppConfig의 환경설정 정보를 가지고 Spring이 Bean으로 등록시킨다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member : "+ member.getName());
        System.out.println("findMember : "+ findMember.getName());

    }

}
