package site.bbichul.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.dto.CalendarMemoResponseDto;
import site.bbichul.dto.CalenderDto;
import site.bbichul.exception.BbichulErrorCode;
import site.bbichul.exception.BbichulException;
import site.bbichul.models.CalendarMemo;
import site.bbichul.models.User;
import site.bbichul.models.UserCalendar;
import site.bbichul.repository.CalendarMemoRepository;
import site.bbichul.repository.UserCalendarRepository;
import site.bbichul.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final UserRepository userRepository;
    private final UserCalendarRepository userCalendarRepository;
    private final CalendarMemoRepository calendarMemoRepository;


    @Transactional
    public List<UserCalendar> getUserInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_USER)
        );

        boolean isCalendarEmptied = userCalendarRepository.findAllByUserId(user.getId()).isEmpty();


        if (isCalendarEmptied) {
            UserCalendar userCalendar = new UserCalendar(user, true);
            userCalendarRepository.save(userCalendar);
        }

        if (user.getTeam() != null) {
            Long getTeamCalendarId = user.getTeam().getId();
            boolean isTeamEmptied = userCalendarRepository.findAllByTeamId(getTeamCalendarId).isEmpty();

            if (isTeamEmptied) {
                UserCalendar userCalendarT = new UserCalendar(user.getTeam(), false);
                userCalendarRepository.save(userCalendarT);
            }
        }
        Long teamId = user.getTeam() != null ? user.getTeam().getId() : null;
        return userCalendarRepository.findAllByUserIdOrTeamId(user.getId(), teamId);
    }



    @Transactional
    public void updateMemo(CalendarMemoDto calendarMemoDto) {


        try{
            CalendarMemo getMemo = calendarMemoRepository.findByUserCalendarIdAndDateData(calendarMemoDto.getCalendarId(), calendarMemoDto.getDateData()).orElseThrow(
                    () -> new BbichulException(BbichulErrorCode.NOT_FOUND_MEMO));
            getMemo.updateMemo(calendarMemoDto);
        }catch (BbichulException e){
            CalendarMemo calendarMemo = new CalendarMemo(calendarMemoDto, userCalendarRepository.getById(calendarMemoDto.getCalendarId()));
            calendarMemoRepository.save(calendarMemo);
        }
    }


    public CalendarMemoResponseDto getMemoClickedDay(Long CalendarId, String dateData) {


        try{
            CalendarMemo calendarMemo = calendarMemoRepository.findByUserCalendarIdAndDateData(CalendarId, dateData).orElseThrow(
                    ()-> new BbichulException(BbichulErrorCode.NOT_FOUND_MEMO));
            return CalendarMemoResponseDto.builder()
                    .dateData(calendarMemo.getDateData())
                    .contents(calendarMemo.getContents())
                    .build();

        }catch (BbichulException e){
            return CalendarMemoResponseDto.builder()
                    .dateData(dateData)
                    .contents("")
                    .build();
        }

    }

    public List<CalendarMemo> getTypeAllMemo(Long calendarId) {

        List<CalendarMemo> calendarMemoList = calendarMemoRepository.findAllByUserCalendarId(calendarId);

        return calendarMemoList;

    }

    @Transactional
    public void addCalendar(CalenderDto calenderDto, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_USER));


        UserCalendar userCalendar = null;
        if (calenderDto.getIsPrivate()) {
            userCalendar = new UserCalendar(user, calenderDto.getIsPrivate());
        } else {
            userCalendar = new UserCalendar(user.getTeam(), calenderDto.getIsPrivate());
        }

        userCalendarRepository.save(userCalendar);
    }
}

