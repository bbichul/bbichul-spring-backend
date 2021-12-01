package site.bbichul.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.bbichul.dto.TeamTaskRequestDto;

import javax.persistence.*;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.

public class TeamTask extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, length = 500)
    private String task;

    @Column(nullable = false)
    private Boolean done;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    public TeamTask(TeamTaskRequestDto teamTaskRequestDto) {
        this.task = teamTaskRequestDto.getTask();
        this.done = teamTaskRequestDto.getDone();
    }
}
