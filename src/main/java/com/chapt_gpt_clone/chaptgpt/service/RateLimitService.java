package com.chapt_gpt_clone.chaptgpt.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String email) {

        return buckets.computeIfAbsent(
                email,
                this::newBucket
        );
    }

    private Bucket newBucket(String key) {

        Bandwidth limit = Bandwidth.builder()
                .capacity(1)
                .refillIntervally(1, Duration.ofMinutes(1))
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

}