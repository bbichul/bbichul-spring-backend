package site.bbichul.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.bbichul.dto.TeamTaskRequestDto;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.

public class TeamTask extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = true, length = 500)
    private String task;

    @Column(nullable = true)
    private Boolean done;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    public TeamTask(TeamTaskRequestDto teamTaskRequestDto) {
        this.task = teamTaskRequestDto.getTask();
        this.done = teamTaskRequestDto.getDone();
    }

    public void taskUpdate(TeamTaskRequestDto requestDto) {
        this.task = requestDto.getTask();
    }
}
