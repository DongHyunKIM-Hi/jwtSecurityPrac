package com.example.kongservicesecurity.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에대한 접근 제한을 할것이다.
                .antMatchers("/api/hello").permitAll() // 제한의 예외로"/api/hello"라는 url의 경우에는 모두 접근 혀옹할 것이다.
                .anyRequest().authenticated(); // 나머지 요청들에는 권한이 필요함
    }
}
