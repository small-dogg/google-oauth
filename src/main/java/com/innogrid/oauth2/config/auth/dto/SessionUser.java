package com.innogrid.oauth2.config.auth.dto;

import com.innogrid.oauth2.domain.user.User;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

  private String name, email, picture;

  public SessionUser(User user) {
    this.name = name;
    this.email = email;
    this.picture = picture;
  }
}
