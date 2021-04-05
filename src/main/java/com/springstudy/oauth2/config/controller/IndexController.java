package com.springstudy.oauth2.config.controller;

import com.springstudy.oauth2.config.domain.user.User;
import com.springstudy.oauth2.config.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private DelegatingPasswordEncoder delegatingPasswordEncoder;

  @GetMapping("/test/login")
  public @ResponseBody
  String testLogin(Authentication authentication) {
    System.out.println("========Test Login=============================");
    System.out.println("Authentication : " + authentication.getPrincipal());
    return null;
//    return

  }

  @GetMapping({"", "/"})
  public String index() {
    return "index";
  }

  @GetMapping("/user")
  public @ResponseBody
  String user() {
    return "user";
  }

  @GetMapping("/admin")
  public @ResponseBody
  String admin() {
    return "admin";
  }

  @GetMapping("/manager")
  public @ResponseBody
  String manager() {
    return "manager";
  }

  @GetMapping("/loginForm")
  public String loginForm() {
    return "loginForm";
  }

  @GetMapping("/joinForm")
  public String joinForm() {
    return "joinForm";
  }

  @PostMapping("/join")
  public String join(User user) {
    user.setRole("ROLE.USER");
    user.setPassword(delegatingPasswordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return "redirect:/loginForm";
  }

  @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
  @GetMapping("/info")
  public @ResponseBody
  String info() {
    return "User Profile";
  }

  @PostAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  @GetMapping("/data")
  public @ResponseBody
  String data() {
    return "Data info";
  }
}
