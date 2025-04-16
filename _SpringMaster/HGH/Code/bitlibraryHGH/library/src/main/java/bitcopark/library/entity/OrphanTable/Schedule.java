package bitcopark.library.entity.OrphanTable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    private String scheduleName;

    @Builder.Default
    private LocalDate scheduleStart = LocalDateTime.now().toLocalDate();

    private LocalDate scheduleEnd;

    private ScheduleType scheduleType;

}
