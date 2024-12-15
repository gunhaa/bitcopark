package study.data_jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;

// 해당 방법은 Spring의 기능을 이용한 방법임
@Getter
// 진짜 상속은 아니고, 데이터만 공유하는 상속을 하는 어노테이션이다.
@MappedSuperclass
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    /*
    엔티티가 EntityManager를 통해 persist() 메서드로 관리 상태가 되기 직전에 실행된다.
    보통 엔티티의 초기화 작업이나, 특정 필드에 대한 기본값 설정에 사용된다.
    */
    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.updateDate = now;
    };
    // 업데이트 전에 호출된다
    @PreUpdate
    public void preUpdate(){
        updateDate = LocalDateTime.now();
    }

}
