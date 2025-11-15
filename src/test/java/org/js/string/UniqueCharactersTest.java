package org.js.string;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UniqueCharactersTest {

    private UniqueCharacters unique;

    @BeforeEach
    void setUp() {
        unique = new UniqueCharacters();
    }

    @Test
    void unique_ascii_true_and_false() {
        assertThat(unique.unique("abcdef")).isTrue();
        assertThat(unique.unique("aabcdef")).isFalse();
        assertThat(unique.unique("")).isFalse();   // your method returns false for empty
        assertThat(unique.unique(null)).isFalse();
    }

    @Test
    void isUniqueChars_lowercase_only() {
        assertThat(unique.isUniqueChars("abc")).isTrue();
        assertThat(unique.isUniqueChars("abca")).isFalse();
    }

    @Test
    void uniqueChars_general_case() {
        assertThat(unique.uniqueChars("hello")).isFalse();
        assertThat(unique.uniqueChars("world")).isTrue();
        assertThatThrownBy(() -> unique.uniqueChars("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> unique.uniqueChars(null)).isInstanceOf(IllegalArgumentException.class);
    }
}
