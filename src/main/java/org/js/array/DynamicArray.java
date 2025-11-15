package org.js.array;

public class DynamicArray {
    private int arr[];
    private int capacity;
    private int currentSize;
    private static final int DEFAULT = 10;

    public DynamicArray() {
        arr = new int[DEFAULT];
        capacity = DEFAULT;
        currentSize = 0;
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity > 0) {
            arr = new int[initialCapacity];
            capacity = initialCapacity;
        } else if (initialCapacity == 0) {
            arr = new int[DEFAULT];
            capacity = DEFAULT;
        } else {
            throw new IllegalArgumentException("Invalid initial capacity");
        }
        currentSize = 0;
    }


    public void add(int data) {
        if (currentSize == capacity) {
            growArr();
        }
        arr[currentSize++] = data;
    }

    public void add(int position, int data) {
        if (position < 0 || position > currentSize) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
        if (currentSize == capacity) {
            growArr();
        }
        for (int i = currentSize - 1; i >= position; i--) {
            arr[i + 1] = arr[i];
        }
        arr[position] = data;
        currentSize++;
    }


    private void growArr() {
        int[] oldArr = arr;
        arr = new int[2 * capacity];
        /*for (int i=0; i<capacity; i++) { arr[i] = oldArr[i]; }*/
        System.arraycopy(oldArr, 0, arr, 0, capacity);
        capacity *= 2;
    }

    public int remove(int position) {
        if (position < 0 || position >= currentSize) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
        int removed = arr[position];
        for (int i = position; i < currentSize - 1; i++) {
            arr[i] = arr[i + 1];
        }
        currentSize--;
        return removed;
    }

    public int remove() {
        if (currentSize == 0) {
            throw new IllegalStateException("Array is empty");
        }

        int removed = arr[currentSize - 1];
        currentSize--;

        return removed;
    }

    public int get(int i) {
        if (i < 0 || i >= currentSize) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return arr[i];
    }

    public void set(int index, int value) {
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        arr[index] = value;
    }

    public boolean contains(int value) {
        return indexOf(value) >= 0;
    }

    public int indexOf(int value) {
        for (int i=0; i < currentSize; i++) {
            if (arr[i] == value) return i;
        }
        return -1;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public void clear () {
        currentSize = 0;
    }

    public int removeAt(int index) {
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        int removed = arr[index];

        for (int i=index; i<currentSize; i++) {
            arr[i] = arr[i+1];
        }
        currentSize--;

        return removed;
    }

    public boolean removeValue(int value) {
        int index = indexOf(value);

        if (index == -1) return false;
        removeAt(index);
        return true;
    }

    public void trimToSize() {
        if (currentSize < capacity) {
            int[] newArr = new int[currentSize];
            System.arraycopy(arr, 0, newArr, 0, currentSize);
            arr = newArr;
            capacity = currentSize;
        }
    }

    public int [] toArray() {
        int[] copy = new int[currentSize];
        System.arraycopy(arr, 0, copy, 0, currentSize);
        return copy;
    }

    public int getCapacity() {
        return capacity;
    }

    public int size() {
        return currentSize;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i=0; i<currentSize; i++) {
            sb.append(arr[i]);
            if (i < currentSize - 1) sb.append(", ");
        }

        sb.append("]");
        return sb.toString();
    }
}
