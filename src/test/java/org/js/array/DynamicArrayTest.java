package org.js.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class DynamicArrayTest {

    private DynamicArray array;

    @BeforeEach
    void setUp() {
        array = new DynamicArray();
        array.add(10);
        array.add(20);
        array.add(30);
        array.add(40);
    }

    // ---------- Add ----------
    @Nested
    class AddTests {

        @Test
        void add_shouldIncreaseSize() {
            array.add(50);
            assertThat(array.size()).isEqualTo(5);
            assertThat(array.get(4)).isEqualTo(50);
        }

        @Test
        void add_shouldTriggerGrowthWhenCapacityExceeded() {
            int initialCapacity = array.getCapacity();
            for (int i = 0; i <= initialCapacity; i++) {
                array.add(100 + i);
            }
            assertThat(array.getCapacity()).isGreaterThan(initialCapacity);
            assertThat(array.size()).isGreaterThan(initialCapacity);
        }

        static Stream<Arguments> addAtProvider() {
            return Stream.of(
                    Arguments.of(0, 5, "[5, 10, 20, 30, 40]"),   // insert at beginning
                    Arguments.of(2, 15, "[10, 20, 15, 30, 40]"), // insert in middle
                    Arguments.of(4, 99, "[10, 20, 30, 40, 99]")  // insert at end
            );
        }

        @ParameterizedTest
        @MethodSource("addAtProvider")
        void addAt_shouldInsertAndShiftElements(int index, int value, String expectedArrayString) {
            array.add(index, value);
            assertThat(array.toString()).isEqualTo(expectedArrayString);
        }

        @Test
        void addAt_shouldThrowOnInvalidIndex() {
            assertThatThrownBy(() -> array.add(-1, 5)).isInstanceOf(IndexOutOfBoundsException.class);
            assertThatThrownBy(() -> array.add(10, 5)).isInstanceOf(IndexOutOfBoundsException.class);
        }
    }

    // ---------- Contains ----------
    @Nested
    class ContainsTests {
        static Stream<Arguments> containsProvider() {
            return Stream.of(
                    Arguments.of(10, true),
                    Arguments.of(20, true),
                    Arguments.of(40, true),
                    Arguments.of(99, false),
                    Arguments.of(-1, false)
            );
        }

        @ParameterizedTest
        @MethodSource("containsProvider")
        void contains_shouldReturnExpected(int value, boolean expected) {
            assertThat(array.contains(value)).isEqualTo(expected);
        }
    }

    // ---------- IndexOf ----------
    @Nested
    class IndexOfTests {
        static Stream<Arguments> indexOfProvider() {
            return Stream.of(
                    Arguments.of(10, 0),
                    Arguments.of(20, 1),
                    Arguments.of(30, 2),
                    Arguments.of(40, 3),
                    Arguments.of(99, -1)
            );
        }

        @ParameterizedTest
        @MethodSource("indexOfProvider")
        void indexOf_shouldReturnCorrectIndex(int value, int expectedIndex) {
            assertThat(array.indexOf(value)).isEqualTo(expectedIndex);
        }
    }

    // ---------- Get ----------
    @Nested
    class GetTests {
        static Stream<Arguments> getProvider() {
            return Stream.of(
                    Arguments.of(0, 10),
                    Arguments.of(1, 20),
                    Arguments.of(2, 30),
                    Arguments.of(3, 40)
            );
        }

        @ParameterizedTest
        @MethodSource("getProvider")
        void get_shouldReturnCorrectValue(int index, int expected) {
            assertThat(array.get(index)).isEqualTo(expected);
        }

        @Test
        void get_shouldThrowOnInvalidIndex() {
            assertThatThrownBy(() -> array.get(-1)).isInstanceOf(IndexOutOfBoundsException.class);
            assertThatThrownBy(() -> array.get(4)).isInstanceOf(IndexOutOfBoundsException.class);
        }
    }

    // ---------- Set ----------
    @Nested
    class SetTests {
        static Stream<Arguments> setProvider() {
            return Stream.of(
                    Arguments.of(0, 99),
                    Arguments.of(1, 88),
                    Arguments.of(2, 77),
                    Arguments.of(3, 66)
            );
        }

        @ParameterizedTest
        @MethodSource("setProvider")
        void set_shouldReplaceValue(int index, int newValue) {
            array.set(index, newValue);
            assertThat(array.get(index)).isEqualTo(newValue);
        }

        @Test
        void set_shouldThrowOnInvalidIndex() {
            assertThatThrownBy(() -> array.set(-1, 5)).isInstanceOf(IndexOutOfBoundsException.class);
            assertThatThrownBy(() -> array.set(4, 5)).isInstanceOf(IndexOutOfBoundsException.class);
        }
    }

    // ---------- RemoveAt ----------
    @Nested
    class RemoveAtTests {
        static Stream<Arguments> removeAtProvider() {
            return Stream.of(
                    Arguments.of(0, 10, "[20, 30, 40]"),
                    Arguments.of(1, 20, "[10, 30, 40]"),
                    Arguments.of(2, 30, "[10, 20, 40]"),
                    Arguments.of(3, 40, "[10, 20, 30]")
            );
        }

        @ParameterizedTest
        @MethodSource("removeAtProvider")
        void removeAt_shouldRemoveCorrectElement(int index, int expectedRemoved, String expectedArrayString) {
            int removed = array.removeAt(index);
            assertThat(removed).isEqualTo(expectedRemoved);
            assertThat(array.toString()).isEqualTo(expectedArrayString);
        }

        @Test
        void removeAt_shouldThrowOnInvalidIndex() {
            assertThatThrownBy(() -> array.removeAt(-1)).isInstanceOf(IndexOutOfBoundsException.class);
            assertThatThrownBy(() -> array.removeAt(4)).isInstanceOf(IndexOutOfBoundsException.class);
        }
    }

    // ---------- RemoveValue ----------
    @Nested
    class RemoveValueTests {
        static Stream<Arguments> removeValueProvider() {
            return Stream.of(
                    Arguments.of(10, true, "[20, 30, 40]"),
                    Arguments.of(20, true, "[10, 30, 40]"),
                    Arguments.of(30, true, "[10, 20, 40]"),
                    Arguments.of(40, true, "[10, 20, 30]"),
                    Arguments.of(99, false, "[10, 20, 30, 40]")
            );
        }

        @ParameterizedTest
        @MethodSource("removeValueProvider")
        void removeValue_shouldWorkAsExpected(int value, boolean expectedResult, String expectedArrayString) {
            boolean result = array.removeValue(value);
            assertThat(result).isEqualTo(expectedResult);
            assertThat(array.toString()).isEqualTo(expectedArrayString);
        }
    }

    // ---------- Remove last ----------
    @Nested
    class RemoveLastTests {
        @Test
        void remove_shouldRemoveLastElement() {
            int removed = array.remove();
            assertThat(removed).isEqualTo(40);
            assertThat(array.toString()).isEqualTo("[10, 20, 30]");
        }

        @Test
        void remove_shouldThrowOnEmptyArray() {
            DynamicArray empty = new DynamicArray();
            assertThatThrownBy(empty::remove)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Array is empty");
        }
    }

    // ---------- Utility Methods ----------
    @Nested
    class UtilityTests {
        @Test
        void isEmpty_and_clear() {
            assertThat(array.isEmpty()).isFalse();
            array.clear();
            assertThat(array.isEmpty()).isTrue();
            assertThat(array.size()).isZero();
        }

        @Test
        void size_and_capacityGrowth() {
            int initialCapacity = array.getCapacity();
            for (int i = 0; i <= initialCapacity; i++) {
                array.add(i);
            }
            assertThat(array.getCapacity()).isGreaterThan(initialCapacity);
            assertThat(array.size()).isGreaterThan(initialCapacity);
        }

        @Test
        void trimToSize_shouldShrinkCapacity() {
            DynamicArray arr = new DynamicArray(20);
            arr.add(1);
            arr.add(2);

            int oldCap = arr.getCapacity();
            arr.trimToSize();

            assertThat(arr.getCapacity()).isLessThan(oldCap);
            assertThat(arr.size()).isEqualTo(2);
        }

        @Test
        void toArray_shouldReturnIndependentCopy() {
            int[] copy = array.toArray();
            assertThat(copy).containsExactly(10, 20, 30, 40);

            copy[0] = 999;
            assertThat(array.get(0)).isEqualTo(10); // original unaffected
        }

        @Test
        void toString_shouldReturnReadableFormat() {
            assertThat(array.toString()).isEqualTo("[10, 20, 30, 40]");
        }
    }
}
