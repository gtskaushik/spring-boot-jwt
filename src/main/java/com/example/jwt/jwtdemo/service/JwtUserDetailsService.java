package com.example.jwt.jwtdemo.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
  @Autowired private PasswordEncoder passwordEncoder;

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails userDetails = createUserDetails(username);
    return userDetails;
  }
  
  private UserDetails createUserDetails(String username) {
    List<GrantedAuthority> grantedAuths = null;
    UserDetails userDetails = null;
    switch (username) {
      case "test_user":
        grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        userDetails = new User(username, passwordEncoder.encode("test_user@123"), grantedAuths);
        break;
      case "test_admin":
        grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER");
        userDetails = new User(username, passwordEncoder.encode("test_admin@123"), grantedAuths);
        break;
      default:
        throw new UsernameNotFoundException("Username " + username + " not found...");
    }
    return userDetails;
  }
}
