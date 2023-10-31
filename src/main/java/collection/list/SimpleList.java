package collection.list;

import java.util.*;

public class SimpleList<T> implements List<T> {
    private final int START_CAPACITY = 10;
    private final double CAPACITY_MULTIPLIER = 1.5;
    private final double CAPACITY_DIVIDER = 1.5;

    private Object[] array = new Object[START_CAPACITY];
    private int size = 0;

    private boolean removeNotReduceCapacity = false;

    public SimpleList() {
    }

    public SimpleList(Collection<? extends T> c) {
        addAll(c);
    }

    public SimpleList(boolean removeNotReduceCapacity) {
        this.removeNotReduceCapacity = removeNotReduceCapacity;
    }

    public SimpleList(Collection<? extends T> c, boolean removeNotReduceCapacity) {
        this.removeNotReduceCapacity = removeNotReduceCapacity;
        addAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T object : c) {
            add(object);
        }
        return true;
    }

    @Override
    public void clear() {
        Arrays.fill(array, null);
        array = new Object[START_CAPACITY];
        size = 0;
    }

    public boolean add(T object) {
        checkFullness();
        array[size] = object;
        size++;
        return true;
    }

    public T get(int index) {
        validIndex(index);
        return (T) array[index];
    }

    public T remove(int index) {
        validIndex(index);
        T removed = (T) array[index];
        array[index] = null;
        shiftClose(index);
        if (removeNotReduceCapacity) checkFullness();
        return removed;
    }

    public int size() {
        return size;
    }

    public T set(int index, T element) {
        validIndex(index);
        T oldValue = (T) array[index];
        array[index] = element;
        return oldValue;
    }

    public void add(int index, T element) {
        validIndex(index);
        checkFullness();
        moveApart(index);
        array[index] = element;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void checkFullness() {
        if (size == array.length) {
            expand();
        } else if (array.length > START_CAPACITY && size < (array.length / (CAPACITY_DIVIDER * 2)) ) {
            reduce();
        }
    }

    private void validIndex(int index) {
        if (index < 0 || index > size - 1) throw new RuntimeException(String.format("index out: index = %d, list bound = 0-%d", index, size-1));
    }

    private void expand() {
        Object[] newArray = new Object[(int) (array.length * CAPACITY_MULTIPLIER)];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    private void reduce() {
        Object[] newArray = new Object[(int) (array.length / CAPACITY_DIVIDER)];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    private void shiftClose(int foldIndex) {
        if (foldIndex == array.length - 1) {
            size--;
            return;
        }
        if (array[foldIndex] != null) throw new RuntimeException("fold index != null");
        System.arraycopy(array, foldIndex + 1, array, foldIndex, size - 1 - foldIndex);
        array[size - 1] = null;
        size--;
    }

    private void moveApart(int extensionIndex) {
        if (extensionIndex == array.length - 1) {
            expand();
        }
        System.arraycopy(array, extensionIndex, array, extensionIndex + 1, size - 1 - extensionIndex + 1);
        array[extensionIndex] = null;
        size++;
    }



    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                return (T) array[index++];
            }
        };
    }


    @Override
    public Object[] toArray() {
        if (size == 0) return new Object[0];
        Object[] result = new Object[size];
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        System.arraycopy(toArray(), 0, a, 0, a.length);
        return a;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("remove(object)");
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("contains");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("containsAll");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("addAll");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("indexOf");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("lastIndexOf");
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("listIterator");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("listIterator");
    }

    @Override
    public SimpleList<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList");
    }

    @Override
    public String toString() {
        int iMax = size - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; ; i++) {
            builder.append(String.valueOf(array[i]));
            if (i == iMax)
                return builder.append(']').toString();
            builder.append(", ");
        }
    }
}
