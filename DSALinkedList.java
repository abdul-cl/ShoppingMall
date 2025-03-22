import java.util.*;

public class DSALinkedList implements Iterable
{

   private DSAListNode head;
    public DSALinkedList() 
    {
        head = null;
    }

    public void insertFirst(Object DSAGraphVertex) 
    {
        DSAListNode newNd = new DSAListNode(DSAGraphVertex);

        if (isEmpty()) 
        {
            head = newNd;
        } else 
        {
            newNd.setNext(head);
            head = newNd;
        }
    }
    
    

    public void insertLast(Object newValue) 
    {
        DSAListNode newNd = new DSAListNode(newValue);

        if (isEmpty()) 
        {
            head = newNd;
        } 
        else 
        {
            DSAListNode currNd = head;
            while (currNd.getNext() != null) 
            {
                currNd = currNd.getNext();
            }
            currNd.setNext(newNd);
        }
    }
    

    public boolean isEmpty() 
    {
        return (head == null);
    }

    public Object peekFirst() 
    {
        if (isEmpty()) 
        {
            throw new IllegalStateException("List is Empty");
        } 
        else 
        {
            return head.getValue();
        }
    }

    public Object peekLast() 
    {
        if (isEmpty()) 
        {
            throw new IllegalStateException("List is Empty");
        } 
        else 
        {
            DSAListNode currNd = head;
            while (currNd.getNext() != null) 
            {
                currNd = currNd.getNext();
            }
            return currNd.getValue();
        }
    }

    public Object removeFirst() 
    {
        if (isEmpty()) 
        {
            throw new IllegalStateException("List is Empty");
        } 
        else 
        {
            Object nodeValue = head.getValue();
            head = head.getNext();
            return nodeValue;
        }
    }

    public Object removeLast() 
    {
        if (isEmpty()) 
        {
            throw new IllegalStateException("List is Empty");
        } 
        else if (head.getNext() == null) 
        {
            Object nodeValue = head.getValue();
            head = null;
            return nodeValue;
        } 
        else 
        {
            DSAListNode currNd = head;
            DSAListNode prevNd = null;

            while (currNd.getNext() != null) 
            {
                prevNd = currNd;
                currNd = currNd.getNext();
            }

            prevNd.setNext(null);
            return currNd.getValue();
        }
    }
    
    public void remove(Object value) {
        if (isEmpty()) {
            throw new IllegalStateException("List is Empty");
        } else if (head.getValue().equals(value)) {
            head = head.getNext();
        } else {
            DSAListNode currNd = head;
            DSAListNode prevNd = null;
            while (currNd != null && !currNd.getValue().equals(value)) {
                prevNd = currNd;
                currNd = currNd.getNext();
            }
            if (currNd == null) {
                throw new IllegalArgumentException("Value not found in list");
            }
            prevNd.setNext(currNd.getNext());
        }
    }
    
    // Add this method to the DSALinkedList class
public boolean containsLocation(String location) {
    DSAListNode currNode = head;
    while (currNode != null) {
        String[] shopInfo = ((String) currNode.getValue()).split(",");
        if (shopInfo.length >= 4 && shopInfo[3].equals(location)) {
            return true;
        }
        currNode = currNode.getNext();
    }
    return false;
}

    public Iterator iterator()
    {
        return new DSALinkedListIterator(this);
    }
    public class DSALinkedListIterator implements Iterator {
	private DSAListNode prevNode; // Keep track of the previous node
    private DSAListNode currentNode;
    private DSAListNode iterNext;

    public DSALinkedListIterator(DSALinkedList theList) {
        iterNext = theList.head;
    }

    public boolean hasNext() {
        return (iterNext != null);
    }

    public Object next() {
        if (iterNext == null) {
            throw new NoSuchElementException();
        }
        prevNode = currentNode;
        currentNode = iterNext;
        iterNext = iterNext.getNext();
        return currentNode.getValue();
    }

    public void remove() {
        if (prevNode == null) {
            throw new IllegalStateException("Cannot remove before calling next()");
        }
        if (prevNode == head) {
            head = head.getNext(); // Remove the head node
        } else {
            prevNode.setNext(currentNode.getNext()); // Remove the current node
        }
         currentNode = null; // Reset currentNode after removal
        prevNode = null; // Reset prevNode after removal
    }

        public int getCount() {
    		int count = 0;
   		 DSAListNode currNode = head;
    		while (currNode != null) {
        		count++;
        		currNode = currNode.getNext();
    }
    return count;
}


    }
    
}
