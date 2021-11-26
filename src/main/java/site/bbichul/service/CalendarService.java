package site.bbichul.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.exception.BbichulErrorCode;
import site.bbichul.exception.BbichulException;
import site.bbichul.models.CalendarMemo;
import site.bbichul.models.User;
import site.bbichul.repository.CalendarRepository;
import site.bbichul.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;


    public CalendarMemo getMemoClickedDay(String dateData, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_USER));

        try{
            CalendarMemo calendarMemo = calendarRepository.findByUserIdAndDateData(user.getId(), dateData).orElseThrow(
                    ()-> new BbichulException(BbichulErrorCode.NOT_FOUND_MEMO));
            return calendarMemo;
        }catch (BbichulException e){
            CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
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

        try{
            CalendarMemo getMemo = calendarRepository.findByUserIdAndDateData(user.getId(), calendarMemoDto.getDateData()).orElseThrow(
                () -> new BbichulException(BbichulErrorCode.NOT_FOUND_MEMO));
            getMemo.updateMemo(calendarMemoDto);
        }catch (BbichulException e){
            CalendarMemo calendarMemo = new CalendarMemo(calendarMemoDto, user);
            calendarRepository.save(calendarMemo);
        }
    }

}

