package site.bbichul.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.exception.BbichulErrorCode;
import site.bbichul.exception.BbichulException;
import site.bbichul.models.CalendarMemo;
import site.bbichul.models.User;
import site.bbichul.models.UserCalendar;
import site.bbichul.repository.CalendarMemoRepository;
import site.bbichul.repository.UserCalendarRepository;
import site.bbichul.repository.UserRepository;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarMemoRepository calendarMemoRepository;
    private final UserRepository userRepository;
    private final UserCalendarRepository userCalendarRepository;


    public CalendarMemo getMemoClickedDay(String dateData,String calendarType, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_USER));

        UserCalendar userCalendar = userCalendarRepository.findByUserIdAndCalendarType(user.getId(), calendarType).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_MATCHED_CALENDAR)
        );


        try{
            CalendarMemo calendarMemo = calendarMemoRepository.findByUserCalendarIdAndDateData(userCalendar.getId(), dateData).orElseThrow(
                    ()-> new BbichulException(BbichulErrorCode.NOT_FOUND_MEMO));
            return calendarMemo;
        }catch (BbichulException e){
            CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
            calendarMemoDto.setCalendarType(calendarType);
            calendarMemoDto.setDateData(dateData);
            calendarMemoDto.setContents("");
            System.out.println(calendarMemoDto);
            return new CalendarMemo(calendarMemoDto);
        }

    }

    @Transactional
    public void updateMemo(CalendarMemoDto calendarMemoDto, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new BbichulException(BbichulErrorCode.NOT_FOUND_USER));


        UserCalendar userCalendar = userCalendarRepository.findByUserIdAndCalendarType(
                user.getId(),
                calendarMemoDto.getCalendarType()).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_MATCHED_CALENDAR)
        );


        try{
            CalendarMemo getMemo = calendarMemoRepository.findByUserCalendarIdAndDateData(userCalendar.getId(), calendarMemoDto.getDateData()).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_MEMO));
            getMemo.updateMemo(calendarMemoDto);
        }catch (BbichulException e){
            CalendarMemo calendarMemo = new CalendarMemo(calendarMemoDto, userCalendar);
            calendarMemoRepository.save(calendarMemo);
        }
    }

    @Transactional
    public List<UserCalendar> getUserInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_USER)
        );

        boolean isEmptied = userCalendarRepository.findAllByUserId(user.getId()).isEmpty();

        if (isEmptied) {
            UserCalendar userCalendar = new UserCalendar(user);
            userCalendar.setCalendarType("P1");
            userCalendar.setUserCount(1);

            userCalendarRepository.save(userCalendar);
        }

        return userCalendarRepository.findAllByUserId(user.getId());
    }

    public List<CalendarMemo> getTypeAllMemo(String calendarType, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_USER));


        UserCalendar userCalendar = userCalendarRepository.findByUserIdAndCalendarType(
                user.getId(),
                calendarType).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_MATCHED_CALENDAR)
        );

        List<CalendarMemo> calendarMemoList = calendarMemoRepository.findAllByUserCalendarId(userCalendar.getId());

        return calendarMemoList;
    }

    @Transactional
    public void addCalendar(boolean isPrivated, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_USER));


        if (isPrivated) {
            List<UserCalendar> userCalendarList = userCalendarRepository.findAllByUserId(user.getId());

            int listLength = userCalendarList.size();

            for (int i = 0; i < listLength; i++) {
                userCalendarList.get(i).setUserCount(listLength+1);
            }

            UserCalendar userCalendar = new UserCalendar(user);
            userCalendar.setUserCount(listLength+1);
            userCalendar.setCalendarType("P" + Integer.toString(listLength+1));

            userCalendarRepository.save(userCalendar);
        }
    }
}

