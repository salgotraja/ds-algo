package org.js.lru;

public class Main {
    public static void main(String[] args) {
        DistributedLRUCache<String, String> cache = new DistributedLRUCache<>(2, 3);
        cache.put("a", "1");
        cache.put("b", "2");
        System.out.println(cache.get("a")); // 1

        ScalableDistributedLRUCache<String, Integer> sc = new ScalableDistributedLRUCache<>(1, 2);
        sc.put("x", 10);
        sc.addShard();
        sc.put("y", 20);
        System.out.println(sc.get("x")); //  ascended
    }
}