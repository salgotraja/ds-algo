package org.js.string;

import java.util.HashMap;

/**
 * Longest Substring Without Repeating Characters
 * • Input: "abcabcbb"
 * • Output: 3 ("abc")
 * • Edge cases: All the same chars "aaaa" → 1; empty string → 0.
 */
public class LongestSubstring {

    public static void main(String[] args) {
        LongestSubstring longest = new LongestSubstring();
        String str = "abcabcbb";
        System.out.println(longest.lengthOfLongestSubstring(str));
        String str1 = "aaaa";
        System.out.println(longest.lengthOfLongestSubstring(str1));
        String str2 = "";
        System.out.println(longest.lengthOfLongestSubstring(str2));
        String str3 = "pwwkew";
        System.out.println(longest.lengthOfLongestSubstring(str3));

        System.out.println(longest.longestUniqueSubstring(str3));
    }

    public int lengthOfLongestSubstring(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String can not be null");
        }

        if (str.isEmpty()) {
            return 0;
        }

        int left = 0;
        int right = 0;
        int n = str.length();
        int len = 0;

        var map = new HashMap<Character, Integer>();
        while (right < n) {
            if(map.containsKey(str.charAt(right))) {
                left = Math.max(map.get(str.charAt(right)) + 1, left);
            }
            map.put(str.charAt(right), right);
            len = Math.max(len, right - left + 1);
            right++;
        }

        return len;
    }

    public String longestUniqueSubstring(String str) {
        if (str == null) throw new IllegalArgumentException("String can not be null");
        if (str.isEmpty()) return "";

        int left = 0, bestStart = 0, bestLen = 0;
        var last = new HashMap<Character, Integer>();

        for (int right = 0; right < str.length(); right++) {
            char ch = str.charAt(right);
            if (last.containsKey(ch)) {
                left = Math.max(left, last.get(ch) + 1);
            }
            last.put(ch, right);

            int currLen = right - left + 1;
            if (currLen > bestLen) {
                bestLen = currLen;
                bestStart = left;
            }
        }
        return str.substring(bestStart, bestStart + bestLen);
    }
}
