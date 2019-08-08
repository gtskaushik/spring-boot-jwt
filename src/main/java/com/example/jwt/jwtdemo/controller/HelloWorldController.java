package com.example.jwt.jwtdemo.controller;

import com.example.jwt.jwtdemo.config.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Secured("ROLE_ADMIN")
public class HelloWorldController {
  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Secured("ROLE_ADMIN")
  @GetMapping("/hello")
  public String homePage(@AuthenticationPrincipal UserDetails userDetails) {
    String userName = userDetails.getUsername();
    return "Hello " + userName + "! This is your world. Rock it!!";
  }
  
  @Secured("ROLE_USER")
  @GetMapping("/me")
  public Claims getUserDetails(HttpServletRequest request) {
    return getClaims(request);
  }
  
  private Claims getClaims(HttpServletRequest request) {
    final String requestTokenHeader = request.getHeader("Authorization");
    String jwtToken = null;
    Claims claimsFrmToken = null;
    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      claimsFrmToken = jwtTokenUtil.getClaimFromToken(jwtToken, claims -> claims);
    }
    return claimsFrmToken;
  }
}
