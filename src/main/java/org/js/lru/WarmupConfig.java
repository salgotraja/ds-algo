package org.js.lru;

import java.time.Duration;
import java.util.Set;

public record WarmupConfig(
        int parallelism,           // number of worker threads
        int batchSize,             // fetch N keys at once
        Duration maxDuration,      // fail-fast timeout
        Set<String> keyPatterns,   // optional: "user:*", "post:123*"
        WarmupSource source        // DB, S3, etc.
) {}
