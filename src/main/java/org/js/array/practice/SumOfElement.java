package org.js.array.practice;

/**
 * Find sum of all elements in an integer array.
 * Input: [1, 2, 3, 4]
 * Output: 10
 * <p>
 * This method handles:
 * - Empty array → returns 0
 * - Integer overflow/underflow - throws ArithmeticException
 * - Null array - throws IllegalArgumentException
 */
public class SumOfElement {
    public static void main(String[] args) {
        SumOfElement sumOfElement = new SumOfElement();
        int [] arr = {1, 2, 3, 4};
        System.out.println(sumOfElement.sumElements(arr));

        int[] arr1 = {2_000_000_000, 1_000_000_000};
        System.out.println(sumOfElement.sumElements(arr1)); // -1294967296 (WRONG!)

    }
    public int sumElements(int [] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (arr.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int j : arr) {
            if (sum > 0 && j > Integer.MAX_VALUE - sum) {
                throw new ArithmeticException("Integer overflow");
            }
            if (sum < 0 && j < Integer.MIN_VALUE - sum) {
                throw new ArithmeticException("Integer underflow");
            }

            sum += j;
        }
        return sum;
    }
}
