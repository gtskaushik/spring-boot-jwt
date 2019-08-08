package com.example.jwt.jwtdemo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;

@Log4j2
public class Testing {
  private String userToken;
  private String adminToken;
  
  private static final HttpClient httpClient = HttpClient.newBuilder()
      .version(Version.HTTP_2)  // this is the default
      .build();
  private static final JsonParser jsonParser = new BasicJsonParser();

  public static void main(String[] args) throws IOException, InterruptedException {
    Testing testing = new Testing();
    testing.testAdmin();
    testing.testUser();
  }
  
  private void testUser() throws IOException, InterruptedException {
    testUserAuth();
    testUserMe();
    testUserHello();
  }
  
  private void testUserAuth() throws IOException, InterruptedException {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/authenticate"))
            .POST(
                BodyPublishers.ofString(
                    "{\"username\": \"test_user\",\"password\": \"test_user@123\"}"))
            .setHeader("Content-Type", "application/json")
            .build();

    HttpResponse<String> httpResponse = httpClient.send(request, BodyHandlers.ofString());
    Response response = new Response(httpResponse);
    log.info("Response status test_user - authenticate: " + response);
    userToken = (String) jsonParser.parseMap(httpResponse.body()).get("jwttoken");
  }

  private void testUserMe() throws IOException, InterruptedException {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/me"))
            .GET()
            .setHeader("Authorization", "Bearer " + userToken)
            .build();

    HttpResponse<String> httpResponse = httpClient.send(request, BodyHandlers.ofString());
    Response response = new Response(httpResponse);
    log.info("Response status test_user - me: " + response);
  }

  private void testUserHello() throws IOException, InterruptedException {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/hello"))
            .GET()
            .setHeader("Authorization", "Bearer " + userToken)
            .build();

    HttpResponse<String> httpResponse = httpClient.send(request, BodyHandlers.ofString());
    Response response = new Response(httpResponse);
    log.info("Response status test_user - hello: " + response);
  }

  private void testAdmin() throws IOException, InterruptedException {
    testAdminAuth();
    testAdminMe();
    testAdminHello();
  }

  private void testAdminAuth() throws IOException, InterruptedException {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/authenticate"))
            .POST(
                BodyPublishers.ofString(
                    "{\"username\": \"test_admin\",\"password\": \"test_admin@123\"}"))
            .setHeader("Content-Type", "application/json")
            .build();

    HttpResponse<String> httpResponse = httpClient.send(request, BodyHandlers.ofString());
    Response response = new Response(httpResponse);
    log.info("Response status test_admin - authenticate: " + response);
    adminToken = (String) jsonParser.parseMap(httpResponse.body()).get("jwttoken");
  }

  private void testAdminMe() throws IOException, InterruptedException {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/me"))
            .GET()
            .setHeader("Authorization", "Bearer " + adminToken)
            .build();

    HttpResponse<String> httpResponse = httpClient.send(request, BodyHandlers.ofString());
    Response response = new Response(httpResponse);
    log.info("Response status test_admin - me: " + response);
  }

  private void testAdminHello() throws IOException, InterruptedException {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/hello"))
            .GET()
            .setHeader("Authorization", "Bearer " + adminToken)
            .build();

    HttpResponse<String> httpResponse = httpClient.send(request, BodyHandlers.ofString());
    Response response = new Response(httpResponse);
    log.info("Response status test_admin - hello: " + response);
  }
}
