public class LinkedListDeque<T> {
    private  class Tnode {
        private T item;
        private Tnode next;
        private Tnode prev;
        public Tnode(T item, Tnode next, Tnode prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Tnode sential;
    private int size;
    private Tnode last;

    public LinkedListDeque() {
        sential = new Tnode(null, null, null);
        size = 0;
        last = null;
    }

    private LinkedListDeque(Tnode sential, int size, Tnode last) {
        this.sential = sential;
        this.size = size;
        this.last = last;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T item) {
        if (size == 0) {
            sential.next = new Tnode(item, null, sential);
            last = sential.next;
        } else {
            sential.next = new Tnode(item, sential.next, sential);
            sential.next.next.prev = sential.next;
        }
        size++;
        return;
    }

    public void addLast(T item) {
        if (size == 0) {
            addFirst(item);
            return;
        } else {
            last.next = new Tnode(item, null, last);
            last = last.next;
        }
        size++;
        return;
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        T item = null;
        if (size == 0) {
            return item;
        } else if (size == 1) {
            item = sential.next.item;
            sential.next = sential.next.next;
            last = null;
        } else {
            item = sential.next.item;
            sential.next = sential.next.next;
            sential.next.prev = sential;
        }
        size--;
        return item;
    }

    public T removeLast() {
        T item = null;
        if (size == 0) {
            return item;
        } else if (size == 1) {
            return removeFirst();
        } else {
            item = last.item;
            last = last.prev;
            last.next = null;
        }
        size--;
        return item;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            int i = 0;
            Tnode p = sential;
            while (i < index) {
                p = p.next;
                i++;
            }
            return p.next.item;
        }
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        } else if (index == 0) {
            return sential.next.item;
        } else {
            LinkedListDeque<T> L = new LinkedListDeque<>(sential.next, size - 1, last);
            return L.getRecursive(index - 1);
        }
    }

    public void printDeque() {
        Tnode L = sential.next;
        int i = 0;
        while (L != null) {
            System.out.print(L.item);
            System.out.print(" ");
            L = L.next;
        }
    }
}
