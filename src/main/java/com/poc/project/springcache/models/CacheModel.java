package com.poc.project.springcache.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CacheModel {
    private String cacheKey;
    private String cacheValue;
}
