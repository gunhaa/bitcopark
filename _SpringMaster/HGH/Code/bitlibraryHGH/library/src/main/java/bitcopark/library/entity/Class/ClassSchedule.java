package bitcopark.library.entity.Class;

import bitcopark.library.entity.Board.Board;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassSchedule {

    @Id
    @GeneratedValue
    @Column(name = "classSchedule_id")
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime recruitmentStartDate;

    private LocalDateTime recruitmentEndDate;

    private Long maxParticipants;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "classSchedule")
    private Board board;


}
