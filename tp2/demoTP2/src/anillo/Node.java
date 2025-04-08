package anillo;

import java.util.List;
import java.util.ArrayList;

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
    public abstract Node remove(Integer size);
    public abstract Node next();
    public abstract Object current();

}

class EmptyNode extends Node {

    public EmptyNode( ) {
        super( null );

    }

    public Node add(Object cargo) {
        return new SingleNode( cargo );

    }

    public Node remove(Integer size) {
        return this;
    }

    public Node next() {
        throw   new RuntimeException("Empty ring");
    }

    public Object current() {
        throw new RuntimeException("Empty ring");
    }
}

class SingleNode extends Node{

    public SingleNode(Object cargo){
        super ( cargo );
        this.next = this;
        this.prev = this;
    }

    public Node add(Object cargo){
        Node myNode = new MultiNode( this.data );
        Node newNode = new MultiNode ( cargo );

        myNode.next = newNode;
        myNode.prev = newNode;
        newNode.next = myNode;
        newNode.prev = myNode;

        return newNode;
        
    }

    public Node remove(Integer size){
        return new EmptyNode();
    }

    public Node next(){
        return this;
    }

    public Object current(){
        return this.data;
    }
}

class MultiNode extends Node {


    public MultiNode(Object cargo) {
        super( cargo );
    }

    public Node add(Object cargo) {
        Node newNode = new MultiNode( cargo );
        newNode.prev = this.prev;
        newNode.next = this;
        this.prev.next = newNode;
        this.prev = newNode;
        return newNode;
    }

    public Node remove(Integer size) {

        List<Node> auxList = new ArrayList<>();

        this.next.prev = this.prev;
        this.prev.next = this.next;

        Integer index = Math.min(size - 1, 1);
        Node auxNode = new SingleNode(this.next.data);

        auxList.add(auxNode);
        auxList.add(this.next);
        
        return auxList.get(index);
    }

    public Node next() {
        return this.next;
    }

    public Object current() {
        return this.data;
    }
}





