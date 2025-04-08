package anillo;

import java.util.List;
import java.util.ArrayList;

public class Ring {

    private Node current;
    private Integer size;

    public Ring() {
        current = new EmptyNode();
        size = 0;

    }

    public Ring next() {
        current = current.next();
        return this;
    }

    public Object current() {
        return current.current();
    }

    public Ring add( Object cargo ) {
        size +=1;
        current = current.add(cargo);
        return this;
    }

    public Ring remove() {
        size -=1;
        current = current.remove(size);
        return this;
    }
}
