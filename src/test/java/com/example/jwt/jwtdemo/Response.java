package com.example.jwt.jwtdemo;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import lombok.Data;

@Data
public class Response {
  private int statusCode;
  private HttpHeaders headers;
  private String body;
  
  public Response(HttpResponse<String> httpResponse) {
    this.statusCode = httpResponse.statusCode();
    this.headers = httpResponse.headers();
    this.body = httpResponse.body();
  } 
}
