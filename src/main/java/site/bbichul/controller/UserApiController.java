package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.JwtResponse;
import site.bbichul.dto.SignupRequestDto;
import site.bbichul.dto.UserDto;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.UserService;
import site.bbichul.utills.JwtTokenUtil;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserApiController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Operation(description = "로그인 기능", method = "POST")
    @PostMapping(value = "/users/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userDto) throws Exception {
        log.info("[USER : {}] Request POST /api/users/login HTTP/1.1", userDto.getUsername());
        authenticate(userDto.getUsername(), userDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    @Operation(description = "회원가입 기능", method = "POST")
    @PostMapping(value = "/users/signup")
    public ResponseEntity<?> createUser(@RequestBody SignupRequestDto userDto) throws Exception {
        log.info("[USER : {}] Request POST /api/users/signup HTTP/1.1", userDto.getUsername());
        userService.registerUser(userDto);
        authenticate(userDto.getUsername(), userDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    @Operation(description = "닉네임 중복 체크", method = "POST")
    @PostMapping(value = "/users/check")
    public String checkUser(@RequestBody UserDto userDto){
        log.info("POST /api/users/check HTTP/1.1");
        System.out.println(userDto);
        return userService.checkUser(userDto);
    }

    @Operation(description = "회원 탈퇴", method = "POST")
    @PostMapping("/users/withdrawal")
    public void updateStatus(@RequestBody UserDto userDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("[USER : {}] Request POST /api/users/withdrawal HTTP/1.1", userDetails.getUsername());
        String username = userDetails.getUsername();
        userService.setStatus(userDto, username);
    }
    @Operation(description = "회원 복구", method = "POST")
    @PostMapping("/users/recover")
    public void updateRecovery(@RequestBody UserDto userDto) {
        log.info("[USER : {}] Request POST /api/users/recover HTTP/1.1", userDto.getUsername());
        userService.setRecover(userDto);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}