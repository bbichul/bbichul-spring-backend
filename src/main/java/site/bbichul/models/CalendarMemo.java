package site.bbichul.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bbichul.dto.CalendarMemoDto;

import javax.persistence.*;
import javax.xml.soap.Text;


@NoArgsConstructor
@Entity
@Data

public class CalendarMemo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userCalendarId",nullable = true)
    private UserCalendar userCalendar;

    @ManyToOne
    @JoinColumn(name = "teamCalendarId",nullable = true)
    private TeamCalendar teamCalendar;

    @ManyToOne
    @JoinColumn(name ="userId" ,nullable = true)
    private User user;


    @Column(nullable = false, length = 200)
    private String dateData;

    @Column(nullable = true, length = 500)
    private String contents;


    public CalendarMemo(CalendarMemoDto calendarMemoDto) {
        this.dateData = calendarMemoDto.getDateData();
        this.contents = calendarMemoDto.getContents();
    }

    public CalendarMemo(CalendarMemoDto calendarMemoDto, User user){
        this.user = user;
        this.dateData = calendarMemoDto.getDateData();
        this.contents = calendarMemoDto.getContents();
    }



    public void updateMemo(CalendarMemoDto calendarMemoDto){
        this.dateData = calendarMemoDto.getDateData();
        this.contents = calendarMemoDto.getContents();
    }


}
