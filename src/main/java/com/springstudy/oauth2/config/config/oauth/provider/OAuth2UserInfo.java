package com.springstudy.oauth2.config.config.oauth.provider;

public interface OAuth2UserInfo {
  String getProviderId();
  String getProvider();
  String getEmail();
  String getName();

}
