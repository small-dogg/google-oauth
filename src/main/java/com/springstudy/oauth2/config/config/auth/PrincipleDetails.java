package com.springstudy.oauth2.config.config.auth;

import com.springstudy.oauth2.config.domain.user.User;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class PrincipleDetails implements UserDetails, OAuth2User {

  private User user;
  private Map<String, Object> attributes;

  //normal user
  public PrincipleDetails(User user) {
    this.user = user;
  }

  //OAuth user
  public PrincipleDetails(User user, Map<String, Object> attributes) {
    this.user = user;
    this.attributes = attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collection = new ArrayList<>();
    collection.add((GrantedAuthority) () -> user.getRole());
    return collection;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    long gap = ChronoUnit.YEARS.between(LocalDateTime.now(),user.getModifiedAt());
    System.out.println("This account's last modified at"+gap);
    return true;
  }


  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String getName() {
    return String.valueOf(attributes.get("sub"));
  }

  public User getUser() {
    return user;
  }
}
