package com.mibu.pawparadiseback.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring")
public class ApplicationProperties {

  private WebProperties web;

  @Getter
  @Setter
  public static class WebProperties {
    private CorsProperties cors;

    @Getter
    @Setter
    public static class CorsProperties {
      private List<String> allowedOrigins;
      private String allowedMethods;
      private String allowedHeaders;
      private boolean allowCredentials;
    }
  }
}
