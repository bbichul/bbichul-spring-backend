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
        time.setWeekday(timeRequestDto.getWeekday());

        // timeRepository 에 찾은 id, year, month, day 가져와서 확인하기
        Time time1 = timeRepository.findByUserIdAndYearAndMonthAndDay(user.getId(), timeRequestDto.getYear(), timeRequestDto.getMonth(), timeRequestDto.getDay()).orElse( null
        );
        // userRepository 에 유저 아이디 가져오기
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new NullPointerException("그럴리가 없쥬")
        );
        findUser.setStudying(timeRequestDto.isIsstudying());

        if (time1 == null) {
            time.setStudy_time(timeRequestDto.getStudy_time());
            timeRepository.save(time);
        } else {
            time1.updateStudyTime(timeRequestDto.getStudy_time() + time1.getStudy_time());
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
        time.setWeekday(timeRequestDto.getWeekday());

        Time time3 = new Time(timeRequestDto, user);
        time3.setYear(timeRequestDto.getYear());
        time3.setMonth(timeRequestDto.getMonth());
        time3.setDay(timeRequestDto.getDay()-1);
        time3.setWeekday(timeRequestDto.getWeekday());

        // timeRepository 에 찾은 id, year, month, day 가져와서 확인하기
        Time time2 =  timeRepository.findByUserIdAndYearAndMonthAndDay(user.getId(), timeRequestDto.getYear(), timeRequestDto.getMonth(), timeRequestDto.getDay()-1).orElse( null
        );

        int ytime = timeRequestDto.getYesterday_time();
        // userRepository 에 유저 아이디 가져오기
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new NullPointerException("그럴리가 없쥬")
        );
        findUser.setStudying(timeRequestDto.isIsstudying());

        if(ytime != 0){

            time.setStudy_time(timeRequestDto.getStudy_time() - ytime);
            timeRepository.save(time);

            if (time2 != null ){

                time2.updateStudyTime(timeRequestDto.getStudy_time()+ ytime);

            }  else {

                time3.setStudy_time(ytime);
                timeRepository.save(time3);
            }
        }

        return time;
    }
}