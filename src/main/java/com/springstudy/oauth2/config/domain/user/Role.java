package com.springstudy.oauth2.config.domain.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  GUEST("ROLE_GUEST","GUEST"),
  ADMIN("ROLE_ADMIN","ADMIN"),
  USER("ROLE_USER","USER");

  private final String key;
  private final String title;
}
