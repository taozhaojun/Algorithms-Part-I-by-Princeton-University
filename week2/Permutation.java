import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            count++;
            if (count > k) {
                int r = StdRandom.uniform(count);
                if (r < k) {
                    queue.dequeue();
                    queue.enqueue(item);
                }
            } else {
                queue.enqueue(item);
            }
        }
        for (String item : queue) {
            StdOut.println(item);
        }
    }
}