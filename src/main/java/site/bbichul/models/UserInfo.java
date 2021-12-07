package site.bbichul.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import site.bbichul.dto.GoalRequestDto;
import site.bbichul.dto.ResolutionRequestDto;

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

    @Column(nullable = true, length = 500)
    private String content;

    public UserInfo(ResolutionRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
        this.goalHour = requestDto.getGoalHour();
    }

    public void resolutionUpdate(ResolutionRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public void goalUpdate(GoalRequestDto requestDto) {
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
        this.goalHour = requestDto.getGoalHour();
    }
}
