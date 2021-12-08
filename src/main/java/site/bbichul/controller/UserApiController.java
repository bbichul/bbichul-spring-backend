package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    // 로그인 기능
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userDto) throws Exception {
        authenticate(userDto.getUsername(), userDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }
    // 회원가입 기능
    @PostMapping(value = "/users/signup")
    public ResponseEntity<?> createUser(@RequestBody SignupRequestDto userDto) throws Exception {
        userService.registerUser(userDto);
        authenticate(userDto.getUsername(), userDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    //닉네임 중복 체크
    @PostMapping(value = "/users/check")
    public String checkUser(@RequestBody UserDto userDto){
        System.out.println(userDto);
        return userService.checkUser(userDto);
    }

    // 회원 상태 체크
    @PostMapping("/users/withdrawal")
    public void updateStatus(@RequestBody UserDto userDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        userService.setStatus(userDto, username);
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
