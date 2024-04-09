package com.exercise.fabrick.demo.connector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

  @Bean
  public WebClient webClient() {

    WebClient webClient = WebClient.builder()
        .baseUrl("https://sandbox.platfr.io")
        .defaultCookie("cookie-name", "cookie-value")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
    return webClient;
  }
}