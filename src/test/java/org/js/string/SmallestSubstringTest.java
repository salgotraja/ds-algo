package org.js.string;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class SmallestSubstringTest {

    private SmallestSubstring solver;

    @BeforeEach
    void setUp() {
        solver = new SmallestSubstring();
    }

    @Test
    void example_ADOBECODEBANC() {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        assertThat(solver.smallestSubstring(s, t)).isEqualTo("BANC");
    }

    @Test
    void t_not_in_s_returns_empty() {
        assertThat(solver.smallestSubstring("hello", "world")).isEmpty();
        assertThat(solver.smallestSubstring("", "a")).isEmpty();
    }

    @Test
    void t_longer_than_s_returns_empty() {
        assertThat(solver.smallestSubstring("a", "aa")).isEmpty();
    }

    @Test
    void identical_single_char() {
        assertThat(solver.smallestSubstring("a", "a")).isEqualTo("a");
    }

    @Test
    void handles_duplicates_and_counts() {
        // requires two 'A's and one 'B'
        String s = "AAXBYAABZ";
        String t = "AAB";
        // smallest window that contains 2 A's and 1 B is "AAB" (the first occurrence)
        assertThat(solver.smallestSubstring(s, t)).isEqualTo("AAB");
    }

    @Test
    void multiple_valid_windows_choose_smallest() {
        String s = "abcbcba";
        String t = "abc";
        // possible windows: "abc", "bcb", "cba" — smallest expected is "abc" (len=3)
        assertThat(solver.smallestSubstring(s, t).length()).isEqualTo(3);
        assertThat(solver.smallestSubstring(s, t)).isIn("abc", "bca", "cab", "cba");
    }

    @Test
    void spaces_and_symbols() {
        String s = "this is a test string";
        String t = " tsg";
        String res = solver.smallestSubstring(s, t);
        // verify the returned window actually contains t's characters (multiset)
        assertThat(containsAllChars(res, t)).isTrue();
    }

    @Test
    void unicode_characters() {
        String s = "αβγδεζηθικλμνξοπ";
        String t = "γηθ";
        String res = solver.smallestSubstring(s, t);
        assertThat(containsAllChars(res, t)).isTrue();
    }

    @Test
    void throws_on_null_input() {
        assertThatThrownBy(() -> solver.smallestSubstring(null, "a"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> solver.smallestSubstring("a", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void empty_t_returns_empty() {
        assertThat(solver.smallestSubstring("any", "")).isEmpty();
    }

    @Test
    void brute_force_sanity_small_random_tests() {
        Random rnd = new Random(0);
        final int TESTS = 200;
        final String alphabet = "abc";
        for (int t = 0; t < TESTS; t++) {
            int n = rnd.nextInt(6);
            int m = rnd.nextInt(4);
            String s = randomString(rnd, alphabet, n);
            String tt = randomString(rnd, alphabet, m);

            String expected = bruteMinWindow(s, tt);
            String actual = solver.smallestSubstring(s, tt);

            if (expected.isEmpty()) {
                assertThat(actual).isEmpty();
            } else {
                assertThat(actual.length()).isEqualTo(expected.length());
                assertThat(containsAllChars(actual, tt)).isTrue();
            }

        }
    }

    /* ----------------- Helpers ----------------- */

    // verifies that window contains all chars of target (respecting counts)
    private static boolean containsAllChars(String window, String target) {
        if (target == null) return false;
        Map<Character, Integer> need = new HashMap<>();
        for (char c : target.toCharArray()) need.merge(c, 1, Integer::sum);
        for (char c : window.toCharArray()) {
            if (need.containsKey(c)) {
                need.put(c, need.get(c) - 1);
                if (need.get(c) == 0) need.remove(c);
            }
        }
        return need.isEmpty();
    }

    private static String randomString(Random rnd, String alphabet, int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append(alphabet.charAt(rnd.nextInt(alphabet.length())));
        return sb.toString();
    }

    private static String bruteMinWindow(String s, String t) {
        if (s == null || t == null || t.length() == 0 || s.length() < t.length()) return "";
        String best = "";
        int bestLen = Integer.MAX_VALUE;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                String sub = s.substring(i, j);
                if (containsAllChars(sub, t)) {
                    int L = sub.length();
                    if (L < bestLen || (L == bestLen && sub.compareTo(best) < 0)) {
                        bestLen = L;
                        best = sub;
                    }
                }
            }
        }
        return bestLen == Integer.MAX_VALUE ? "" : best;
    }
}
