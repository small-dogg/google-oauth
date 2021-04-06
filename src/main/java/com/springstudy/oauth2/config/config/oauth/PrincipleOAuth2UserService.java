package com.springstudy.oauth2.config.config.oauth;


import com.springstudy.oauth2.config.config.auth.PrincipleDetails;
import com.springstudy.oauth2.config.config.oauth.provider.FacebookUserInfo;
import com.springstudy.oauth2.config.config.oauth.provider.GoogleUserInfo;
import com.springstudy.oauth2.config.config.oauth.provider.KakaoUserInfo;
import com.springstudy.oauth2.config.config.oauth.provider.NaverUserInfo;
import com.springstudy.oauth2.config.config.oauth.provider.OAuth2UserInfo;
import com.springstudy.oauth2.config.domain.user.User;
import com.springstudy.oauth2.config.domain.user.UserRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipleOAuth2UserService extends DefaultOAuth2UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private DelegatingPasswordEncoder delegatingPasswordEncoder;

  //call post-processing for userRequest Data from google
  //When loadUser method return, will be made @AuthenticationPrinciple
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    //click the google login button -> Google Login Form -> success sign in -> return the code(OAuth0client library) -> Request the AccessToken
    //userRequest Info -> call loadUser method -> get user Profile from google
    OAuth2User oAuth2User = super.loadUser(userRequest);
    System.out.println("getAttributes : " + oAuth2User.getAttributes());

    OAuth2UserInfo oAuth2UserInfo = null;

    if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
      oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
      oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
      oAuth2UserInfo = new NaverUserInfo(
          (Map) oAuth2User.getAttributes().get("response"));
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
      oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes());
    } else {
      System.out.println("We support only google | facebook | naver | kakao");
    }
    String provider = oAuth2UserInfo.getProvider();
    String providerId = oAuth2UserInfo.getProviderId();
    String username = oAuth2UserInfo.getName();
    String email = oAuth2UserInfo.getEmail();
    String password = delegatingPasswordEncoder.encode("getinthere");
    String role = "ROLE_USER";

    User userEntity = userRepository.findByUsername(username);
    if (userEntity == null) {
      userEntity = User.builder()
          .username(username)
          .password(password)
          .email(email)
          .role(role)
          .provider(provider)
          .providerId(providerId)
          .build();
      userRepository.save(userEntity);
    } else {
      System.out.println("You're already signed-up");
    }
    return new PrincipleDetails(userEntity, oAuth2User.getAttributes());
  }
}
