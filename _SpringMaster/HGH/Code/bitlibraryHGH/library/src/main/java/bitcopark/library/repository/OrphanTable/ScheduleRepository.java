package bitcopark.library.repository.OrphanTable;

import bitcopark.library.entity.OrphanTable.Schedule;
import bitcopark.library.entity.OrphanTable.ScheduleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByScheduleName(String scheduleName);

    List<Schedule> findByScheduleType(ScheduleType scheduleType);

}
