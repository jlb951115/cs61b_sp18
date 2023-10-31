public class ArrayDeque<T> {
    private T[] item;
    private int size;
    private int nextfirst;
    private int nextlast;
    private final int REF = 16;

    private int plusone(int x) {
        if (x == item.length - 1) {
            return 0;
        } else {
            return x + 1;
        }
    }

    private int minusone(int x) {
        if (x == 0) {
            return item.length - 1;
        } else {
            return x - 1;
        }
    }

    private void resize(int cap) {
        T[] a = (T[]) new Object[cap];
        int index = nextfirst;
        for (int i = 0; i < size; i++) {
            a[i] = item[plusone(index)];
            index = plusone(index);
        }
        item = a;
        nextfirst = item.length - 1;
        nextlast = size;
    }

    public ArrayDeque() {
        nextfirst = 0;
        nextlast = 1;
        size = 0;
        item = (T[]) new Object[8];
    }

    public void addFirst(T x) {
        if (size == item.length) {
            resize(2 * size);
        }
        item[nextfirst] = x;
        size++;
        nextfirst = minusone(nextfirst);
    }

    public void addLast(T x) {
        if (size == item.length) {
            resize(2 * size);
        }
        item[nextlast] = x;
        size++;
        nextlast = plusone(nextlast);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = item[plusone(nextfirst)];
        nextfirst = plusone(nextfirst);
        size--;
        if (item.length > REF && ((size / item.length) < 0.25)) {
            resize(2 * size);
        }
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T x = item[minusone(nextlast)];
        nextlast = minusone(nextlast);
        size--;
        if (item.length > REF && ((size / item.length) < 0.25)) {
            resize(2 * size);
        }
        return x;
    }

    public T get(int index) {
        return item[(nextfirst + index + 1) % item.length];
    }

    public void printDeque() {
        int index = nextfirst;
        for (int i = 0; i < size; i++) {
            System.out.print(this.get(i));
            System.out.print(" ");
        }
    }
}
