package site.bbichul.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.bbichul.dto.TeamRequestDto;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.


public class Team {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "team",cascade = CascadeType.REMOVE)
    List<UserCalendar> calendars = new ArrayList<>();

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false, length = 100)
    private String teamname;

    public Team(String teamname, Long id) {
        this.id = id;
        this.teamname = teamname;
    }

    public Team(String teamname) {
        this.teamname = teamname;
    }

    public Team(TeamRequestDto teamRequestDto) {
        this.teamname = teamRequestDto.getTeamname();
        this.id = teamRequestDto.getId();
    }
}
