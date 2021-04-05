package com.springstudy.oauth2.config.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
  public User findByUsername(String username);//JPA Query Method
}
