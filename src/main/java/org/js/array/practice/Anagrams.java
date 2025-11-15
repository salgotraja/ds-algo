package org.js.array.practice;

import java.util.*;

/**
 * Group Anagrams
 * Problem: Group strings that are anagrams of each other together.
 * • Input: ["eat","tea","tan","ate","nat","bat"]
 * • Output: [["eat","tea","ate"],["tan","nat"],["bat"]]
 * • Two strings are anagrams if they have the same characters with the same frequencies.
 * • Edge case: Single word, all anagrams, empty array
 */
public class Anagrams {
    public static void main(String[] args) {
        Anagrams anagrams = new Anagrams();
        String[] str = new String[] {"eat","tea","tan","ate","nat","bat"};
        System.out.println(anagrams.groupAnagrams(str));

        String[] str1 = new String[] {"caa", "cat", "act", "aca", "tac"};
        System.out.println(anagrams.groupAnagrams(str1));
    }

    // Time complexity: O(m * nlog n); Space complexity: O(m * n)
    public List<List<String>> groupAnagramsBrute(String[] strs) {
        if (strs == null || strs.length < 1) {
            return new ArrayList<>();
        }

        var result = new HashMap<String, List<String>>();

        for (String s: strs) {
            char[] charArray = s.toCharArray();
            Arrays.sort(charArray);
            String sortedS = new String(charArray);
            result.putIfAbsent(sortedS, new ArrayList<>());
            result.get(sortedS).add(s);
        }

        return new ArrayList<>(result.values());
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();

        var result = new HashMap<String, List<String>>();
        for (String s : strs) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            String key = Arrays.toString(count);
            result.putIfAbsent(key, new ArrayList<>());
            result.get(key).add(s);
        }

        return new ArrayList<>(result.values());
    }

    public List<List<String>> groupAnagramsOptimized(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();

        var map = new LinkedHashMap<String, List<String>>();

        boolean allLowercase = true;
        for (String s : strs) {
            if (s == null) { allLowercase = false; break; }

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if ( c < 'a' || c > 'z') {
                    allLowercase = false;
                    break;
                }
            }
            if (!allLowercase) break;
        }

        for (String s : strs) {
            String key;
            if (allLowercase) {
                int[] count = new int[26];
                for (char c : s.toCharArray()) {
                    count[c - 'a']++;
                }

                StringBuilder sb = new StringBuilder(26 * 2);
                for (int v : count) {
                    sb.append(v).append('#');
                }
                key = sb.toString();
            } else {
                char[] ca = s.toCharArray();
                Arrays.sort(ca);
                key = new String(ca);
            }

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }
}
