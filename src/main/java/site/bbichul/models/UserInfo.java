package site.bbichul.models;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import site.bbichul.dto.UserDto;
import site.bbichul.dto.UserInfoRequestDto;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
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

    public UserInfo(UserInfoRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.startDate = requestDto.getStart_date();
        this.endDate = requestDto.getEnd_date();
        this.goalHour = requestDto.getGoal_hour();
    }

    public void update(UserInfoRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.startDate = requestDto.getStart_date();
        this.endDate = requestDto.getEnd_date();
        this.goalHour = requestDto.getGoal_hour();
    }
}
