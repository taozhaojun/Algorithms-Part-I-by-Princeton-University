import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;         // size of the deque
    private Node first, last; // first and last nodes of the deque

    private class Node {
        private Item item;   // item stored in the node
        private Node prev;   // pointer to the previous node
        private Node next;   // pointer to the next node
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldfirst;
        if (isEmpty()) {
            last = first;
        } else {
            oldfirst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = last.item;
        last = last.prev;
        size--;
        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // inner class that implements the Iterator interface
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("Hello");
        deque.addLast("World");
        deque.addFirst("Goodbye");
        deque.addLast("Moon");
        deque.removeFirst();
        deque.removeLast();
        for (String s : deque) {
            System.out.println(s);
        }
        System.out.println("Size: " + deque.size());
        System.out.println("IsEmpty: " + deque.isEmpty());
        System.out.println("Elements: ");
        for (String s : deque) {
            System.out.println(s);
        }
        // Testing corner cases
        Deque<String> emptyDeque = new Deque<String>();
        try {
            emptyDeque.addFirst(null);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException caught successfully");
        }

        try {
            emptyDeque.addLast(null);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException caught successfully");
        }

        try {
            emptyDeque.removeFirst();
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException caught successfully");
        }

        try {
            emptyDeque.removeLast();
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException caught successfully");
        }

        try {
            Iterator<String> it = emptyDeque.iterator();
            it.next();
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException caught successfully");
        }

        try {
            Iterator<String> it = emptyDeque.iterator();
            it.remove();
        } catch (UnsupportedOperationException e) {
            System.out.println("UnsupportedOperationException caught successfully");
        }
    }
}
