package site.bbichul.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class UserCalendar {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = true)
    private Team team;

    @Column(nullable = false)
    private Boolean isPrivate;

    @Column
    private String calendarName;


    public UserCalendar(User user, boolean isPrivate, String calendarName){
        this.user = user;
        this.isPrivate = isPrivate;
        this.calendarName = calendarName;
    }

    public UserCalendar(Team team, boolean isPrivate, String calendarName){
        this.team = team;
        this.isPrivate = isPrivate;
        this.calendarName = calendarName;
    }

    public void renameCalendar(String calendarName) {
        this.calendarName = calendarName;
    }
}
