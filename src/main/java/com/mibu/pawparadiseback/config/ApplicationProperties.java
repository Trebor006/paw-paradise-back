package com.mibu.pawparadiseback.config;

import java.util.List;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "spring")
public class ApplicationProperties {

  private WebProperties web;

  @Getter
  public static class WebProperties {
    private CorsProperties cors;

    @Getter
    public static class CorsProperties {
      private List<String> allowedOrigins;
      private String allowedMethods;
      private String allowedHeaders;
      private boolean allowCredentials;
    }
  }
}
