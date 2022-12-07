package com.gsdd.earthquake.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EarthquakeConfig {

  @Bean
  public WebClient usgsClient(WebClient.Builder builder,
      @Value("${usgs.base-url}") String usgsBaseUrl) {
    return builder.baseUrl(usgsBaseUrl).defaultHeaders(this::usgsDefaultHeaders).build();
  }

  void usgsDefaultHeaders(HttpHeaders headers) {
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
  }
}
