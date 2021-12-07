package site.bbichul.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.bbichul.dto.CalenderDto;

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

    @Column(nullable = true)
    private int userCount;

    @Column(nullable = true)
    private int teamCount;

    @Column(nullable = false, length = 50)
    private String calendarType;

    public UserCalendar(User user){
        this.user = user;
    }

    public UserCalendar(Team team){
        this.team = team;
    }

}
