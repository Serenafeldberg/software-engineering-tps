package anillo;

public abstract class Node {

    Object data;
    Node next;
    Node prev;

    public Node( Object data ) {
        this.data = data;
        next = null;
        prev = null;
    }

    public abstract Node add (Object cargo);
    public abstract Node remove();
    public abstract Node next();
    public abstract Object current();

}

class EmptyNode extends Node {
    public EmptyNode( ) {
        super( null );

    }

    public Node add(Object cargo) {
        Node one = new NodeOne( cargo );
        one.prev = one;
        one.next = one;

        return one;
    }

    public Node remove() {
        return this;
    }

    public Node next() {
        throw   new RuntimeException("Empty ring");
    }

    public Object current() {
        throw new RuntimeException("Empty ring");
    }
}

class NodeOne extends Node {
    public NodeOne(Object cargo) {
        super( cargo );
    }

    public Node add(Object cargo) {
        Node newNode = new NodeOne( cargo );
        newNode.prev = this.prev;
        newNode.next = this;
        this.prev.next = newNode;
        this.prev = newNode;
        return newNode;
    }

    public Node remove() {
        if (this.prev == this) {
            return new EmptyNode();
        }
        this.next.prev = this.prev;
        this.prev.next = this.next;
        return this.next;
    }

    public Node next() {
        return this.next;
    }

    public Object current() {
        return this.data;
    }
}





