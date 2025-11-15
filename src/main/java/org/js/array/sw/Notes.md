# Sliding Window Pattern

The sliding window pattern is used to solve problems involving subarrays or substrings by maintaining a window and expanding/contracting it efficiently.

## Core Concepts

**Time Complexity**: O(n) - each element is visited at most twice (once by right pointer, once by left)
**Space Complexity**: O(1) to O(k) depending on auxiliary data structures

## Two Main Types

### 1. Fixed-Size Window
Window size `k` is predetermined and constant.

**Template:**
```java
public int fixedWindow(int[] arr, int k) {
    // 1. Build the first window
    int sum = 0;
    for (int i = 0; i < k; i++) {
        sum += arr[i];
    }
    int result = sum;

    // 2. Slide the window: add right, remove left
    for (int i = k; i < arr.length; i++) {
        sum = sum + arr[i] - arr[i - k];
        result = updateResult(result, sum); // max, min, etc.
    }
    return result;
}
```

**Examples:**
- Maximum/Minimum sum of subarray of size k
- First negative number in every window of size k

### 2. Variable-Size Window
Window size changes dynamically based on conditions.

**Template:**
```java
public int variableWindow(int[] arr, int target) {
    int left = 0;
    int sum = 0;
    int result = 0;

    for (int right = 0; right < arr.length; right++) {
        // Expand window
        sum += arr[right];

        // Contract window while condition is met
        while (condition_met(sum, target)) {
            result = updateResult(result, right - left + 1);
            sum -= arr[left];
            left++;
        }
    }
    return result;
}
```

**Examples:**
- Minimum length subarray with sum >= target
- Longest substring without repeating characters
- Contains duplicates within distance k

## Common Patterns & Solutions

### Pattern 1: Fixed Window - Max/Min Sum
**Problem**: Find maximum/minimum sum of any k consecutive elements.

**Key Points:**
- Build first window of size k
- Slide by adding next element and removing first element of previous window
- Track max/min as you slide

**Implementation**: See `MaxSum.java`, `MinSum.java`

### Pattern 2: Variable Window - Minimum Length with Condition
**Problem**: Find minimum length subarray whose sum is >= target (e.g., `MinSubArray.java`).

**Key Points:**
- Expand right until condition is met (sum >= target)
- Contract left while condition still holds
- Track minimum length during contraction

**Why it works:**
- Once sum >= target, we try to minimize window from left
- When sum < target again, continue expanding right

### Pattern 3: Fixed Window with Deque - First Element with Property
**Problem**: Find first negative number in every window of size k.

**Key Points:**
- Use deque to track indices of elements with desired property
- Remove indices outside current window from front
- Add new qualifying indices to back

**Implementation**: See `FirstNegativeNumber.java`

**Deque Operations:**
```java
Deque<Integer> dq = new ArrayDeque<>();

// Add element index if it qualifies
if (arr[right] < 0) dq.offerLast(right);

// Remove elements outside window
if (!dq.isEmpty() && dq.peekFirst() == left) dq.pollFirst();

// Get answer for current window
int answer = dq.isEmpty() ? 0 : arr[dq.peekFirst()];
```

### Pattern 4: Fixed Window with Set - Existence Check
**Problem**: Check if duplicates exist within distance k (e.g., `Duplicates.java`).

**Key Points:**
- Maintain set of elements in current window
- Check if element exists before adding
- Remove element that's sliding out

**Implementation:**
```java
Set<Integer> window = new HashSet<>();
for (int i = 0; i < arr.length; i++) {
    if (window.contains(arr[i])) return true;

    window.add(arr[i]);

    if (i >= k) {
        window.remove(arr[i - k]); // remove leftmost element
    }
}
return false;
```

### Pattern 5: Fixed Window with TreeSet - Range Query
**Problem**: Find elements within value range in window (e.g., `ContainsDuplicate.java` - almost duplicate).

**Key Points:**
- TreeSet allows efficient range queries (floor, ceiling)
- Maintain window of size indexDiff
- Check if any element in window is within valueDiff

**Implementation:**
```java
TreeSet<Long> window = new TreeSet<>();
for (int i = 0; i < nums.length; i++) {
    long num = nums[i];

    // Check floor and ceiling
    Long floor = window.floor(num);
    if (floor != null && num - floor <= valueDiff) return true;

    Long ceiling = window.ceiling(num);
    if (ceiling != null && ceiling - num <= valueDiff) return true;

    window.add(num);
    if (i >= indexDiff) {
        window.remove((long) nums[i - indexDiff]);
    }
}
```

## Decision Guide

**Use Fixed Window when:**
- Window size k is given
- You need to examine all windows of the same size
- Examples: max/min sum of k elements, first negative in every window

**Use Variable Window when:**
- Window size depends on a condition
- You need to find optimal (min/max) window length
- Examples: minimum window with sum >= target, longest substring without repeats

**Auxiliary Data Structures:**
- **Set/Map**: Track existence or frequency (duplicates, unique characters)
- **Deque**: Track indices of qualifying elements in order (first negative, max in window)
- **TreeSet**: Range queries within window (floor/ceiling operations)

## Common Mistakes

1. **Off-by-one errors**: Ensure window size calculation `right - left + 1` is correct
2. **Forgetting to shrink**: In variable window, always shrink when condition is met
3. **Not clearing old elements**: In fixed window with set/map, remove elements outside window
4. **Integer overflow**: Use `long` for sum calculations when needed
5. **Empty window check**: Handle edge cases when k > array length

## Complexity Analysis

**Fixed Window:**
- Time: O(n) - single pass with constant work per element
- Space: O(1) for simple aggregates, O(k) with set/deque

**Variable Window:**
- Time: O(n) - each element added once, removed once
- Space: O(1) for simple aggregates, O(n) worst case with set/map

## Practice Problems in This Package

1. `MaxSum.java` - Maximum sum of size k subarray
2. `MinSum.java` - Minimum sum of size k subarray
3. `MinSubArray.java` - Minimum length subarray with sum >= target
4. `FirstNegativeNumber.java` - First negative in every window of size k
5. `Duplicates.java` - Contains duplicate within distance k
6. `ContainsDuplicate.java` - Contains almost duplicate (TreeSet approach)