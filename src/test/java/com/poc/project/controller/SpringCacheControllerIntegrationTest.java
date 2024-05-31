package com.poc.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.project.springcache.models.CacheModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringCacheControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() {
        // Assuming the cache is loaded from the text file during startup
        String response = restTemplate.getForObject("http://localhost:" + port + "/", String.class);

        // Add assertions based on your expectations
        assertThat(response).isNotNull(); // Modify this based on your actual response
    }

    @Test
    void testGetSpecific() {
        // Assuming the cache is loaded from the text file during startup
        String key = "key1";
        String response = restTemplate.getForObject("http://localhost:" + port + "/" + key, String.class);

        // Add assertions based on your expectations
        assertThat(response).isNotNull(); // Modify this based on your actual response
    }

    @Test
    void testPutCache() throws Exception {
        CacheModel cacheModel = new CacheModel("newKey", "newValue");
        String requestBody = objectMapper.writeValueAsString(cacheModel);

        HttpHeaders headers = createJsonHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject("http://localhost:" + port + "/", requestEntity, String.class);

        // Add assertions based on your expectations
        assertThat(response).isEqualTo("OK"); // Modify this based on your actual response
    }

    private HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    void testDeleteEntry() {
        // Assuming the cache is loaded from the text file during startup
        String key = "someKey";
        String response = restTemplate.exchange(
                "http://localhost:" + port + "/" + key,
                HttpMethod.DELETE,
                null,
                String.class
        ).getBody();

        // Add assertions based on your expectations
        assertThat(response).isEqualTo("OK"); // Modify this based on your actual response
    }

    // Additional integration tests can be added for other endpoints
}

