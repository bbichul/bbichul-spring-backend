//package site.bbichul.repository;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import site.bbichul.models.Time;
//import site.bbichul.models.User;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
//@SpringBootTest
//@Transactional
//class TimeRepositoryTest {
//
//
//    @Autowired
//    TimeRepository timeRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//
//    private User user1;
//    private User user2;
//
//    @BeforeEach
//    void setup(){
//        user1 = new User()
//        user2 = new User()
//        u
//    }
//
//
//
//    @Test
//    @DisplayName("시간 생성")
//    @Order(1)
//    public void create_time(){
//
//        // given
//        Time time = new Time(2021,12,17,5,1000);
//
//        // when
//        timeRepository.save(time);
//
//        // then
//        List<Time> users = timeRepository.findAll();
//        assertThat(users.size()).isEqualTo(1);
//    }
//
//
//
//}
//
