package com.example.jwt.jwtdemo.service;

import java.io.Serializable;
import lombok.Data;

@Data
public class JwtRequest implements Serializable {
  private static final long serialVersionUID = 5926468583005150707L;
  private String username;
  private String password;
}
