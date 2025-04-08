package anillo;

import java.util.Stack;

public class Ring {

    private Link current;
    private Stack<Link> stack;

    public Ring() {
        current = new NullLink();
        stack = new Stack<Link>();
        stack.push(current);
    }

    public Ring next() {
        Link top = stack.peek();
        current = top.next (current);
        return this;
    }

    public Object current() {
        Link top = stack.peek();
        return top.current (current);
    }

    public Ring add( Object cargo ) {
        Link top = stack.peek();
        current = top.add (cargo, current);
        stack.push(current);
        return this;
    }

    public Ring remove() {
        Link top = stack.peek();
        current = top.remove (current);
        Link removed = stack.pop();
        return this;
    }
}
