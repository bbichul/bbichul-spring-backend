package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.dto.SignupRequestDto;
import site.bbichul.dto.TimeRequestDto;
import site.bbichul.models.Time;
import site.bbichul.models.User;
import site.bbichul.repository.TimeRepository;
import site.bbichul.repository.UserRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class TimeService {

    private final TimeRepository timeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Time upsertTime(TimeRequestDto timeRequestDto, Long userId) {

        Time time = new Time(timeRequestDto, userId);
        time.setYear(timeRequestDto.getYear());
        time.setMonth(timeRequestDto.getMonth());
        time.setDay(timeRequestDto.getDay());
        time.setWeekday(timeRequestDto.getWeekday());

        // Repository에 찾은 정보 가져와서 확인하기
        Time time1 = timeRepository.findByUserIdAndYearAndMonthAndDay(userId, timeRequestDto.getYear(), timeRequestDto.getMonth(), timeRequestDto.getDay()).orElse( null

        );
        if (time1 == null) {

            time.setStudy_time(timeRequestDto.getStudy_time());
            timeRepository.save(time);

        } else {
            time1.updateStudyTime(timeRequestDto.getStudy_time() + time1.getStudy_time());
        }
        return time;
    }
    @Transactional
    public void upstudy(TimeRequestDto timeRequestDto, String username){
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("그럴리가 없쥬")
        );
    }




}
