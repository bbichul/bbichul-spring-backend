package site.bbichul.api;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import site.bbichul.models.Time;
import site.bbichul.models.User;
import site.bbichul.models.UserRole;
import site.bbichul.repository.TimeRepository;
import site.bbichul.repository.UserRepository;
import site.bbichul.security.UserDetailsImpl;

import javax.transaction.Transactional;

@SpringBootTest()
@ActiveProfiles("test")
@Transactional
public class TimeControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TimeRepository timeRepository;

    private Long userId;

    @BeforeAll
    void init(){
        User user = new User("김성훈", "123456",  UserRole.USER,null,null,false,false,null);
        userRepository.save(user);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);


        int studyTime = 5000;
        boolean studying = true;

        Time time = new Time(2021,12,17,5,1000,user);
        this.timeRepository.save(time);
        userId = time.getId();
    }





}


