package anillo;

import java.util.List;
import java.util.ArrayList;

public class Ring {

    private Node current;

    public Ring() {
        current = new EmptyNode();

    }

    public Ring next() {
        current = current.next();
        return this;
    }

    public Object current() {
        return current.current();
    }

    public Ring add( Object cargo ) {
        current = current.add(cargo);
        return this;
    }

    public Ring remove() {
        current = current.remove();
        return this;
    }
}
