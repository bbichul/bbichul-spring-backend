package site.bbichul.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles("test")
class TimeTest {

    @Nested
    @DisplayName("공부시간 객체생성")
    class CreatTime {
        private User user;
        private int year;
        private int month;
        private int day;
        private int weekday;
        private int studyTIme;

        @BeforeEach
        void setup() {
            year = 2021;
            month = 12;
            day = 17;
            weekday = 5;
            studyTIme = 1000;
            user = user;

        }

        @Test
        @DisplayName("정상 케이스")
        void create_Normal() {

            // given

            // when
            Time time = new Time(year, month, day, weekday, studyTIme,user);
            // then
            assertThat(time.getId()).isNull();
            assertThat(time.getYear()).isEqualTo(2021);
            assertThat(time.getMonth()).isEqualTo(12);
            assertThat(time.getDay()).isEqualTo(17);
            assertThat(time.getWeekDay()).isEqualTo(5);
            assertThat(time.getStudyTime()).isEqualTo(1000);


        }


        @Nested
        @DisplayName("실패 케이스")
        class FailCases {

            @Nested
            @DisplayName("날짜 유효성 검사")
            class Date{

                @Test
                @DisplayName("null")
                void invalid_date(){
                    // given
                    year = 0;
                    month = 0;
                    day = 0;
                    weekday = 0;
                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class,
                            () -> new Time(year, month,day,weekday,studyTIme,user));

                    // then
                    assertThat(exception.getMessage()).isEqualTo("날짜가 올바르지 않습니다");
                   }
            }


            @Nested
            @DisplayName("공부시간 유효성 검사")
            class StudyTime{

                @Test
                @DisplayName("null")
                void invalid_time(){
                    // given
                    studyTIme = 0;
                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class,
                            () -> new Time(year, month,day,weekday,studyTIme,user));

                    // then
                    assertThat(exception.getMessage()).isEqualTo("저장 될 공부시간이 없습니다");
                }
                @Test
                @DisplayName("over")
                void over_time(){
                    // given
                    studyTIme = 86401;
                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class,
                            () -> new Time(year, month,day,weekday,studyTIme,user));

                    // then
                    assertThat(exception.getMessage()).isEqualTo("저장 될 공부시간을 초과 했습니다");
                }



            }





            }


        }


    }
