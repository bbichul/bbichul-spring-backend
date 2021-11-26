package site.bbichul.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Entity
@Data

public class CalendarMemo {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_calendar_id",nullable = false)
    private UserCalendar userCalendar;

    @ManyToOne
    @JoinColumn(name = "team_calendar_id",nullable = false)
    private TeamCalendar teamCalendar;


    @Column(nullable = false, length = 200)
    private String dateData;

    @Column(nullable = true, length = 500)
    private String content;


}
