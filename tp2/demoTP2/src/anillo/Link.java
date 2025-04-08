package anillo;

public abstract class Link {

    Object data;
    Link next;
    Link prev;

    public Link( Object data ) {
        this.data = data;
        next = null;
        prev = null;
    }

    public abstract Link add (Object cargo, Link link);
    public abstract Link remove(Link link);
    public abstract Link next(Link link);
    public abstract Object current(Link link);

}

class NullLink extends Link {
    public NullLink( ) {
        super( null );

    }

    public Link add(Object cargo, Link link) {
        Link one = new MyLink( cargo );
        one.prev = one;
        one.next = one;

        return one;
    }

    public Link remove(Link link) {
        return this;
    }

    public Link next(Link link) {
        throw   new RuntimeException("Empty ring");
    }

    public Object current(Link link) {
        throw new RuntimeException("Empty ring");
    }
}

class MyLink extends Link {
    public MyLink(Object cargo) {
        super( cargo );
    }

    public Link add(Object cargo, Link link) {
        Link newNode = new MyLink( cargo );
        newNode.prev = link.prev;
        newNode.next = link;
        link.prev.next = newNode;
        link.prev = newNode;
        return newNode;
    }

    public Link remove(Link link) {
        link.next.prev = link.prev;
        link.prev.next = link.next;
        return link.next;
    }

    public Link next(Link link) {
        return link.next;
    }

    public Object current(Link link) {
        return link.data;
    }
}