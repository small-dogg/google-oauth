package com.springstudy.oauth2.config.config;

import java.util.HashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
@EnableWebSecurity//register Security config to Spring filter chain
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)//Active @secured, @preAuthorize,@postAuthorize
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public DelegatingPasswordEncoder passwordEncoder() {
    String idForEncode = "bcrypt";
    HashMap<String, PasswordEncoder> encoderHashMap = new HashMap<>();
    encoderHashMap.put("bcrypt", new BCryptPasswordEncoder());
    encoderHashMap.put("pbkdf2", new Pbkdf2PasswordEncoder());
    encoderHashMap.put("scrypt", new SCryptPasswordEncoder());
    return new DelegatingPasswordEncoder(idForEncode,encoderHashMap);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/user/**").authenticated()
        .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
        .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
        .anyRequest().permitAll()
        .and()
        .formLogin()
        .loginPage("/loginForm")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/");
        //.usernameParameter(); rename the username Parameter

  }
}
