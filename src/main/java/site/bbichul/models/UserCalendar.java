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

    @Column(nullable = false)
    private Boolean isPrivate;


    public UserCalendar(User user, boolean isPrivate){
        this.user = user;
        this.isPrivate = isPrivate;
    }

    public UserCalendar(Team team, boolean isPrivate){
        this.team = team;
        this.isPrivate = isPrivate;
    }

}
