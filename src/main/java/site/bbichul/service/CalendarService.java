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
import site.bbichul.utills.CalendarServiceValidator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarService {

    private final UserRepository userRepository;
    private final UserCalendarRepository userCalendarRepository;
    private final CalendarMemoRepository calendarMemoRepository;


    @Transactional
    public List<UserCalendar> getUserInfo(User user) {

        validateUser(user);


        boolean isCalendarEmptied = userCalendarRepository.findAllByUserId(user.getId()).isEmpty();


        String calendarName = null;
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
                log.info("[USER : {}] Service Auto Create, Team {} Calendar", user.getUsername(), user.getTeam().getTeamname());
            }
        }
        Long teamId = user.getTeam() != null ? user.getTeam().getId() : -1;
        return userCalendarRepository.findAllByUserIdOrTeamId(user.getId(), teamId);
    }


    @Transactional
    public void updateMemo(CalendarMemoDto calendarMemoDto, User user) {

        validateUser(user);
        validateUserOwnCalendar(calendarMemoDto.getCalendarId(), user);
        CalendarServiceValidator.validateServiceDateData(calendarMemoDto.getDateData());

        try {
            CalendarMemo getMemo = calendarMemoRepository.findByUserCalendarIdAndDateData(calendarMemoDto.getCalendarId(), calendarMemoDto.getDateData()).orElseThrow(
                    () -> new BbichulException(BbichulErrorCode.NOT_FOUND_MEMO));
            getMemo.updateMemo(calendarMemoDto);
            log.info("Service updateMemo, CalendarId : {}, date : {}", calendarMemoDto.getCalendarId(), calendarMemoDto.getDateData());
        } catch (BbichulException e) {
            CalendarMemo calendarMemo = new CalendarMemo(calendarMemoDto, userCalendarRepository.getById(calendarMemoDto.getCalendarId()));
            calendarMemoRepository.save(calendarMemo);
            log.info("Service saveMemo, CalendarId : {}, date : {}", calendarMemoDto.getCalendarId(), calendarMemoDto.getDateData());
        }
    }


    public CalendarMemoResponseDto getMemoClickedDay(Long calendarId, String dateData, User user) {

        validateUser(user);
        validateUserOwnCalendar(calendarId, user);
        CalendarServiceValidator.validateServiceDateData(dateData);

        try {
            CalendarMemo calendarMemo = calendarMemoRepository.findByUserCalendarIdAndDateData(calendarId, dateData).orElseThrow(
                    () -> new BbichulException(BbichulErrorCode.NOT_FOUND_MEMO));
            return CalendarMemoResponseDto.builder()
                    .dateData(calendarMemo.getDateData())
                    .contents(calendarMemo.getContents())
                    .build();

        } catch (BbichulException e) {
            return CalendarMemoResponseDto.builder()
                    .dateData(dateData)
                    .contents("")
                    .build();
        }

    }

    public List<CalendarMemo> getTypeAllMemo(Long calendarId, User user) {

        validateUser(user);
        validateUserOwnCalendar(calendarId, user);

        List<CalendarMemo> calendarMemoList = calendarMemoRepository.findAllByUserCalendarId(calendarId);

        return calendarMemoList;

    }

    @Transactional
    public void addCalendar(CalendarDto calendarDto, User user) {

        validateUser(user);

        String calendarName = calendarDto.getCalendarName();

        UserCalendar userCalendar = null;
        if (calendarDto.getIsPrivate()) {
            userCalendar = new UserCalendar(user, calendarDto.getIsPrivate(), calendarName);
        } else {
            log.info("[USER : {}] Service create Team Calendar : {}", user.getUsername(), calendarName);
            userCalendar = new UserCalendar(user.getTeam(), calendarDto.getIsPrivate(), calendarName);
        }

        userCalendarRepository.save(userCalendar);
    }


    @Transactional
    public void deleteCalendar(Long calendarId, User user) {

        validateUser(user);
        validateUserOwnCalendar(calendarId, user);

        calendarMemoRepository.deleteAllByUserCalendarId(calendarId);

        userCalendarRepository.deleteById(calendarId);
        log.info("[User :{}] Service Delete Calendar calendarId : {}", user.getUsername(), calendarId);
    }

    @Transactional
    public void renameCalendar(CalendarDto calendarDto, User user) {

        validateUser(user);
        validateUserOwnCalendar(calendarDto.getCalendarId(), user);

        UserCalendar userCalendar = userCalendarRepository.findById(calendarDto.getCalendarId()).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_MATCHED_CALENDAR)
        );

        log.info("[User : {}] Service rename Calendar : {} -> {}", user, userCalendar.getCalendarName(), calendarDto.getCalendarName());
        userCalendar.renameCalendar(calendarDto.getCalendarName());
    }


// 유효성 검사를 위해 DB 데이터를 찾는 메소드
    private void validateUser(User user) {
        userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_USER)
        );
    }


    private void validateUserOwnCalendar(Long calendarId, User user) {
        UserCalendar userCalendar = userCalendarRepository.findById(calendarId).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_MATCHED_CALENDAR)
        );

        if (userCalendar.getUser() != null) {
            if (userCalendar.getUser().getId() != user.getId()) {
                throw new BbichulException(BbichulErrorCode.NOT_FOUND_MATCHED_CALENDAR);
            }
        }
        if (userCalendar.getTeam() != null) {
            if (userCalendar.getTeam().getId() != user.getTeam().getId()) {
                throw new BbichulException(BbichulErrorCode.NOT_FOUND_MATCHED_CALENDAR);
            }
        }
    }

//캘린더 서비스 끝 라인
}


