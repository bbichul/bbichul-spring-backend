package site.bbichul.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import site.bbichul.dto.UserDto;
import site.bbichul.dto.UserInfoRequestDto;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Data
@Entity

public class UserInfo {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = true)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = true)
    private Date endDate;

    @Column(nullable = true)
    private int goalHour;

    @Column(nullable = true, length = 200)
    private String content;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public UserInfo(UserInfoRequestDto requestDto, User user) {
        this.content = requestDto.getContent();
        this.startDate = requestDto.getStart_date();
        this.endDate = requestDto.getEnd_date();
        this.goalHour = requestDto.getGoal_hour();
        this.user = user;

//        this.tutor = requestDto.getTutor();
    }
}
