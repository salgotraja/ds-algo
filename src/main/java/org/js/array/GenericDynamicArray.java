package org.js.array;

import java.util.*;
import java.util.function.Predicate;

public class GenericDynamicArray<E> implements Iterable<E> {
    private Object[] arr;
    private int capacity;
    private int currentSize;
    private static final int DEFAULT = 10;
    private double growthFactor = 1.5;

    public GenericDynamicArray() {
        arr = new Object[DEFAULT];
        capacity = DEFAULT;
        currentSize = 0;
    }

    public GenericDynamicArray(int initialCapacity) {
        if (initialCapacity > 0) {
            arr = new Object[initialCapacity];
            capacity = initialCapacity;
        } else if (initialCapacity == 0) {
            arr = new Object[DEFAULT];
            capacity = DEFAULT;
        } else {
            throw new IllegalArgumentException("Invalid initial capacity");
        }
        currentSize = 0;
    }

    public void setGrowthFactor(double factor) {
        if (factor <= 1.0) throw new IllegalArgumentException("Growth factor must be > 1.0");
        this.growthFactor = factor;
    }

    public void add(E element) {
        if (currentSize == capacity) growArr();
        arr[currentSize++] = element;
    }

    public void add(int position, E element) {
        if (position < 0 || position > currentSize) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
        if (currentSize == capacity) growArr();
        for (int i = currentSize - 1; i >= position; i--) {
            arr[i + 1] = arr[i];
        }
        arr[position] = element;
        currentSize++;
    }

    public void addFirst(E element) { add(0, element); }
    public void addLast(E element) { add(element); }

    private void growArr() {
        int newCapacity = (int) (capacity * growthFactor);
        if (newCapacity == capacity) newCapacity++; // safeguard
        arr = Arrays.copyOf(arr, newCapacity);
        capacity = newCapacity;
    }

    @SuppressWarnings("unchecked")
    public E remove() {
        if (currentSize == 0) throw new IllegalStateException("Array is empty");
        E removed = (E) arr[--currentSize];
        arr[currentSize] = null;
        return removed;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if (index < 0 || index >= currentSize) throw new IndexOutOfBoundsException("Invalid position");
        E removed = (E) arr[index];
        for (int i = index; i < currentSize - 1; i++) arr[i] = arr[i + 1];
        arr[--currentSize] = null;
        return removed;
    }

    public boolean remove(E ele) {
        int index = indexOf(ele);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    public void removeFirst() {
        if (currentSize == 0) throw new NoSuchElementException("Array is empty");
        remove(0);
    }

    public void removeLast() {
        if (currentSize == 0) throw new NoSuchElementException("Array is empty");
        remove(currentSize - 1);
    }

    public boolean removeIf(Predicate<? super E> filter) {
        boolean removedAny = false;
        for (int i = 0; i < currentSize; i++) {
            @SuppressWarnings("unchecked") E e = (E) arr[i];
            if (filter.test(e)) {
                remove(i--);
                removedAny = true;
            }
        }
        return removedAny;
    }

    public int indexOf(E ele) {
        for (int i = 0; i < currentSize; i++) {
            if (Objects.equals(arr[i], ele)) return i;
        }
        return -1;
    }

    public int lastIndexOf(E ele) {
        for (int i = currentSize - 1; i >= 0; i--) {
            if (Objects.equals(arr[i], ele)) return i;
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= currentSize) throw new IndexOutOfBoundsException("Invalid index");
        return (E) arr[index];
    }

    public void set(int index, E ele) {
        if (index < 0 || index >= currentSize) throw new IndexOutOfBoundsException("Invalid index");
        arr[index] = ele;
    }

    @SuppressWarnings("unchecked")
    public E getFirst() {
        if (currentSize == 0) throw new NoSuchElementException("Array is empty");
        return (E) arr[0];
    }

    @SuppressWarnings("unchecked")
    public E getLast() {
        if (currentSize == 0) throw new NoSuchElementException("Array is empty");
        return (E) arr[currentSize - 1];
    }

    public boolean contains(E ele) { return indexOf(ele) >= 0; }
    public boolean isEmpty() { return currentSize == 0; }
    public int size() { return currentSize; }
    public int getCapacity() { return capacity; }

    public void clear() {
        for (int i = 0; i < currentSize; i++) arr[i] = null;
        currentSize = 0;
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > capacity) {
            arr = Arrays.copyOf(arr, minCapacity);
            capacity = minCapacity;
        }
    }

    public void trimToSize() {
        if (currentSize < capacity) {
            arr = Arrays.copyOf(arr, currentSize);
            capacity = currentSize;
        }
    }

    public Object[] toArray() { return Arrays.copyOf(arr, currentSize); }

    @SuppressWarnings("unchecked")
    public E[] toArray(E[] a) {
        if (a.length < currentSize) {
            return (E[]) Arrays.copyOf(arr, currentSize, a.getClass());
        }
        System.arraycopy(arr, 0, a, 0, currentSize);
        if (a.length > currentSize) a[currentSize] = null;
        return a;
    }

    public GenericDynamicArray<E> subList(int from, int to) {
        if (from < 0 || to > currentSize || from > to) throw new IndexOutOfBoundsException("Invalid subList range");
        GenericDynamicArray<E> sub = new GenericDynamicArray<>(to - from);
        for (int i = from; i < to; i++) {
            @SuppressWarnings("unchecked") E e = (E) arr[i];
            sub.add(e);
        }
        return sub;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int cursor = 0;
            public boolean hasNext() { return cursor < currentSize; }
            @SuppressWarnings("unchecked")
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (E) arr[cursor++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < currentSize; i++) {
            sb.append(arr[i]);
            if (i < currentSize - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
