package site.bbichul.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import site.bbichul.models.User;
import site.bbichul.models.UserRole;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    @Test
    @DisplayName("유저 생성")
    @Order(1)
    public void create_user() {
        // given
        User user = new User("김성훈훈", "123456",  UserRole.USER,null);

        // when
        userRepository.save(user);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);

    }

}