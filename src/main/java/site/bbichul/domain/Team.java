package site.bbichul.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.bbichul.dto.TeamRequestDto;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Team {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String teamname;

//    @Column(nullable = false)
//    @Enumerated(value = EnumType.STRING)
//    private UserRole role;

    public Team(String teamname) {
        this.teamname = teamname;
    }

}
