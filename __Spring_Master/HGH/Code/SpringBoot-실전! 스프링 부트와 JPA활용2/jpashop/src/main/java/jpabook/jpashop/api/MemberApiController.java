package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
// @Responsebody 와 @Controller를 합친 RestAPI스타일로 만드는것
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // member의 속성이 변하면 API의 스펙이 변해서 굉장한 위험이 생긴다
    // 이렇게 개발하면 위험요소가 많다..
    // 엔티티를 직접 외부에 노출하지 말아야 한다. pw도 노출 되서 문제가 심각해진다.
    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    // 엔티티가 변화되어도 안전하다.
    @GetMapping("/api/v2/members")
    public Result membersV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .toList();

        return new Result<>(collect);
    }

    // 배열로 감싸서 내보내는 방법
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

    @PostMapping("/api/v1/members")                      // javax validation이 자동으로 된다.
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
                                            // 해당 어노테이션은 json으로 온 body를 Member에 자동으로 매핑해준다.

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    // API마다 별도의 DTO를 만들어서 데이터를 받아야한다.
    // 다른 사람이 보거나 다시 봤을때, Member로 받는다면 어느 범위까지 데이터를 받는 것인지 알 수가 없어서 필요한 데이터만 받는 DTO가 필요하다.
    // 엔티티를 수정했을때 일어나는 사이드 이펙트에 최대한 영향을 받지 않도록 해야한다.
    // 결론 : 엔티티를 노출시키지 말자
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }


    // PUT은 멱등하다. 같은 요청을 보내도 계속 수정이 된다.
    // 회원 수정
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV1(@RequestBody @Valid UpdateMemberRequest request , @PathVariable("id") Long id){

        Long updateId = memberService.update(id, request.getName());
        Member findMember = memberService.findOne(updateId);

        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }

    }

    @Data
    static class CreateMemberRequest {
        private String name;

    }

    // Entity에서는 최대한 롬복을 자제하지만, DTO에선 많이 써도 될듯? 데이터가 거쳐가기만 하기 때문에.
    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }
}
