package org.js.lru;

public record WarmupStats(
        long keysWarmed,
        long failures,
        long finalCacheSize
) {}
