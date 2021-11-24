package site.bbichul.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class User extends TimeStamped {
    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = true, length = 500)
    private Long kakaoId;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false, length = 500)
    private String username;

    @Column(nullable = false, length = 1000)
    private String password;

    @Column(nullable = true, length = 500)
    private String email;

    @Column(nullable = true, length = 500)
    private boolean status;

    @Column(nullable = true, length = 500)
    private String teamId;

    @Column(nullable = true, length = 500)
    private String position;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public User(String username, String password, String email, UserRole role, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
    }

    public User(String username, String password, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = null;
    }
}
