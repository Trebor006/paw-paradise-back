package com.mibu.pawparadiseback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class HttpConfig {

  private final ApplicationProperties applicationProperties;

  public HttpConfig(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(applicationProperties.getWeb().getCors().getAllowedOrigins());
    config.setAllowedMethods(List.of(applicationProperties.getWeb().getCors().getAllowedMethods().split(",")));
    config.setAllowedHeaders(List.of(applicationProperties.getWeb().getCors().getAllowedHeaders().split(",")));
    config.setAllowCredentials(applicationProperties.getWeb().getCors().isAllowCredentials());

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsFilter(source);
  }
}
