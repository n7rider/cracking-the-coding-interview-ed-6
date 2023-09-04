package n7rider.ch2.linked_lists;

import static org.junit.Assert.assertNull;

/**
 * 2.4
 * Partition: Write code to partition a linked list around a value x, such that all nodes less than x come
 * before all nodes greater than or equal to x. If x is contained within the list, the values of x only need
 * to be after the elements less than x (see below). The partition element x can appear anywhere in the
 * "right partition"; it does not need to appear between the left and right partitions.
 * EXAMPLE
 * Input:
 * Output:
 * 3 -> 5 -> 8 -> 5 -> 10 -> 2 -> 1 [partition= 5]
 * 3 -> 1 -> 2 -> 10 -> 5 -> 5 -> 8
 * Hints: #3, #24
 * ---
 * Post-run observations:
 *
 *
 *
 * After comparing with solution:
 * - Same as the solution
 *
 */
public class Question2_4_Attempt2 {

    public static void main(String[] args) {
        Node n1 = new Node(3);
        n1.then(5).then(8).then(5).then(10).then(2).then(1);

        Node head1 = partition(n1, 5);
        prettyPrint(head1);
    }

    static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }

        public Node then(int val) {
            this.next = new Node(val);
            return this.next;
        }
    }

    static void prettyPrint(Node head) {
        Node node = head;
        System.out.print(node.val); // Skip null check
        while(node.next != null) {
            node = node.next;
            System.out.print(" > " + node.val);
        }
        System.out.println();
    }

    static Node partition(Node head, int pVal) {
        Node preHeadLeft = new Node(-1);
        Node leftCurrent = preHeadLeft;
        Node preHeadRight = new Node(-1);
        Node rightCurrent = preHeadRight;

        Node n = head;
        while(n != null) {
            if(n.val < pVal) {
                leftCurrent.next = n;
                leftCurrent = leftCurrent.next;
            } else {
                rightCurrent.next = n;
                rightCurrent = rightCurrent.next;
            }
            n = n.next;
        }

        leftCurrent.next = preHeadRight.next;
        rightCurrent.next = null;
        return preHeadLeft.next;
    }
}

/**
 * Algorithm:
 *
 * Have 2 nodes representing two partitions
 * Add to either based on comparison
 * Connect both partitions and return the first
 *
 */