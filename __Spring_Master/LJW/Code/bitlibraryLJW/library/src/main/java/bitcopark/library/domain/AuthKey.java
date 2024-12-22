package bitcopark.library.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthKey extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "auth_key_id")
    private Long id;
    private String code;
    private String email;
}
