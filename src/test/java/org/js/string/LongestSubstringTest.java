package org.js.string;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class LongestSubstringTest {

    private LongestSubstring solver;

    @BeforeEach
    void setUp() {
        solver = new LongestSubstring();
    }

    @Test
    void example_case_abcabcbb() {
        assertThat(solver.lengthOfLongestSubstring("abcabcbb")).isEqualTo(3);
        assertThat(solver.longestUniqueSubstring("abcabcbb")).isIn("abc", "bca", "cab");
    }

    @Test
    void all_same_chars_returns_1() {
        assertThat(solver.lengthOfLongestSubstring("aaaa")).isEqualTo(1);
        assertThat(solver.longestUniqueSubstring("aaaa")).isEqualTo("a");
    }

    @Test
    void empty_string_returns_0_and_empty_substring() {
        assertThat(solver.lengthOfLongestSubstring("")).isEqualTo(0);
        assertThat(solver.longestUniqueSubstring("")).isEmpty();
    }

    @Test
    void typical_case_pwwkew() {
        assertThat(solver.lengthOfLongestSubstring("pwwkew")).isEqualTo(3);
        assertThat(solver.longestUniqueSubstring("pwwkew")).isIn("wke", "kew");
    }

    @Test
    void mixed_case_and_symbols() {
        assertThat(solver.lengthOfLongestSubstring("AaBbCc")).isEqualTo(6);
        assertThat(solver.lengthOfLongestSubstring("a!b@c#a")).isEqualTo(6);
        assertThat(solver.longestUniqueSubstring("a!b@c#a")).isIn("a!b@c#", "!b@c#a");
    }

    @Test
    void unicode_bmp_characters() {
        assertThat(solver.lengthOfLongestSubstring("åß∂ƒ©")).isEqualTo(5);
        assertThat(solver.longestUniqueSubstring("åß∂ƒ©")).isEqualTo("åß∂ƒ©");
    }

    @Test
    void throws_on_null_input() {
        assertThatThrownBy(() -> solver.lengthOfLongestSubstring(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("String can not be null");
        assertThatThrownBy(() -> solver.longestUniqueSubstring(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("String can not be null");
    }
}
