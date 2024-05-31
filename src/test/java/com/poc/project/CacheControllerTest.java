package com.poc.project;

import com.poc.project.springcache.service.CacheService;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class CachePerformanceTest {

    @Autowired
    private CacheService cacheService;  // Assuming this is your cache service

    @RepeatedTest(10)  // Run the test 10 times (adjust as needed)
    void testCachePerformance() {
        // For write performance test
        long startTimeWrite = System.nanoTime();
        cacheService.addValue("performanceKey", "performanceValue");
        long endTimeWrite = System.nanoTime();
        long durationWrite = endTimeWrite - startTimeWrite;
        System.out.println("Write operation took: " + durationWrite + " ns");

        // For read performance test
        long startTimeRead = System.nanoTime();
        String value = cacheService.getValue("performanceKey");
        long endTimeRead = System.nanoTime();
        long durationRead = endTimeRead - startTimeRead;
        System.out.println("Read operation took: " + durationRead + " ns");

        cacheService.deleteValue("performanceKey");

        // Assertions or additional logging based on your specific requirements
        // You may want to assert that the read value matches the expected value
    }
}

