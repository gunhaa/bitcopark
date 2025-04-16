package bitcopark.library.service;

import bitcopark.library.entity.OrphanTable.Schedule;
import bitcopark.library.entity.OrphanTable.ScheduleType;
import bitcopark.library.repository.OrphanTable.ScheduleRepository;
import bitcopark.library.service.OrphanTable.ScheduleService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    @Commit
    public void 스케쥴_등록(){
        //given
        String scheduleName = "스케쥴_이름";
        LocalDate scheduleEnd = LocalDate.of(2024, 12, 31);
        ScheduleType scheduleType = ScheduleType.HOLIDAY;

        //when
        scheduleService.registerSchedule(scheduleName, scheduleEnd, scheduleType);

        //then
        List<Schedule> findScheduleNameList = scheduleRepository.findByScheduleName(scheduleName);

        List<Schedule> findScheduleTypeList = scheduleRepository.findByScheduleType(scheduleType);

        Assertions.assertThat(findScheduleNameList.size()).isEqualTo(1);

        Assertions.assertThat(findScheduleTypeList.size()).isEqualTo(1);

    }

}