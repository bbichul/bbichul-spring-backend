//package site.bbichul.api;
//
//
//import com.google.gson.GsonBuilder;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import site.bbichul.dto.SignupRequestDto;
//import site.bbichul.dto.UserDto;
//import site.bbichul.models.User;
//import site.bbichul.repository.UserRepository;
//import site.bbichul.service.UserService;
//import site.bbichul.utills.JwtTokenUtil;
//
//import javax.transaction.Transactional;
//
//@SpringBootTest()
//@ActiveProfiles("test")
//@Transactional
//@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//
//public class UserControllerApiTest {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Autowired
//    JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    protected MockMvc mockMvc;
//
//
//
//
//    @Test
//    @Transactional
//    public void 회원가입() throws Exception{
//        SignupRequestDto signupRequestDto = new SignupRequestDto();
//        signupRequestDto.setUsername("김성훈");
//        signupRequestDto.setPassword("123456");
//        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(signupRequestDto);
//
//
//        mockMvc.perform(post("/users/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("signup",
//                        requestFields(
//                                fieldWithPath("username").description("사용자 이름"),
//                                fieldWithPath("password").description("비밀번호")
//
//
//                        )
//                ));
//    }
//
//    @Test
//    @Transactional
//    public void 로그인() throws Exception{
//        User user = new User("김성훈",passwordEncoder.encode("123456"));
//        userRepository.save(user);
//
//        UserDto userDto = new UserDto();
//        userDto.setUsername("김성훈");
//        userDto.setPassword("123456");
//        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(userDto);
//
//        mockMvc.perform(post("/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("login",
//                        requestFields(
//                                fieldWithPath("username").description("사용자 이름"),
//                                fieldWithPath("password").description("비밀번호")
//                        )
//                ));
//    }
//
//
//}
//
//
//
//
