import java.util.*;
public class DSAListNode
{

    public Object value;
    public DSAListNode next;


    public DSAListNode(Object inValue) {
        value = inValue;
        next = null;
    }


    public Object getValue() {
        return value;
    }


    public void setValue(Object inValue) {
        value = inValue;
    }



    public DSAListNode getNext() {
        return next;
    }


    public void setNext(DSAListNode newNext) {
        next = newNext;
    }
}
