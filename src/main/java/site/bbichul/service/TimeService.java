package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.dto.TimeRequestDto;
import site.bbichul.models.Time;
import site.bbichul.repository.TimeRepository;

@RequiredArgsConstructor
@Service
public class TimeService {

    private final TimeRepository timeRepository;


    public Time createTime(TimeRequestDto timeRequestDto, Long userId) {
        Time time = new Time(timeRequestDto, userId);
        time.setYear(timeRequestDto.getYear());
        time.setMonth(timeRequestDto.getMonth());
        time.setDay(timeRequestDto.getDay());
        time.setWeekday(timeRequestDto.getWeekday());
        time.setStudy_time(timeRequestDto.getStudy_time());

        timeRepository.save(time);
        return time;
    }
}
