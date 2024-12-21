package bitcopark.library.entity.member;

import bitcopark.library.entity.Book.BookFavorite;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Reply;
import bitcopark.library.entity.util.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"email", "name", "phoneNumber", "gender", "birth", "address", "delFlag", "authority"})
public class Member extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;

    private MemberGender gender;

    private int birth;

    @Embedded
    private Address address;

    private MemberDelFlag delFlag;
    private MemberAuthority authority;

    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<BookFavorite> bookFavoriteList = new ArrayList<>();


    public static Member createMember(String email, String name, String phoneNumber, MemberGender gender, int birth, Address address) {
        Member member = new Member();
        member.email = email;
        member.name = name;
        member.phoneNumber = phoneNumber;
        member.gender = gender;
        member.birth = birth;
        member.address = address;
        member.delFlag = MemberDelFlag.N;
        member.authority = MemberAuthority.MEMBER;

        return member;
    }

    public static Member createAdmin(String email, String name, String phoneNumber, MemberGender gender, int birth, Address address) {
        Member member = new Member();
        member.email = email;
        member.name = name;
        member.phoneNumber = phoneNumber;
        member.gender = gender;
        member.birth = birth;
        member.address = address;
        member.delFlag = MemberDelFlag.N;
        member.authority = MemberAuthority.ADMIN;

        return member;
    }


}
