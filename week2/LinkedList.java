public class LinkedList {
    Node head;

    public LinkedList() {
        this.head = null;
    }

    public void add(int value) {
        if (head == null) {
            head = new Node(value);
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node(value);
        }
    }
    public static void inversePrint(Node head) {
        if (head == null) {
            return;
        }

        // Recursive call to the next node in the list
        inversePrint(head.next);

        // Print the current node value after the recursive calls

        System.out.print(head.value + "->");

    }
    public static void main(String[] args) {
        // Create a linked list and add elements 1, 2, 3, 4, and 5
        LinkedList myList = new LinkedList();
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);

        // Call the inversePrint method
        Node buffer = myList.head;
        inversePrint(buffer);
    }
}