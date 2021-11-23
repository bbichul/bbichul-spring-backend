package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.domain.CalendarMemo;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.repository.CalendarRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;


    public CalendarMemo getMemoClickedDay(String dateData) {
        try{
            return calendarRepository.findByDateData(dateData);
        }catch (NullPointerException e){
            return new CalendarMemo();
        }

    }

    @Transactional
    public void updateMemo(CalendarMemoDto calendarMemoDto) {
        try{
            CalendarMemo getMemo = calendarRepository.findByDateData(calendarMemoDto.getDateData());
            getMemo.updateMemo(calendarMemoDto);
        }catch (NullPointerException e){
            CalendarMemo calendarMemo = new CalendarMemo(calendarMemoDto);
            calendarRepository.save(calendarMemo);
        }
    }
}
