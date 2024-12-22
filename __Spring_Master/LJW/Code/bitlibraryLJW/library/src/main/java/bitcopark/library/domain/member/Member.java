package bitcopark.library.domain.member;

import bitcopark.library.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"email", "name", "phoneNumber", "gender", "dateOfBirth", "address", "role"})
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String dateOfBirth;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.USER;

    private LocalDateTime deletedAt;

    @Builder
    public Member(String email, String password, String name,
                               String phoneNumber, Gender gender, String dateOfBirth,
                               Address address, MemberRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.role = role;
    }

    public void encodingPassword(BCryptPasswordEncoder encoder) {
        password = encoder.encode(password);
    }
}
