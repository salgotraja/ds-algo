package org.js.lru;

import java.util.Map;
import java.util.stream.Stream;

@FunctionalInterface
public interface WarmupSource<K, V> {
    /** Stream of (key, value) to warm */
    Stream<Map.Entry<K, V>> stream(WarmupConfig config) throws Exception;
}