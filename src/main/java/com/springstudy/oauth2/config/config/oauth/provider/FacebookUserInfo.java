package com.springstudy.oauth2.config.config.oauth.provider;

import java.util.Map;

public class FacebookUserInfo implements OAuth2UserInfo {

  private Map<String, Object> attributes;

  public FacebookUserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getProviderId() {
    return String.valueOf(attributes.get("id"));
  }

  @Override
  public String getProvider() {
    return "facebook";
  }

  @Override
  public String getEmail() {
    return String.valueOf(attributes.get("email"));
  }

  @Override
  public String getName() {
    return getProvider() + "_" + attributes.get("name");
  }
}
