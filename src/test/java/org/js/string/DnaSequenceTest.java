package org.js.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DnaSequenceTest {

    private final DnaSequence solver = new DnaSequence();

    private static Set<String> asSet(List<String> list) {
        return list == null ? Collections.emptySet() : new HashSet<>(list);
    }

    @Test
    @DisplayName("Example 1: AAAAACCCCC... -> two repeated sequences")
    void example1() {
        String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
        List<String> result = solver.findRepeatedDnaSequences(s);
        Set<String> expected = new HashSet<>(Arrays.asList("AAAAACCCCC", "CCCCCAAAAA"));
        assertEquals(expected, asSet(result));
    }

    @Test
    @DisplayName("Example 2: long run of A's -> single repeated sequence")
    void example2_allA() {
        String s = "AAAAAAAAAAAAA"; // length 13 -> substrings "AAAAAAAAAA" appear multiple times
        List<String> result = solver.findRepeatedDnaSequences(s);
        Set<String> expected = Collections.singleton("AAAAAAAAAA");
        assertEquals(expected, asSet(result));
    }

    @Test
    @DisplayName("Exact length 10 and unique -> no repeats (empty result)")
    void exactLength10_unique() {
        String s = "ACGTACGTAC"; // length 10, only one substring
        List<String> result = solver.findRepeatedDnaSequences(s);
        assertTrue(result.isEmpty(), "Expected empty result for single 10-char string");
    }

    @Test
    @DisplayName("Length less than 10 -> empty result")
    void shortStringReturnsEmpty() {
        assertTrue(solver.findRepeatedDnaSequences("ACGTACG").isEmpty());
        assertThrows(IllegalArgumentException.class,
                () -> solver.findRepeatedDnaSequences(""));
    }

    @Test
    @DisplayName("Null input -> IllegalArgumentException")
    void nullInputThrows() {
        assertThrows(IllegalArgumentException.class, () -> solver.findRepeatedDnaSequences(null));
    }

    @Test
    @DisplayName("Empty string input -> IllegalArgumentException")
    void emptyStringThrows() {
        assertThrows(IllegalArgumentException.class, () -> solver.findRepeatedDnaSequences(""));
    }

    @Test
    @DisplayName("Invalid DNA character triggers IllegalArgumentException")
    void invalidCharacterThrows() {
        String s = "ACGTACGTXACGT"; // contains 'X'
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> solver.findRepeatedDnaSequences(s));
        assertTrue(ex.getMessage().contains("Invalid DNA character"));
    }

    @Test
    @DisplayName("Overlapping repeats (AAAAAAAAAAA) -> single repeated substring")
    void overlappingRepeats() {
        String s = "AAAAAAAAAAA"; // length 11, two overlapping "AAAAAAAAAA"
        List<String> result = solver.findRepeatedDnaSequences(s);
        Set<String> expected = Collections.singleton("AAAAAAAAAA");
        assertEquals(expected, asSet(result));
    }

    @Test
    @DisplayName("Multiple different repeated sequences and ordering doesn't matter")
    void multipleDistinctRepeats() {
        String s = "AAAACCCCCAAAAACCCCCAAAACCCCC"; // contains AAAACCCCCC and CCCCCAAAAA etc.
        List<String> result = solver.findRepeatedDnaSequences(s);
        // Construct expected set by scanning manually (we expect at least these)
        Set<String> expected = new HashSet<>();
        expected.add("AAAACCCCCC".substring(0)); // placeholder to build set; will be checked against actual result
        // instead build expected by scanning with same logic (defensive)
        Set<String> seen = new HashSet<>();
        Set<String> expectedFound = new HashSet<>();
        for (int i = 0; i <= s.length() - 10; i++) {
            String sub = s.substring(i, i + 10);
            if (!seen.add(sub)) expectedFound.add(sub);
        }
        assertEquals(expectedFound, asSet(result));
    }

    @Test
    @DisplayName("Case sensitivity: lowercase letters are invalid")
    void lowercaseCharactersInvalid() {
        String s = "aaaaaaaaaaaaa"; // lowercase; implementation accepts only uppercase A/C/G/T
        assertThrows(IllegalArgumentException.class, () -> solver.findRepeatedDnaSequences(s));
    }

    @Test
    @DisplayName("Large input sanity: repeated pattern of 1000 A's contains 'AAAAAAAAAA'")
    void largeInputSanity() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) sb.append('A');
        List<String> res = solver.findRepeatedDnaSequences(sb.toString());
        assertTrue(asSet(res).contains("AAAAAAAAAA"));
    }
}
