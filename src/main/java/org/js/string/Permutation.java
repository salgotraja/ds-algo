package org.js.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
 * In other words, return true if one of s1's permutations is the substring of s2.
 * <p>
 * Example 1:
 * <p>
 * Input: s1 = "ab", s2 = "eidbaooo"
 * Output: true
 * Explanation: s2 contains one permutation of s1 ("ba").
 * <p>
 * Constraints:
 * 1 <= s1.length, s2.length <= 104
 * s1 and s2 consist of lowercase English letters.
 */
public class Permutation {
    public static void main(String[] args) {

        /*String s = "abc";
        System.out.println(generatePermutations(s));*/

        String s1 = "ab", s2 = "eidbaooo";
        System.out.println(checkInclusion(s1, s2));  // true

        s1 = "ab";
        s2 = "eidboaoo";
        System.out.println(checkInclusion(s1, s2));  // false

        // Additional tests
        s1 = "aa";
        s2 = "bbaa";
        System.out.println(checkInclusion(s1, s2));  // true (substring "aa")

        s1 = "ab";
        s2 = "a";
        System.out.println(checkInclusion(s1, s2));  // false
    }

    public static boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s1.isEmpty() || s2 == null || s2.isEmpty()) {
            throw new IllegalArgumentException("Invalid string");
        }

        int k = s1.length();
        if (k > s2.length()) {
            return false;
        }

        int[] s1Freq = new int[26];
        for (char c : s1.toCharArray()) {
            s1Freq[c - 'a']++;
        }

        int[] windowFreq = new int[26];
        for (int i = 0; i < k; i++) {
            windowFreq[s2.charAt(i) - 'a']++;
        }

        if (Arrays.equals(s1Freq, windowFreq)) {
            return true;
        }

        for (int i = k; i < s2.length(); i++) {
            windowFreq[s2.charAt(i) - 'a']++;
            windowFreq[s2.charAt(i - k) - 'a']--;
            if (Arrays.equals(s1Freq, windowFreq)) {
                return true;
            }
        }

        return false;
    }

    public static boolean checkInclusionBrute(String s1, String s2) {
        if (s1 == null || s1.isEmpty() || s2 == null || s2.isEmpty()) {
            throw new IllegalArgumentException("Invalid string");
        }

        List<String> perms = generatePermutations(s1);

        int k = s1.length();
        for(int i = 0; i < s2.length(); i++) {
            for (int j = i; j <= s2.length() - k; j++) {
                String temp = s2.substring(i, j);
                for (String str : perms) {
                    if (temp.equals(str)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public static List<String> generatePermutations(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Invalid string");
        }

        List<String> permutations = new ArrayList<>();
        permutations.add(String.valueOf(s.charAt(0)));

        for (int i = 1; i < s.length(); i++) {
            char currChar = s.charAt(i);
            List<String> newPermutations = new ArrayList<>();

            for (String p : permutations) {
                for (int j = 0; j <= p.length(); j++){
                    newPermutations.add(p.substring(0, j) + currChar + p.substring(j));
                }
            }
            permutations = newPermutations;
        }

        return permutations;
    }
}
