package org.js.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class GenericDynamicArrayTest {

    private GenericDynamicArray<Integer> array;

    @BeforeEach
    void setUp() {
        array = new GenericDynamicArray<>();
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
            assertThat(array.getLast()).isEqualTo(50);
        }

        @Test
        void add_shouldTriggerGrowthWhenCapacityExceeded() {
            GenericDynamicArray<Integer> arr = new GenericDynamicArray<>(2);
            arr.add(1);
            arr.add(2);
            int oldCap = arr.getCapacity();
            arr.add(3); // triggers growth
            assertThat(arr.getCapacity()).isGreaterThan(oldCap);
            assertThat(arr.size()).isEqualTo(3);
        }

        static Stream<Arguments> addAtProvider() {
            return Stream.of(
                    Arguments.of(0, 5, "[5, 10, 20, 30, 40]"),   // insert at start
                    Arguments.of(2, 15, "[10, 20, 15, 30, 40]"), // middle
                    Arguments.of(4, 99, "[10, 20, 30, 40, 99]")  // end
            );
        }

        @ParameterizedTest
        @MethodSource("addAtProvider")
        void addAt_shouldInsertAndShiftElements(int index, int value, String expected) {
            array.add(index, value);
            assertThat(array.toString()).isEqualTo(expected);
        }

        @Test
        void addAt_shouldThrowOnInvalidIndex() {
            assertThatThrownBy(() -> array.add(-1, 5)).isInstanceOf(IndexOutOfBoundsException.class);
            assertThatThrownBy(() -> array.add(10, 5)).isInstanceOf(IndexOutOfBoundsException.class);
        }
    }

    // ---------- Get / Set ----------
    @Nested
    class GetSetTests {
        @Test
        void get_shouldReturnCorrectElement() {
            assertThat(array.get(0)).isEqualTo(10);
            assertThat(array.get(3)).isEqualTo(40);
        }

        @Test
        void get_shouldThrowOnInvalidIndex() {
            assertThatThrownBy(() -> array.get(-1)).isInstanceOf(IndexOutOfBoundsException.class);
            assertThatThrownBy(() -> array.get(4)).isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        void set_shouldUpdateElement() {
            array.set(1, 99);
            assertThat(array.get(1)).isEqualTo(99);
        }

        @Test
        void set_shouldThrowOnInvalidIndex() {
            assertThatThrownBy(() -> array.set(-1, 5)).isInstanceOf(IndexOutOfBoundsException.class);
            assertThatThrownBy(() -> array.set(4, 5)).isInstanceOf(IndexOutOfBoundsException.class);
        }
    }

    // ---------- GetFirst / GetLast ----------
    @Nested
    class FirstLastTests {
        @Test
        void getFirst_shouldReturnFirstElement() {
            assertThat(array.getFirst()).isEqualTo(10);
        }

        @Test
        void getLast_shouldReturnLastElement() {
            assertThat(array.getLast()).isEqualTo(40);
        }

        @Test
        void getFirst_shouldThrowWhenEmpty() {
            GenericDynamicArray<Integer> empty = new GenericDynamicArray<>();
            assertThatThrownBy(empty::getFirst).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        void getLast_shouldThrowWhenEmpty() {
            GenericDynamicArray<Integer> empty = new GenericDynamicArray<>();
            assertThatThrownBy(empty::getLast).isInstanceOf(NoSuchElementException.class);
        }
    }

    // ---------- Remove ----------
    @Nested
    class RemoveTests {
        @Test
        void remove_shouldRemoveLastElement() {
            int removed = array.remove();
            assertThat(removed).isEqualTo(40);
            assertThat(array.size()).isEqualTo(3);
        }

        @Test
        void remove_shouldThrowOnEmptyArray() {
            GenericDynamicArray<Integer> empty = new GenericDynamicArray<>();
            assertThatThrownBy(empty::remove).isInstanceOf(IllegalStateException.class);
        }

        @Test
        void removeAt_shouldRemoveCorrectElement() {
            int removed = array.remove(1);
            assertThat(removed).isEqualTo(20);
            assertThat(array.toString()).isEqualTo("[10, 30, 40]");
        }

        @Test
        void removeAt_shouldThrowOnInvalidIndex() {
            assertThatThrownBy(() -> array.remove(-1)).isInstanceOf(IndexOutOfBoundsException.class);
            assertThatThrownBy(() -> array.remove(4)).isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        void removeValue_shouldRemoveMatchingElement() {
            boolean result = array.remove(Integer.valueOf(20));
            assertThat(result).isTrue();
            assertThat(array.toString()).isEqualTo("[10, 30, 40]");
        }

        @Test
        void removeValue_shouldReturnFalseIfNotFound() {
            boolean result = array.remove(Integer.valueOf(99));
            assertThat(result).isFalse();
        }

        @Test
        void removeFirst_shouldRemoveHead() {
            array.removeFirst();
            assertThat(array.toString()).isEqualTo("[20, 30, 40]");
        }

        @Test
        void removeLast_shouldRemoveTail() {
            array.removeLast();
            assertThat(array.toString()).isEqualTo("[10, 20, 30]");
        }
    }

    // ---------- IndexOf / LastIndexOf ----------
    @Nested
    class IndexTests {
        @Test
        void indexOf_shouldReturnCorrectIndex() {
            assertThat(array.indexOf(30)).isEqualTo(2);
            assertThat(array.indexOf(99)).isEqualTo(-1);
        }

        @Test
        void lastIndexOf_shouldReturnCorrectIndex() {
            array.add(20);
            assertThat(array.lastIndexOf(20)).isEqualTo(4);
        }
    }

    // ---------- Utility Methods ----------
    @Nested
    class UtilityTests {
        @Test
        void contains_shouldReturnExpected() {
            assertThat(array.contains(30)).isTrue();
            assertThat(array.contains(99)).isFalse();
        }

        @Test
        void isEmpty_shouldReflectSize() {
            assertThat(array.isEmpty()).isFalse();
            array.clear();
            assertThat(array.isEmpty()).isTrue();
        }

        @Test
        void sizeAndCapacity_shouldWork() {
            assertThat(array.size()).isEqualTo(4);
            assertThat(array.getCapacity()).isGreaterThanOrEqualTo(4);
        }

        @Test
        void clear_shouldResetArray() {
            array.clear();
            assertThat(array.size()).isZero();
            assertThat(array.toString()).isEqualTo("[]");
        }

        @Test
        void trimToSize_shouldShrinkCapacity() {
            GenericDynamicArray<Integer> arr = new GenericDynamicArray<>(20);
            arr.add(1);
            arr.add(2);
            arr.trimToSize();
            assertThat(arr.getCapacity()).isEqualTo(2);
        }

        @Test
        void ensureCapacity_shouldIncreaseCapacity() {
            int oldCap = array.getCapacity();
            array.ensureCapacity(oldCap + 10);
            assertThat(array.getCapacity()).isGreaterThan(oldCap);
        }

        @Test
        void toArray_shouldReturnCopy() {
            Object[] copy = array.toArray();
            assertThat(copy).containsExactly(10, 20, 30, 40);
        }

        @Test
        void toArrayGeneric_shouldReturnTypedArray() {
            Integer[] ints = array.toArray(new Integer[0]);
            assertThat(ints).containsExactly(10, 20, 30, 40);
        }

        @Test
        void toString_shouldBeReadable() {
            assertThat(array.toString()).isEqualTo("[10, 20, 30, 40]");
        }
    }

    // ---------- Advanced Features ----------
    // ---------- Advanced Features ----------
    @Nested
    class AdvancedTests {
        @Test
        void removeIf_shouldRemoveAllEvenNumbers() {
            Predicate<Integer> evenFilter = i -> i % 2 == 0;
            boolean removed = array.removeIf(evenFilter);
            assertThat(removed).isTrue();
            assertThat(array.toString()).isEqualTo("[]"); // all even numbers removed
        }

        @Test
        void removeIf_shouldRemoveConditionally() {
            Predicate<Integer> greaterThan20 = i -> i > 20;
            boolean removed = array.removeIf(greaterThan20);
            assertThat(removed).isTrue();
            assertThat(array.toString()).isEqualTo("[10, 20]");
        }

        @Test
        void subList_shouldReturnValidRange() {
            GenericDynamicArray<Integer> sub = array.subList(1, 3);
            assertThat(sub.toString()).isEqualTo("[20, 30]");
        }

        @Test
        void subList_shouldThrowOnInvalidRange() {
            assertThatThrownBy(() -> array.subList(-1, 2)).isInstanceOf(IndexOutOfBoundsException.class);
            assertThatThrownBy(() -> array.subList(1, 10)).isInstanceOf(IndexOutOfBoundsException.class);
            assertThatThrownBy(() -> array.subList(3, 2)).isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        void growthFactor_shouldAffectCapacityExpansion() {
            GenericDynamicArray<Integer> arr = new GenericDynamicArray<>(2);
            arr.setGrowthFactor(2.0);
            arr.add(1);
            arr.add(2);
            int oldCap = arr.getCapacity();
            arr.add(3); // should double capacity
            assertThat(arr.getCapacity()).isEqualTo(oldCap * 2);
        }

        @Test
        void iterator_shouldIterateOverElementsInOrder() {
            int sum = 0;
            for (int value : array) {
                sum += value;
            }
            assertThat(sum).isEqualTo(100); // 10+20+30+40
            assertThat(array).containsExactly(10, 20, 30, 40);
        }
    }


    // ---------- String Tests ----------
    @Nested
    class StringArrayTests {
        private GenericDynamicArray<String> strArray;

        @BeforeEach
        void init() {
            strArray = new GenericDynamicArray<>();
            strArray.add("alpha");
            strArray.add("beta");
            strArray.add("gamma");
            strArray.add("beta"); // duplicate for lastIndexOf
        }

        @Test
        void addAndGet_shouldWork() {
            strArray.add("delta");
            assertThat(strArray.size()).isEqualTo(5);
            assertThat(strArray.get(4)).isEqualTo("delta");
        }

        @Test
        void getFirstAndLast_shouldReturnCorrectElements() {
            assertThat(strArray.getFirst()).isEqualTo("alpha");
            assertThat(strArray.getLast()).isEqualTo("beta");
        }

        @Test
        void indexOfAndLastIndexOf_shouldReturnCorrectIndices() {
            assertThat(strArray.indexOf("beta")).isEqualTo(1);
            assertThat(strArray.lastIndexOf("beta")).isEqualTo(3);
        }

        @Test
        void contains_shouldReturnExpected() {
            assertThat(strArray.contains("gamma")).isTrue();
            assertThat(strArray.contains("zeta")).isFalse();
        }

        @Test
        void removeByValue_shouldWork() {
            boolean removed = strArray.remove("gamma");
            assertThat(removed).isTrue();
            assertThat(strArray.toString()).isEqualTo("[alpha, beta, beta]");
        }

        @Test
        void removeIf_shouldRemoveMatching() {
            boolean removed = strArray.removeIf(s -> s.startsWith("b"));
            assertThat(removed).isTrue();
            assertThat(strArray.toString()).isEqualTo("[alpha, gamma]");
        }

        @Test
        void subList_shouldReturnExpectedRange() {
            GenericDynamicArray<String> sub = strArray.subList(1, 3);
            assertThat(sub.toString()).isEqualTo("[beta, gamma]");
        }

        @Test
        void clear_shouldEmptyArray() {
            strArray.clear();
            assertThat(strArray.isEmpty()).isTrue();
            assertThat(strArray.toString()).isEqualTo("[]");
        }
    }

    // ---------- Mixed Types (sanity check) ----------
    @Nested
    class MixedTypesTests {
        @Test
        void genericArray_shouldHandleDifferentTypes() {
            GenericDynamicArray<Object> mixed = new GenericDynamicArray<>();
            mixed.add("string");
            mixed.add(42);
            mixed.add(true);

            assertThat(mixed.size()).isEqualTo(3);
            assertThat(mixed.get(0)).isInstanceOf(String.class);
            assertThat(mixed.get(1)).isInstanceOf(Integer.class);
            assertThat(mixed.get(2)).isInstanceOf(Boolean.class);
        }
    }
}
