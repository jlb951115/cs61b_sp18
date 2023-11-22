package synthesizer;

public interface BoundedQueue<T> extends Iterable<T> {
    public int capacity();
    public int fillCount();
    public void enqueue(T x);
    public T dequeue();
    public T peek();
    default boolean isEmpty() {
        return this.fillCount() == 0;
    }
    default boolean isFull() {
        return this.fillCount() == this.capacity();
    }

}
