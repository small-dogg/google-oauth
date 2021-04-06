package com.springstudy.oauth2.config.config.oauth;


import com.springstudy.oauth2.config.config.auth.PrincipleDetails;
import com.springstudy.oauth2.config.domain.user.User;
import com.springstudy.oauth2.config.domain.user.UserRepository;
import java.util.HashMap;
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


    String clientName = userRequest.getClientRegistration().getClientName(); // google
    String providerId = oAuth2User.getAttribute("sub");
    String username = clientName+"_"+providerId;
    String email = oAuth2User.getAttribute("email");
    String password = delegatingPasswordEncoder.encode("getinthere");
    String role = "ROLE_USER";

    User userEntity = userRepository.findByUsername(username);
    if(userEntity==null) {
      userEntity = User.builder()
          .username(username)
          .password(password)
          .email(email)
          .role(role)
          .provider(clientName)
          .providerId(providerId)
          .build();
      userRepository.save(userEntity);
    }else{
      System.out.println("You're already signed-up");
    }
    return new PrincipleDetails(userEntity, oAuth2User.getAttributes());
  }
}
