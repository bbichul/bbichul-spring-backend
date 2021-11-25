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
            CalendarMemo calendarMemo = calendarRepository.findByDateData(dateData).orElseThrow(
                    ()-> new NullPointerException()
            );
            return calendarMemo;
        }catch (NullPointerException e){
            CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
            calendarMemoDto.setDateData(dateData);
            calendarMemoDto.setContents("");
            System.out.println(calendarMemoDto);
            return new CalendarMemo(calendarMemoDto);
        }

    }

    @Transactional
    public void updateMemo(CalendarMemoDto calendarMemoDto) {
        try{
            CalendarMemo getMemo = calendarRepository.findByDateData(calendarMemoDto.getDateData()).orElseThrow(
                    ()-> new NullPointerException()
            );
            getMemo.updateMemo(calendarMemoDto);
        }catch (NullPointerException e){
            CalendarMemo calendarMemo = new CalendarMemo(calendarMemoDto);
            calendarRepository.save(calendarMemo);
        }
    }
}
