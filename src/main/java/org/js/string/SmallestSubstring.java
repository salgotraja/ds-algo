package org.js.string;

import java.util.*;

/**
 * Minimum Window Substring
 *  • Problem: Find the smallest substring in s containing all chars of t.
 *  • Input: s="ADOBECODEBANC", t="ABC"
 *  • Output: "BANC"
 *   • Edge cases: t not in s → return "".
 */
public class SmallestSubstring {
    public static void main(String[] args) {
        SmallestSubstring solver = new SmallestSubstring();
        System.out.println(solver.smallestSubstring("ADOBECODEBANC", "ABC")); // "BANC"
        System.out.println(solver.smallestSubstring("a", "a"));               // "a"
        System.out.println(solver.smallestSubstring("a", "aa"));              // ""
        System.out.println(solver.smallestSubstring("this is a test", "tist"));// "t is"
    }

    /**
     * Returns the minimum window substring of s that contains all characters of t (including duplicates).
     * If no such window exists, it returns an empty string.
     *
     * Handles ASCII fast-path; falls back to HashMap for non-ASCII characters.
     */
    public String smallestSubstring(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("String or target can not be null");
        }
        if (t.isEmpty() || s.isEmpty() || t.length() > s.length()) {
            return "";
        }

        if (isAscii(s) && isAscii(t)) {
            return minWindowAscii(s, t);
        } else {
            return minWindowUnicode(s, t);
        }
    }

    // Generic Unicode-safe path
    public String minWindowUnicode(String s, String t) {
        if (s == null || t == null) throw new IllegalArgumentException("String or target can not be null");
        if (t.isEmpty() || s.isEmpty() || t.length() > s.length()) return "";

        var need = new HashMap<Character, Integer>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int missing = t.length();
        int left = 0, bestStart = 0, bestLen = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            if (need.containsKey(c)) {
                need.put(c, need.get(c) - 1);
                if (need.get(c) >= 0) {
                    missing--;
                }
            }

            while (missing == 0) {
                int windowLen = right - left + 1;

                if (windowLen < bestLen) {
                    bestLen = windowLen;
                    bestStart = left;
                }

                char cl = s.charAt(left);
                if (need.containsKey(cl)) {
                    need.put(cl, need.get(cl) + 1);
                    if (need.get(cl) > 0) {
                        missing++;
                    }
                }
                left++;
            }
        }

        if (bestLen == Integer.MAX_VALUE) return "";

        return s.substring(bestStart, bestStart + bestLen);
    }

    // ASCII fast path
    private String minWindowAscii(String s, String t) {
        final int[] need = new int[128];
        Arrays.fill(need, 0);

        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }

        int missing = t.length();
        int left = 0;
        int bestStart = 0, bestLen = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            char cr = s.charAt(right);
            need[cr]--;

            if (need[cr] >= 0) {
                missing--;
            }

            while (missing == 0) {
                int windowLen = right - left + 1;
                if (windowLen < bestLen) {
                    bestLen = windowLen;
                    bestStart = left;
                }

                char cl = s.charAt(left);
                need[cl]++;

                if (need[cl] > 0) {
                    missing++;
                }
                left++;
            }
        }

        if (bestLen == Integer.MAX_VALUE) return "";

        return s.substring(bestStart, bestStart + bestLen);
    }

    // small helper to detect ASCII-only strings (<= 127)
    private boolean isAscii(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) > 127) return false;
        }
        return true;
    }
}
