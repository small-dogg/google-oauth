package com.springstudy.oauth2.config.config.oauth;


import com.springstudy.oauth2.config.domain.user.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipleOAuth2UserService extends DefaultOAuth2UserService {

  //call post-processing for userRequest Data from google
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    Map<String, Object> userMap = super.loadUser(userRequest).getAttributes();

    //click the google login button -> Google Login Form -> success sign in -> return the code(OAuth0client library) -> Request the AccessToken
    //userRequest Info -> call loadUser method -> get user Profile from google
    OAuth2User oAuth2User = super.loadUser(userRequest);



    return super.loadUser(userRequest);
  }
}
