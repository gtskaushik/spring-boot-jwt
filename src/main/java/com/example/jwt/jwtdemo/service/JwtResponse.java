package com.example.jwt.jwtdemo.service;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class JwtResponse implements Serializable {
  private static final long serialVersionUID = -8091879091924046844L;
  @Getter private final String jwttoken;
}
