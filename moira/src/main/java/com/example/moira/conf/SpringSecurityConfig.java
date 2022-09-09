package com.example.moira.conf;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration //Classfile을 설정파일로 사용하게 해주는 어노테이션
@EnableWebSecurity //스프링시큐리티 활성화
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * 어느 요청이건 인증로직 처리
         */
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                ;

        http
                .formLogin()
                ;
    }
}
