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


    // 그날 공부 저장
    @Transactional
    public Time upsertTime(TimeRequestDto timeRequestDto, User user) {

        Time time = new Time(timeRequestDto, user);
        time.setYear(timeRequestDto.getYear());
        time.setMonth(timeRequestDto.getMonth());
        time.setDay(timeRequestDto.getDay());
        time.setWeekDay(timeRequestDto.getWeekDay());

        // timeRepository 에 찾은 id, year, month, day 가져와서 확인하기
        Time todayTime = timeRepository.findByUserIdAndYearAndMonthAndDay(user.getId(), timeRequestDto.getYear(), timeRequestDto.getMonth(), timeRequestDto.getDay()).orElse( null
        );
        // userRepository 에 유저 아이디 가져오기
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new NullPointerException("유저가 없습니다")
        );
        findUser.setStudying(timeRequestDto.isStudying());

        if (todayTime == null) {
            time.setStudyTime(timeRequestDto.getStudyTime());
            timeRepository.save(time);
        } else {
            todayTime.updateStudyTime(timeRequestDto.getStudyTime() + todayTime.getStudyTime());
        }
        return time;
    }

    // 00시 기준 전날 다음날 공부 저장
    @Transactional
    public Time upsertStudy(TimeRequestDto timeRequestDto, User user){
        Time time = new Time(timeRequestDto, user);
        time.setYear(timeRequestDto.getYear());
        time.setMonth(timeRequestDto.getMonth());
        time.setDay(timeRequestDto.getDay());
        time.setWeekDay(timeRequestDto.getWeekDay());

        Time yesterdayTime = new Time(timeRequestDto, user);
        yesterdayTime.setYear(timeRequestDto.getYear());
        yesterdayTime.setMonth(timeRequestDto.getMonth());
        yesterdayTime.setDay(timeRequestDto.getDay()-1);
        yesterdayTime.setWeekDay(timeRequestDto.getWeekDay());

        // timeRepository 에 찾은 id, year, month, day 가져와서 확인하기
        Time setYesterdayTime =  timeRepository.findByUserIdAndYearAndMonthAndDay(user.getId(), timeRequestDto.getYear(), timeRequestDto.getMonth(), timeRequestDto.getDay()-1).orElse( null
        );

        int yesterdayStudyTime = timeRequestDto.getYesterdayTime();

        // userRepository 에 유저 아이디 가져오기
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new NullPointerException("유저가 없습니다")
        );
        findUser.setStudying(timeRequestDto.isStudying());

        if(yesterdayStudyTime != 0){

            time.setStudyTime(timeRequestDto.getStudyTime() - yesterdayStudyTime);
            timeRepository.save(time);

            if (setYesterdayTime != null ){

                setYesterdayTime.updateStudyTime(timeRequestDto.getStudyTime()+ yesterdayStudyTime);

            }  else {
                yesterdayTime.setStudyTime(yesterdayStudyTime);
                timeRepository.save(yesterdayTime);
            }
        }
        return time;
    }
}