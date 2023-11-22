package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T>  {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        this.rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
    }



    private int plusone(int x) {
        if (x + 1 == this.capacity) {
            return 0;
        } else {
            return x + 1;
        }
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = plusone(last);
        fillCount++;
        return;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T x = rb[first];
        rb[first] = null;
        first = plusone(first);
        fillCount--;
        return x;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return rb[first];
        }
    }




    private class ArrayRingIterator implements Iterator<T> {
        private int wizPos;
        public ArrayRingIterator() {
            wizPos = first;
        }

        public boolean hasNext() {
            return wizPos != last;
        }

        public T next() {
            T x = rb[wizPos];
            wizPos = plusone(wizPos);
            return x;
        }
    }
    public Iterator<T> iterator() {
        return new ArrayRingIterator();
    }
}
