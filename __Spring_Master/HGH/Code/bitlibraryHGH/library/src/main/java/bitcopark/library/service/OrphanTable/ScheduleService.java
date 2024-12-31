package bitcopark.library.service.OrphanTable;

import bitcopark.library.entity.OrphanTable.Schedule;
import bitcopark.library.entity.OrphanTable.ScheduleType;
import bitcopark.library.repository.OrphanTable.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Schedule registerSchedule(String scheduleName, LocalDate scheduleEnd, ScheduleType scheduleType){
        Schedule schedule = Schedule.builder()
                .scheduleName(scheduleName)
                .scheduleEnd(scheduleEnd)
                .scheduleType(scheduleType)
                .build();
        return scheduleRepository.save(schedule);
    }

}
