package bitcopark.library.entity.Audit;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// Listener 설정
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseAuditEntity {

    @CreatedDate
    @Column(updatable = false, name = "created_date")
    // 기본 전략이 있으면, 추가적으로 명확하게 적을 것인지에 대해서
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "lastmodified_date")
    private LocalDateTime lastModifiedDate;
}
