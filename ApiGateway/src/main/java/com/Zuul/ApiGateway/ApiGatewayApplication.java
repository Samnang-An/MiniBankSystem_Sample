package com.Zuul.ApiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }

  @Bean
  public DiscoveryClientRouteDefinitionLocator discoveryClientRouteDefinitionLocator(
      ReactiveDiscoveryClient rdc,
      DiscoveryLocatorProperties dlp) {
    dlp.setLowerCaseServiceId(true); // Optional
    return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
  }
}