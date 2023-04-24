import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // resize the underlying array
    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        if (size >= 0) System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot enqueue null item");
        }
        if (size == items.length) {
            resize(2 * items.length);
        }
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue from an empty queue");
        }
        int index = StdRandom.uniform(size);
        Item item = items[index];
        items[index] = items[--size];
        items[size] = null; // to avoid loitering
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot sample from an empty queue");
        }
        int index = StdRandom.uniform(size);
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int index;
        private Item[] shuffledItems;

        public RandomizedQueueIterator() {
            index = 0;
            shuffledItems = (Item[]) new Object[size];
            System.arraycopy(items, 0, shuffledItems, 0, size);
            System.arraycopy(items, 0, shuffledItems, 0, size);
            StdRandom.shuffle(shuffledItems);
        }

        public boolean hasNext() {
            return index < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            return shuffledItems[index++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println("IsEmpty: " + rq.isEmpty());
        System.out.println("Size: " + rq.size());
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        System.out.println("IsEmpty: " + rq.isEmpty());
        System.out.println("Size: " + rq.size());

        // Test enqueue and sample
        System.out.println("Sample: " + rq.sample());
        rq.enqueue(6);
        rq.enqueue(7);
        rq.enqueue(8);
        rq.enqueue(9);
        rq.enqueue(10);
        System.out.println("Sample: " + rq.sample());

        // Test iterator
        for (int i : rq) {
            System.out.println(i);
        }

        // Test dequeue
        System.out.println("Dequeue: " + rq.dequeue());
        System.out.println("Dequeue: " + rq.dequeue());

        // Test size after dequeue
        System.out.println("Size: " + rq.size());

        // Test iterator after dequeue
        for (int i : rq) {
            System.out.println(i);
        }
    }
}
