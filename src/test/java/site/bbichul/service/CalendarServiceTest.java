package site.bbichul.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import site.bbichul.domain.CalendarMemo;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.repository.CalendarRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CalendarServiceTest {

    @Autowired private CalendarService calendarService;


    @Test
    @DisplayName("메모 추가 테스트")
    void updateMemo() {
        //give

        CalendarMemoDto calendarMemoDto1 = new CalendarMemoDto();
        calendarMemoDto1.setDateData("2021Y11M5");
        calendarMemoDto1.setContents("하이하이");

        //when

        calendarService.updateMemo(calendarMemoDto1);

        //then

        String memo = calendarMemoDto1.getContents();
        CalendarMemo calendarMemo1 = calendarRepository.findByDateData("2021Y11M5").orElseThrow(
                () -> new NullPointerException("No Data")
        );

        assertThat(memo).isEqualTo(calendarMemo1);

    }





    @Test
    @DisplayName("메모 클릭시 데이터를 불러옴")
    void getMemoClickedDay() {

    }
}