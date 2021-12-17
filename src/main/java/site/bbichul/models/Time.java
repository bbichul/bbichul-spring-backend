package site.bbichul.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import site.bbichul.dto.TimeRequestDto;
import site.bbichul.utills.TimeValidator;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Time extends TimeStamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int day;

    @Column(nullable = false)
    private int weekDay;

    @Column(nullable = false)
    private int studyTime;


    @JsonIgnore //순환참조 제거
    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user;


    public Time(TimeRequestDto timeRequestDto, User user) {
        // 로그인 되어있는  ID 저장
        this.user = user;
        this.year = timeRequestDto.getYear();
        this.month = timeRequestDto.getMonth();
        this.day = timeRequestDto.getDay();
        this.weekDay = timeRequestDto.getWeekDay();
        this.studyTime = timeRequestDto.getStudyTime();

    }
    public Time(int year, int month, int day, int weekDay, int studyTime){
        TimeValidator.validateCreateTime(year, month, day, weekDay, studyTime);
        this.year = year;
        this.month = month;
        this.day = day;
        this.weekDay = weekDay;
        this.studyTime = studyTime;
    }





    public void updateStudyTime(int studyTime){
        this.studyTime = studyTime;
    }
}