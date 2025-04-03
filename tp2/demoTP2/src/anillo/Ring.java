package anillo;

import java.util.List;
import java.util.ArrayList;

public class Ring {

    private Node current;

    public Ring() {
        current = null;

    }

    public Ring next() {
        if (current == null) {
            throw   new RuntimeException("Empty ring");
        }
        current = current.next;
        return this;
    }

    public Object current() {
        if (current == null) {
            throw new RuntimeException("Empty ring");
        }
        return current;
    }

    public Ring add( Object cargo ) {
        Node newNode = new Node( cargo );
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
        current = newNode;
        return this;
    }

    public Ring remove() {
        Node removed = current;
//        current.prev.next = current.next;
//        current.next.prev = current.prev;
        current = current.next;
        current.prev = removed.prev;
        removed.prev.next = current;
        removed = null;

        return this;
    }
}
