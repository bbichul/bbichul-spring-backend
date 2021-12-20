package site.bbichul.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import site.bbichul.dto.TimeRequestDto;
import site.bbichul.models.Time;
import site.bbichul.models.User;
import site.bbichul.repository.TimeRepository;
import site.bbichul.repository.UserRepository;
import javax.transaction.Transactional;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Transactional
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TimeServiceTest {

    @Mock
    TimeRepository timeRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    TimeService timeService;


    TimeRequestDto timeRequestDto;


    @Test
    @DisplayName("그날아무것도 안한 유저가 새로운 시간을 저장할 때")
    void newTime(){
        //Given
        User user = new User();

        user.setUsername("김성훈");
        user.setId(1L);

        Time time = null;
        timeRequestDto = new TimeRequestDto(1L,2021,12,20,1,10000,false,0);


        ArgumentCaptor<Time> captor = ArgumentCaptor.forClass(Time.class);

        given(timeRepository.findByUserIdAndYearAndMonthAndDay(1L,2021,12,20)).willReturn(Optional.ofNullable(time));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        //when
        Time result = timeService.upsertTime(timeRequestDto,user);


        //then
        verify(timeRepository,times(1)).save(captor.capture());
        Time result1 = captor.getValue();

        assertEquals(result1.getUser().getId(),1);
        assertEquals(result1.getYear(),2021);
        assertEquals(result1.getMonth(),12);
        assertEquals(result1.getDay(),20);
        assertEquals(result1.getWeekDay(),1);
        assertEquals(result1.getStudyTime(),10000);


    }
    @Test
    @DisplayName("그날 공부 시간이 있으면 더해서 저장")
    void plusTime(){
        //Given
        User user = new User();

        user.setUsername("김성훈");
        user.setId(1L);

        timeRequestDto = new TimeRequestDto(1L,2021,12,20,1,10000,false,0);

        Time time = new Time(timeRequestDto,user);

        given(timeRepository.findByUserIdAndYearAndMonthAndDay(1L,2021,12,20)).willReturn(Optional.of(time));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        //when
        Time result = timeService.upsertTime(timeRequestDto,user);


        //then
        assertEquals(result.getUser().getId(),1);
        assertEquals(result.getYear(),2021);
        assertEquals(result.getMonth(),12);
        assertEquals(result.getDay(),20);
        assertEquals(result.getWeekDay(),1);
        assertEquals(result.getStudyTime(),20000);
    }

    @Test
    @DisplayName("어제 공부시간이 있으면 오늘 공부시간에 총 공부시간 - 어제 공부시간 저장")
    void toTimeMinusYeTime(){
        //Given
        User user = new User();

        user.setUsername("김성훈");
        user.setId(1L);

        TimeRequestDto timeRequestDto1 = new TimeRequestDto(1L,2021,12,19,1,10000,false,0);
        Time time1 = new Time(timeRequestDto1,user);

        timeRequestDto = new TimeRequestDto(1L,2021,12,20,1,10000,false,5000);


        ArgumentCaptor<Time> captor = ArgumentCaptor.forClass(Time.class);

        given(timeRepository.findByUserIdAndYearAndMonthAndDay(1L,2021,12,19)).willReturn(Optional.of(time1));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        //when
        Time result = timeService.upsertStudy(timeRequestDto,user);

        //then
        verify(timeRepository,times(1)).save(captor.capture());
        Time result1 = captor.getValue();

        assertEquals(result1.getUser().getId(),1);
        assertEquals(result1.getYear(),2021);
        assertEquals(result1.getMonth(),12);
        assertEquals(result1.getDay(),20);
        assertEquals(result1.getWeekDay(),1);
        assertEquals(result1.getStudyTime(),5000);
    }

    @Test
    @DisplayName("어제 공부한 시간이 없으면 어제 공부시간을 새로 저장")
    void newYeTime(){
        //Given
        User user = new User();

        user.setUsername("김성훈");
        user.setId(1L);

        Time time = null;

        timeRequestDto = new TimeRequestDto(1L,2021,12,20,1,10000,false,5000);


        ArgumentCaptor<Time> captor = ArgumentCaptor.forClass(Time.class);

        given(timeRepository.findByUserIdAndYearAndMonthAndDay(1L,2021,12,19)).willReturn(Optional.ofNullable(time));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        //when
        Time result = timeService.upsertStudy(timeRequestDto,user);


        //then
        verify(timeRepository,times(2)).save(captor.capture());
        Time result1 = captor.getValue();

        assertEquals(result1.getUser().getId(),1);
        assertEquals(result1.getYear(),2021);
        assertEquals(result1.getMonth(),12);
        assertEquals(result1.getDay(),19);
        assertEquals(result1.getWeekDay(),1);
        assertEquals(result1.getStudyTime(),5000);

    }

    @Test
    @DisplayName("어제 공부를 한 시간이 있으면 어제 공부시간 + 넘어온 어제 공부시간 저장")
    void yeTime0PlusYeTime(){
        //Given
        User user = new User();

        user.setUsername("김성훈");
        user.setId(1L);

        TimeRequestDto timeRequestDto1 = new TimeRequestDto(1L,2021,12,19,1,10000,false,0);

        timeRequestDto = new TimeRequestDto(1L,2021,12,20,1,10000,false,5000);


        Time time1 = new Time(timeRequestDto1,user);


        given(timeRepository.findByUserIdAndYearAndMonthAndDay(1L,2021,12,19)).willReturn(Optional.of(time1));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        //when
        Time result = timeService.upsertStudy(timeRequestDto,user);


        //then
        assertEquals(result.getUser().getId(),1);
        assertEquals(result.getYear(),2021);
        assertEquals(result.getMonth(),12);
        assertEquals(result.getDay(),19);
        assertEquals(result.getWeekDay(),1);
        assertEquals(result.getStudyTime(),15000);
    }
}