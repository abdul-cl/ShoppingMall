import java.util.*;

class stack {
    private DSAGraphVertex stack[];
    private static int count;
    private int DEFAULT_CAPACITY = 100;

    public stack() {
        stack = new DSAGraphVertex[DEFAULT_CAPACITY];
        count = 0;
    }

    public stack(int maxCapacity) {
        stack = new DSAGraphVertex[maxCapacity];
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public static boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == stack.length;
    }
    public DSAGraphVertex peek() {
    if (isEmpty()) {
        throw new NoSuchElementException("Stack is empty");
    }
    return stack[count - 1];
}



    public void push(DSAGraphVertex value) {
        if (isFull()) {
            throw new IllegalStateException("Stack is full. Cannot push more elements.");
        } else {
            stack[count] = value;
            count++;
        }
    }

    public DSAGraphVertex pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty. Cannot pop.");
        } else {
            DSAGraphVertex topVal = top();
            count--;
            return topVal;
        }
    }

    public DSAGraphVertex top() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        } else {
            return stack[count - 1];
        }
    }
}
