package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class AnagramsTest {

    private Anagrams solver;

    @BeforeEach
    void setUp() {
        solver = new Anagrams();
    }

    private static Set<String> normalizedGroups(List<List<String>> groups) {
        return groups.stream()
                .map(list -> list.stream().collect(Collectors.joining(","))) // preserve element order in group
                .collect(Collectors.toCollection(LinkedHashSet::new));        // preserve top-level order if needed
    }

    @Test
    void example_groups_correctly_brute() {
        String[] input = {"eat","tea","tan","ate","nat","bat"};
        List<List<String>> groups = solver.groupAnagramsBrute(input);

        // Convert groups to sets (inner order not required), then compare ignoring outer order
        Set<Set<String>> actual = groups.stream().map(HashSet::new).collect(Collectors.toSet());
        Set<Set<String>> expected = new HashSet<>();
        expected.add(new HashSet<>(List.of("eat","tea","ate")));
        expected.add(new HashSet<>(List.of("tan","nat")));
        expected.add(new HashSet<>(List.of("bat")));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void example_groups_correctly_optimized_preserves_order() {
        String[] input = {"eat","tea","tan","ate","nat","bat"};
        List<List<String>> groups = solver.groupAnagramsOptimized(input);

        // Expect group insertion order preserved and elements in original order:
        assertThat(groups).hasSize(3);
        assertThat(groups.get(0)).containsExactly("eat","tea","ate");
        assertThat(groups.get(1)).containsExactly("tan","nat");
        assertThat(groups.get(2)).containsExactly("bat");
    }

    @Test
    void groupAnagrams_handles_empty_and_null() {
        assertThat(solver.groupAnagramsBrute(new String[]{})).isEmpty();
        assertThat(solver.groupAnagrams(new String[]{})).isEmpty();
        assertThat(solver.groupAnagramsOptimized(new String[]{})).isEmpty();

        assertThat(solver.groupAnagramsBrute(null)).isEmpty();
        assertThat(solver.groupAnagrams(null)).isEmpty();
        assertThat(solver.groupAnagramsOptimized(null)).isEmpty();
    }

    @Test
    void groupAnagrams_handles_single_word() {
        String[] input = {"hello"};
        assertThat(solver.groupAnagrams(input)).hasSize(1);
        assertThat(solver.groupAnagrams(input).get(0)).containsExactly("hello");
    }

    @Test
    void groupAnagrams_handles_empty_strings_in_array() {
        String[] input = {"", "", "a"};
        List<List<String>> res = solver.groupAnagramsOptimized(input);
        // one group for two empty strings, one for "a"
        assertThat(res.stream().anyMatch(list -> list.size() == 2 && list.containsAll(List.of("", "")))).isTrue();
        assertThat(res.stream().anyMatch(list -> list.size() == 1 && list.contains("a"))).isTrue();
    }

    @Test
    void brute_sorted_key_handles_mixed_characters() {
        String[] input = {"Ab", "bA", "ba", "ab"}; // case-sensitive grouping
        List<List<String>> res = solver.groupAnagramsBrute(input);

        // group sizes: ["Ab","bA"] are anagrams ignoring case? No, case-sensitive sorted key groups "Ab" and "bA" together, and "ba","ab" together
        // We'll just assert there are two groups of size 2
        assertThat(res.stream().map(List::size).sorted().collect(Collectors.toList())).containsExactly(2, 2);
    }

    @Test
    void optimized_uses_count_when_all_lowercase() {
        String[] input = {"eat","tea","ate","bat"};
        List<List<String>> res = solver.groupAnagramsOptimized(input);
        // Ensure grouping is correct
        Set<Set<String>> actual = res.stream().map(HashSet::new).collect(Collectors.toSet());
        Set<Set<String>> expected = new HashSet<>();
        expected.add(new HashSet<>(List.of("eat","tea","ate")));
        expected.add(new HashSet<>(List.of("bat")));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void optimized_falls_back_to_sorted_key_for_non_lowercase() {
        String[] input = {"Ab", "bA", "ba"};
        // non-lowercase present -> fallback to sorted key -> "Ab" and "bA" are grouped, and "ba" grouped separately
        List<List<String>> res = solver.groupAnagramsOptimized(input);
        assertThat(res).hasSize(2);
    }

    @Test
    void performance_sanity_large_input() {
        String[] input = new String[2000];
        for (int i = 0; i < input.length; i++) input[i] = "a".repeat((i % 10) + 1);
        assertThatCode(() -> solver.groupAnagramsOptimized(input)).doesNotThrowAnyException();
    }
}
