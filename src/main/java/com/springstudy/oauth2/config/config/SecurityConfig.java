package com.springstudy.oauth2.config.config;

import com.springstudy.oauth2.config.config.oauth.PrincipleOAuth2UserService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
@EnableWebSecurity//register Security config to Spring filter chain
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//Active @secured, @preAuthorize,@postAuthorize
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private PrincipleOAuth2UserService principleOAuth2UserService;

  @Bean
  public DelegatingPasswordEncoder passwordEncoder() {
    String idForEncode = "bcrypt";
    HashMap<String, PasswordEncoder> encoderHashMap = new HashMap<>();
    encoderHashMap.put("bcrypt", new BCryptPasswordEncoder());
    encoderHashMap.put("pbkdf2", new Pbkdf2PasswordEncoder());
    encoderHashMap.put("scrypt", new SCryptPasswordEncoder());
    return new DelegatingPasswordEncoder(idForEncode, encoderHashMap);
  }

  /**
   * OAuth2 sign up processing 1.get code(Authentication) 2. get access-token(Authorize) 3.get User
   * Profile 4. auto-signup || get more info data for auto-sign
   */

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/user/**").authenticated()
        .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
        .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
        .anyRequest().permitAll()
        //loginPage redirect
        .and()
        .formLogin()
        .loginPage("/loginForm")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/")
        //.usernameParameter("username2") rename the username Parameter

        //OAuth2 Authentication & Authorize
        .and()
        .oauth2Login()
        .loginPage("/loginForm")
        .userInfoEndpoint()
        .userService(principleOAuth2UserService);//Tip) get access-token + user's profile

  }


  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers("/i18n/**")
        .antMatchers("/static/**")
        .antMatchers("/css/**")
        .antMatchers("/js/**")
        .antMatchers("/images/**");
  }
}
