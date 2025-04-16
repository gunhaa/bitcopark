package study.data_jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// SpringDataJpa의 기능 활용해서 auditing 해결방법
// SPringBoot Start에 어노테이션 추가시키는 것을 잊지 말아야한다.
// 사용하고싶은 테이블에 상속 시켜야 한다.
@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity extends BaseTimeEntity{

//    @CreatedDate
//    @Column(updatable = false)
//    private LocalDateTime createdDate;
//
//    @LastModifiedDate
//    private LocalDateTime lastModifiedDate;

    // 등록자 수정자
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

}
