package models;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeBuffer {
    private Lock lock;
    private Condition condVariable;
    private ConcurrentLinkedQueue<Integer> queue;
    // isOpen represents a flag that shows if the buffer is still open for reading.
    private boolean isOpen = true;

    public SafeBuffer() {
        this.lock = new ReentrantLock();
        this.condVariable = lock.newCondition();
        queue = new ConcurrentLinkedQueue<>();
    }

    public void add(int number) {
        // adds a number to the queue and signals the consumer that the queue is not empty
        lock.lock();
        queue.add(number);

        condVariable.signal();
        lock.unlock();
    }

    public Integer pop() {
        lock.lock();
        try {
            while (true) {
                if (!queue.isEmpty()) {
                    Integer number = queue.poll();
                    lock.unlock();
                    return number;
                }
                if (!isOpen) {
                    lock.unlock();
                    return null;
                }
                // if the queue is empty, the thread enters a disabled state until it is signaled by other thread
                // when the thread enters the disabled state, it releases the lock
                // and when it returns from the await state, it will re-acquire the lock.
                condVariable.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        lock.lock();
        isOpen = false;
        condVariable.signalAll();
        lock.unlock();
    }
}
