package org.js.string;

import java.util.LinkedHashMap;
import java.util.Map;

public class FirstUniqueChar {
    public static void main(String[] args) {
        String str = "walmartworld";
        System.out.println(new FirstUniqueChar().uniqueChar(str));
    }

    public char uniqueChar(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Invalid string");
        }

        var map = new LinkedHashMap<Character, Integer>();

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        char ch = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                ch = entry.getKey();
                break;
            }
        }

        return ch;
    }
}
