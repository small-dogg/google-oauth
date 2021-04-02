package com.springstudy.oauth2.config.controller;

import com.springstudy.oauth2.config.domain.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

  @GetMapping({"","/"})
  public @ResponseBody String index(){
    return "index";
  }

  @GetMapping("/user")
  public @ResponseBody String user(){
    return "user";
  }

  @GetMapping("/admin")
  public @ResponseBody String admin(){
    return "admin";
  }

  @GetMapping("/manager")
  public @ResponseBody String manager(){
    return "manager";
  }

  @GetMapping("/loginForm")
  public String login(){
    return "loginForm";
  }

  @GetMapping("/joinForm")
  public String joinForm(){
    return "joinForm";
  }

  @PostMapping("/join")
  public @ResponseBody String join(User user){
    System.out.println(user.getEmail());
    return "join";
  }
}
