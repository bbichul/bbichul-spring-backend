package site.bbichul.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.bbichul.dto.CalenderDto;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class UserCalendar {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private int userCount;

    @Column(nullable = false)
    private int teamCount;

    @Column(nullable = false)
    private String calendarType;

    public UserCalendar(User user){
        this.user = user;
    }

}
