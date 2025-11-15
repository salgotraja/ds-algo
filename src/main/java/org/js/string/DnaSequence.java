package org.js.string;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The DNA sequence is composed of a series of nucleotides abbreviated as 'A', 'C', 'G', and 'T'.
 * <p>
 * For example, "ACGAATTCCG" is a DNA sequence.
 * When studying DNA, it is useful to identify repeated sequences within the DNA.
 * <p>
 * Given a string s that represents a DNA sequence, return all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule.
 * You may return the answer in any order.
 * <p>
 * Example 1:
 * <p>
 * Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 * Output: ["AAAAACCCCC","CCCCCAAAAA"]
 * <p>
 * Example 2:
 * <p>
 * Input: s = "AAAAAAAAAAAAA"
 * Output: ["AAAAAAAAAA"]
 * <p>
 * Constraints:
 * <p>
 * 1 <= s.length <= 105
 * s[i] is either 'A', 'C', 'G', or 'T'.
 */
public class DnaSequence {
    // Optimization, but keep it in the back of pocket.
    private static final int L = 10;                 // substring length
    private static final int BITS_PER_CHAR = 2;      // 2 bits per nucleotide
    private static final int WINDOW_BITS = L * BITS_PER_CHAR; // 20
    private static final int MASK = (1 << WINDOW_BITS) - 1;   // keep last 20 bits


    public static void main(String[] args) {
        DnaSequence dnaSequence = new DnaSequence();
        String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
        System.out.println(dnaSequence.findRepeatedDnaSequencesOptimized(s));

        s = "AAAAAAAAAAAAA";
        System.out.println(dnaSequence.findRepeatedDnaSequences(s));

        s = "AAAAAAAAAAA";
        System.out.println(dnaSequence.findRepeatedDnaSequences(s));
    }

    public List<String> findRepeatedDnaSequences(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Invalid string");
        }

        if (s.length() < 10) {
            return new ArrayList<>();
        }

        // Validate DNA characters (A/C/G/T only)
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != 'A' && c != 'C' && c != 'G' && c != 'T') {
                throw new IllegalArgumentException("Invalid DNA character: " + c);
            }
        }

        Set<String> result = new HashSet<>();
        Set<String> seen = new HashSet<>();

        for (int i = 0; i <= s.length() - 10; i++) {
            String sub = s.substring(i, i + 10);
            if (!seen.add(sub)) {
                result.add(sub);
            }
        }

        return new ArrayList<>(result);
    }

    public List<String> findRepeatedDnaSequencesOptimized(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Invalid string");
        }
        if (s.length() < L) {
            return new ArrayList<>();
        }

        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicatesBits = new HashSet<>();
        int hash = 0;

        for (int i = 0; i < s.length(); i++) {
            int val = charToBits(s.charAt(i)); // validates char; throws on invalid
            // push 2 bits and mask to keep only last 20 bits
            hash = ((hash << BITS_PER_CHAR) | val) & MASK;

            if (i >= L - 1) { // we have a full 10-char window ending at i
                if (!seen.add(hash)) {
                    duplicatesBits.add(hash); // already seen once before
                }
            }
        }

        // decode duplicates bits back to strings
        List<String> result = new ArrayList<>(duplicatesBits.size());
        for (int bits : duplicatesBits) {
            result.add(decode(bits));
        }
        return result;
    }

    private static int charToBits(char c) {
        // A -> 00 (0), C -> 01 (1), G -> 10 (2), T -> 11 (3)
        switch (c) {
            case 'A': return 0;
            case 'C': return 1;
            case 'G': return 2;
            case 'T': return 3;
            default:
                throw new IllegalArgumentException("Invalid DNA character: " + c);
        }
    }

    private static String decode(int bits) {
        // bits encodes 10 chars starting from most significant 2-bit chunk to least
        char[] out = new char[L];
        for (int k = 0; k < L; k++) {
            int shift = BITS_PER_CHAR * (L - 1 - k); // 18,16,...,0
            int twoBits = (bits >> shift) & 0b11;
            out[k] = bitsToChar(twoBits);
        }
        return new String(out);
    }

    private static char bitsToChar(int b) {
        switch (b) {
            case 0: return 'A';
            case 1: return 'C';
            case 2: return 'G';
            case 3: return 'T';
            default: throw new IllegalStateException("Unexpected bits: " + b);
        }
    }

}
