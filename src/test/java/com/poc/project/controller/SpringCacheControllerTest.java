package com.poc.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.project.springcache.models.CacheModel;
import com.poc.project.springcache.service.CacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class SpringCacheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CacheService cacheService;  // Mock the CacheService

    // Assuming CacheController is the class where your endpoints are defined

    @Test
    void getAll() throws Exception {
        when(cacheService.getAllValues()).thenReturn(new HashMap<>());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)));

        verify(cacheService, times(1)).getAllValues();
    }

    @Test
    void getSpecific() throws Exception {
        when(cacheService.getValue("key1")).thenReturn("value1");

        mockMvc.perform(get("/key1"))
                .andExpect(status().isOk())
                .andExpect(content().string("value1"));

        verify(cacheService, times(1)).getValue("key1");
    }

    @Test
    void putCache() throws Exception {
        CacheModel cacheModel = new CacheModel("newKey", "newValue");
        String requestBody = objectMapper.writeValueAsString(cacheModel);

        mockMvc.perform(put("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));

        verify(cacheService, times(1)).addValue("newKey", "newValue");
    }

    @Test
    void deleteEntry() throws Exception {
        mockMvc.perform(delete("/key1"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));

        verify(cacheService, times(1)).deleteValue("key1");
    }
}

