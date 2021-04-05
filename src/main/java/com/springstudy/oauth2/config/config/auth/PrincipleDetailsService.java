package com.springstudy.oauth2.config.config.auth;

import com.springstudy.oauth2.config.domain.user.User;
import com.springstudy.oauth2.config.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// loginProcessingUrl("/login")
//When requested login, loadUserByUsername  IoC made from UserDetailsService will be execute in security configuration
//Spring security(Authentication(UserDetails))
@Service
public class PrincipleDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User userEntity = userRepository.findByUsername(username);

    if(userEntity!=null) return new PrincipleDetails(userEntity);

    return null;
  }
}
