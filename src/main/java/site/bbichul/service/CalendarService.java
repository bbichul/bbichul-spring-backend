package site.bbichul.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.dto.CalendarMemoResponseDto;
import site.bbichul.dto.CalendarDto;
import site.bbichul.exception.BbichulErrorCode;
import site.bbichul.exception.BbichulException;
import site.bbichul.models.CalendarMemo;
import site.bbichul.models.User;
import site.bbichul.models.UserCalendar;
import site.bbichul.repository.CalendarMemoRepository;
import site.bbichul.repository.UserCalendarRepository;
import site.bbichul.repository.UserRepository;
import site.bbichul.utills.CalendarMemoValidator;

import java.util.List;

@Slf4j
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


        String calendarName= null;
        if (isCalendarEmptied) {
            calendarName = user.getUsername() + "의 캘린더 1";
            UserCalendar userCalendar = new UserCalendar(user, true, calendarName);
            userCalendarRepository.save(userCalendar);
        }

        if (user.getTeam() != null) {
            Long getTeamCalendarId = user.getTeam().getId();
            boolean isTeamEmptied = userCalendarRepository.findAllByTeamId(getTeamCalendarId).isEmpty();

            if (isTeamEmptied) {
                calendarName = "팀 " + user.getTeam().getTeamname() + "의 캘린더 1";
                UserCalendar userCalendarT = new UserCalendar(user.getTeam(), false, calendarName);
                userCalendarRepository.save(userCalendarT);
            }
        }
        Long teamId = user.getTeam() != null ? user.getTeam().getId() : -1;
        return userCalendarRepository.findAllByUserIdOrTeamId(user.getId(), teamId);
    }



    @Transactional
    public void updateMemo(CalendarMemoDto calendarMemoDto) {

        CalendarMemoValidator.validateServiceDateData(calendarMemoDto.getDateData());

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

        CalendarMemoValidator.validateServiceDateData(dateData);

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
    public void addCalendar(CalendarDto calendarDto, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_USER));

        String calendarName = calendarDto.getCalendarName();

        UserCalendar userCalendar = null;
        if (calendarDto.getIsPrivate()) {
            userCalendar = new UserCalendar(user, calendarDto.getIsPrivate(), calendarName);
        } else {
            userCalendar = new UserCalendar(user.getTeam(), calendarDto.getIsPrivate(), calendarName);
        }

        userCalendarRepository.save(userCalendar);
    }


    @Transactional
    public void deleteCalendar(Long calendarId) {
        calendarMemoRepository.deleteAllByUserCalendarId(calendarId);

        userCalendarRepository.deleteById(calendarId);
    }

    @Transactional
    public void renameCalendar(CalendarDto calendarDto) {

        UserCalendar userCalendar = userCalendarRepository.findById(calendarDto.getCalendarId()).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_MATCHED_CALENDAR)
        );

        userCalendar.renameCalendar(calendarDto.getCalendarName());
    }
}

