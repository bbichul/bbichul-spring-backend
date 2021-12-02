package site.bbichul.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import site.bbichul.controller.JwtAuthenticationEntryPoint;
import site.bbichul.controller.JwtAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                // images 폴더 내의 파일 로그인 없이 허용
                .antMatchers("/img/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                // audio 폴더 사용
                .antMatchers("/audio/**").permitAll()
                // js 폴더를 login 없이 허용
                .antMatchers("/js/**").permitAll()
                // user 폴더를 login 없이도 사용
                .antMatchers("/user/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()

                .antMatchers("/login").permitAll()
                // 모든 html 사용
                .antMatchers("/**.html").permitAll()
                // 닉네임 중복체크 사용
                .antMatchers("/check").permitAll()
                //카카오 로그인 사용
                .antMatchers("/login/kakao").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/").permitAll()
//                .antMatchers("/resolution").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin()
                .loginPage("/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/user/logout")
//                .permitAll()
                .and()
                .exceptionHandling();



        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
