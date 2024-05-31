package com.poc.project.springcache.controller;

import com.poc.project.springcache.models.CacheModel;
import com.poc.project.springcache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class SpringCacheController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/")
    public Map<String, String> getAll(){
        return cacheService.getAllValues();
    }

    @GetMapping("/{key}")
    public String getSpecific(@PathVariable("key") String key){
        return cacheService.getValue(key);
    }

    @PostMapping("/")
    public String putCache(@RequestBody CacheModel cacheModel){
        cacheService.addValue(cacheModel.getCacheKey(), cacheModel.getCacheValue());
        return "OK";
    }

    @DeleteMapping("/{key}")
    public String deleteEntry(@PathVariable("key") String key){
        cacheService.deleteValue(key);
        return "OK";
    }
}
