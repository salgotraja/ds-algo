# Data Structures & Algorithms

A comprehensive Java implementation of fundamental data structures and algorithm solutions, organized by topic and pattern.

## About

This repository contains:
- Custom implementations of core data structures (Dynamic Arrays, Linked Lists, HashMaps)
- Solutions to common algorithm problems
- Pattern-based organization (Sliding Window, Two Pointer, K-Sum)
- Detailed notes and explanations for key algorithms

## Prerequisites

- Java 24
- Maven (Maven wrapper included)

## Getting Started

### Build the Project

```bash
./mvnw clean compile
```

### Run Tests

**All tests:**
```bash
./mvnw test
```

**Specific test class:**
```bash
./mvnw test -Dtest=DynamicArrayTest
```

**Single test method:**
```bash
./mvnw test -Dtest=DynamicArrayTest#testAdd
```

## Project Structure

```
src/main/java/org/js/
├── array/              # Array fundamentals
│   ├── practice/       # Common array problems
│   ├── sw/            # Sliding window patterns
│   └── tp/            # Two pointer patterns
├── string/            # String manipulation
├── linkedlist/        # Linked list implementations
│   └── practice/      # Linked list problems
└── lru/              # LRU cache implementations
```

## Key Topics Covered

### Data Structures
- Dynamic Arrays (generic and non-generic)
- Singly Linked Lists
- HashMaps with chaining
- LRU Cache

### Algorithm Patterns
- **Sliding Window**: Subarray/substring optimization
- **Two Pointer**: Left/right pointer technique
- **K-Sum**: Generalized sum finding (TwoSum → ThreeSum → FourSum → KSum)
- **Fast/Slow Pointer**: Cycle detection, middle node finding

### Common Problems
- Array: rotation, searching in rotated arrays, prefix sums
- String: longest/smallest substring, anagrams, unique characters
- Linked List: reversal, merging, cycle detection, palindrome checking

## Dependencies

- JUnit 5 - Testing framework
- AssertJ - Fluent assertions
- Mockito - Mocking framework
- Guava - Utility libraries

## Learning Resources

Check the `.md` files in specific packages for detailed algorithm explanations and common pitfalls:
- `src/main/java/org/js/array/sw/Notes.md`
- `src/main/java/org/js/string/smallest-substring.md`
