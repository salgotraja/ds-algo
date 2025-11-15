package org.js.string;

import java.sql.Array;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Given a 0-indexed string s, permute s to get a new string t such that:
 * <p>
 * All consonants remain in their original places. More formally, if there is an index i with 0 <= i < s.length such that s[i] is a consonant, then t[i] = s[i].
 * The vowels must be sorted in the nondecreasing order of their ASCII values. More formally, for pairs of indices i, j with 0 <= i < j < s.length such that s[i] and s[j] are vowels, then t[i] must not have a higher ASCII value than t[j].
 * Return the resulting string.
 * <p>
 * The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in lowercase or uppercase. Consonants comprise all letters that are not vowels.
 * <p>
 * Constraints:
 * <p>
 * 1 <= s.length <= 105
 * s consists only of letters of the English alphabet in uppercase and lowercase.
 */
public class SortVowels {
    public static void main(String[] args) {
        SortVowels sorter = new SortVowels();
        String input = "lEetcOde";
        String sortedString = sorter.sortVowels(input);
        System.out.println("Original: " + input);
        System.out.println("Sorted Vowels: " + sortedString);
    }

    public String sortVowelsInString(String s) {
        var vowels = new ArrayList<Character>();
        for (char c : s.toCharArray()) {
            if (isVowel(c)) {
                vowels.add(c);
            }
        }
        Collections.sort(vowels);

        int vowelIdx = 0;
        var result = new StringBuilder();

        for (char c : s.toCharArray()) {
            if(isVowel(c)) {
                result.append(vowels.get(vowelIdx++));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public String sortVowels(String s) {
        int[] vowelCounts = new int[128];  // ASCII size; only use for vowels
        for (char c : s.toCharArray()) {
            if (isVowel(c)) {
                vowelCounts[c]++;
            }
        }

        // Possible vowels in ASCII order: A(65), E(69), I(73), O(79), U(85), a(97), e(101), i(105), o(111), u(117)
        String sortedVowelChars = "AEIOUaeiou";
        var sortedVowels = new ArrayList<Character>();  // Or char[], but list for simplicity
        for (char vowel : sortedVowelChars.toCharArray()) {
            for (int i = 0; i < vowelCounts[vowel]; i++) {
                sortedVowels.add(vowel);
            }
        }

        int vowelIdx = 0;
        var result = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (isVowel(c)) {
                result.append(sortedVowels.get(vowelIdx++));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private boolean isVowel(char c) {
        c = Character.toLowerCase(c);
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}
