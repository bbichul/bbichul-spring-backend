package site.bbichul.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles("test")
class UserTest {

    @Nested
    @DisplayName("유저 객체 생성")
    class CreateUser {
        private String username;
        private String password;
        private UserRole role;
        private UserInfo userInfo;


        @BeforeEach
        void setup() {
            username = "김성훈";
            password = "123456";
            role = UserRole.USER;
            userInfo = null;
        }

        @Test
        @DisplayName("정상 케이스")
        void create_Normal() {

            // given

            // when
            User user = new User(username, password, role, userInfo);
            // then
            assertThat(user.getId()).isNull();
            assertThat(user.getUsername()).isEqualTo("김성훈");
            assertThat(user.getPassword()).isEqualTo("123456");
            assertThat(user.getRole()).isEqualTo(role);
            assertThat(user.getUserInfo()).isEqualTo(null);
        }

        @Nested
        @DisplayName("실패 케이스")
        class FailCases {

            @Nested
            @DisplayName("유저네임 유효성 검사")
            class Username {

                @Test
                @DisplayName("null")
                void invalid_username() {
                    // given
                    username = null;
                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class,
                            () -> new User(username, password, role, userInfo));

                    // then
                    assertThat(exception.getMessage()).isEqualTo("입력된 닉네임이 없습니다");
                }

                @Test
                @DisplayName("공백")
                void gap_username() {
                    // given
                    username = "";
                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class,
                            () -> new User(username, password, role, userInfo));

                    // then
                    assertThat(exception.getMessage()).isEqualTo("입력된 닉네임이 없습니다");
                }

            }

            @Nested
            @DisplayName("비밀번호 유효성검사")
            class Password {

                @Test
                @DisplayName("null")
                void invalid_password() {
                    // given
                    password = null;
                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class,
                            () -> new User(username, password, role, userInfo));

                    // then

                    assertThat(exception.getMessage()).isEqualTo("입력된 비밀번호가 없습니다");

                }

                @Test
                @DisplayName("공백")
                void gap_password() {
                    // given
                    password = "";
                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class,
                            () -> new User(username, password, role, userInfo));

                    // then

                    assertThat(exception.getMessage()).isEqualTo("입력된 비밀번호가 없습니다");

                }
                @Test
                @DisplayName("길이")
                void length_password() {
                    // given
                    password = "w5q5d";
                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class,
                            () -> new User(username, password, role, userInfo));

                    // then

                    assertThat(exception.getMessage()).isEqualTo("비밀번호를 6자 이상 입력해주세요");

                }

            }
        }
    }
}


