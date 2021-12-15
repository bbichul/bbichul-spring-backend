package site.bbichul.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.bbichul.dto.UserDto;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class User extends TimeStamped {

    public User(String username, String password, UserRole role, UserInfo userInfo) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.userInfo = userInfo;
    }

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // 유저이름
    @Column(nullable = false, length = 200)
    private String username;

    // 패스워드
    @Column(nullable = false, length = 200)
    private String password;

    // 공부중 유무
    @Column(nullable = true)
    private boolean studying;

    // 팀 아이디(외래키)
    @ManyToOne
    @JoinColumn(name = "teamId",nullable = true )
    private Team team;

    // 팀장 팀원 역할
    @Column(nullable = true, length = 50)
    private String position;

    // 유저 , 어드민
    @Column(nullable = true, length = 50)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    // 개인 정보 아이디
    @OneToOne
    @JoinColumn(name = "userInfoId",nullable = true)
    private UserInfo userInfo;

    // 회원상태
    @Column(nullable = true)
    private boolean status=true;

    @OneToMany(mappedBy = "user")
    List<Time> times = new ArrayList<>();

    public User(UserDto userDto) {
        this.studying = userDto.isStudying();

    }
    public void updateStatus(UserDto userDto){
        this.status = userDto.isStatus();
    }
    public void updateStudy(UserDto userDto){
        this.studying = userDto.isStudying();
    }

}
