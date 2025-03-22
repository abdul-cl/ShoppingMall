import java.util.*;

class DSAQueue {
    private DSAGraphVertex[] queue;
    private int count;
    private int DEFAULT_CAPACITY = 100;

    public DSAQueue() {
        queue = new DSAGraphVertex[DEFAULT_CAPACITY];
        count = 0;
    }

    public DSAQueue(int maxCapacity) {
        queue = new DSAGraphVertex[maxCapacity];
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == DEFAULT_CAPACITY;
    }

    public void enqueue(DSAGraphVertex value) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full. Cannot add more elements.");
        }
        queue[count++] = value;
    }

    public DSAGraphVertex dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty. Cannot dequeue.");
        }
        DSAGraphVertex frontVal = queue[0];
        System.arraycopy(queue, 1, queue, 0, count - 1);
        count--;
        return frontVal;
    }

    public DSAGraphVertex peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }
        return queue[0];
    }
}
