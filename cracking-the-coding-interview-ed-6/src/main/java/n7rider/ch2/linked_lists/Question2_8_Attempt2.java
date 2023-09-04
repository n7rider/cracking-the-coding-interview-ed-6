package n7rider.ch2.linked_lists;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * 2.8
 * Loop Detection: Given a circular linked list, implement an algorithm that returns the node at the
 * beginning of the loop.
 * DEFINITION
 * Circular linked list: A (corrupt) linked list in which a node's next pointer points to an earlier node, so
 * as to make a loop in the linked list.
 * EXAMPLE
 * Input:
 * A -> B -> C -> D -> E -> C [the same C as earlier]
 * Output:
 * C
 * Hints: #50, #69, #83, #90
 * ---
 *  * Post-run observations:
 * It wouldn't meet at loop start for me.
 *
 *  * After comparing with solution:
 * Found that fast and slow start at 1, 1 | then 2, 3 etc. My code goes for 1, 2 | 2, 4 etc. Mine works
 * for cycle detection but nodes don't meet for finding loop start.
 */
public class Question2_8_Attempt2 {

    static class Node {
         int val;
         Node next;

         Node(int val) {
             this.val = val;
         }

         Node then(int val) {
             this.next = new Node(val);
             return this.next;
         }

        Node then(Node nextNode) {
            this.next = nextNode;
            return this.next;
        }
    }

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node three = new Node(3);
        n1.then(2).then(three).then(4).then(5).then(6).then(three);

        Node loopBeginning = findLoopBeginning(n1);
        assertEquals(three, loopBeginning);

    }

    static Node findLoopBeginning(Node n1) {
        // Skipping null checks
        Node slow = n1;
        Node fast = n1;

        do {
            System.out.println("Current positions " + slow.val + " " + fast.val);
            slow = slow.next;
            fast = fast.next.next;
        } while (slow != fast);
        System.out.println("Collision point found at " + slow.val);

        Node slow2 = n1;
        while(slow2 != slow) {
            slow = slow.next;
            slow2 = slow2.next;
            System.out.println("Current positions " + slow.val + " " + slow2.val);
        }
        System.out.println("Loop start detected at " + slow.val);
        return slow;
    }
}


/**
 * Algorithm
 *
 * Find cycle - Use Floyd's algorithm
 * Find where loop is:
 *   Use Floyd's algorithm to find collision point
 *   Keep a pointer there, keep another at list start
 *   Go 1x until you find a point of intersection. That's the loop start
 */