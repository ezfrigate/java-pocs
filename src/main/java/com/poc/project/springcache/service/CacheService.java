package com.poc.project.springcache.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CacheService {

    private final Map<String, String> cache = new HashMap<>();

    public CacheService() {
        initializeCacheFromFile();
    }

    private void initializeCacheFromFile() {
        // Read values from a file and populate the cache
        try {
            Path filePath = Path.of("src/main/resources/intial-cache-data.txt");
            cache.putAll(Files.lines(filePath)
                    .map(line -> line.split(","))
                    .collect(Collectors.toMap(parts -> parts[0], parts -> parts[1])));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Cacheable(value = "myCache", key = "#key")
    public String getValue(String key) {
        // This method is cached, so it will only be executed if the value is not in the cache
        return cache.get(key);
    }

    public Map<String, String> getAllValues() {
        // Return the entire cache map
        return new HashMap<>(cache);
    }
    @CachePut(value = "myCache", key = "#key")
    public String addValue(String key, String value) {
        // This method will always be executed, and the result will be stored in the cache
        cache.put(key, value);
        return value;
    }

    @CacheEvict(value = "myCache", key = "#key")
    public void deleteValue(String key) {
        // This method will evict the entry from the cache
        cache.remove(key);
    }
}

