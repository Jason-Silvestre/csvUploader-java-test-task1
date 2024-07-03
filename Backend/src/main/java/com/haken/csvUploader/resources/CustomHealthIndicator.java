package com.haken.csvUploader.resources;

import org.springframework.boot.actuate.health.Health;

import org.springframework.boot.actuate.health.HealthIndicator;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    private static final String API_HEALTH_CHECK_URL = "http://localhost:8080/api/v1/health";

    @Override
    public Health health() {
        boolean isHealthy = checkHealth();

        if (isHealthy) {
            return Health.up().withDetail("API Health Check", "API is reachable").build();
        } else {
            return Health.down().withDetail("API Health Check", "API is not reachable").build();
        }
    }

    private boolean checkHealth() {
        try {
            // Performs a GET request to check the health of the API
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(API_HEALTH_CHECK_URL, String.class);

            // Checks whether the API response was successful (status code 2xx)
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}