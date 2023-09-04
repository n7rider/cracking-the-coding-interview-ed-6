package n7rider.chapter2.linked_lists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * 2.2
 * Return Kth to Last: Implement an algorithm to find the kth to last element of a singly linked list.
 * Hints:#8, #25, #41, #67, #126
 * ---
 *  * Post-run observations:
 *
 *  * After comparing with solution:
 * - I considered `Use two iterators at 1x and kx speeds? Doesn't give any advantage` but didn't consider
 * it much. Besides, my solution is too simple to consider too.
 * - Considering this now
 *
 *  After comparing with solution:
 *  - Same as mine
 */
public class Question2_2 {
    public static void main(String[] args) {
        Node n1 = new Node("1");
        n1.then("2").then("3");

        assertEquals("2", nMinusK(n1, 2).val);
        assertEquals("1", nMinusK(n1, 3).val);
        assertNull(nMinusK(n1, 4));
        assertEquals("3", nMinusK(n1, 1).val);

        assertEquals("2", nMinusK_v2(n1, 2).val);

        prettyPrint(n1);
        assertEquals("1", nMinusK_v2(n1, 3).val);
        assertNull(nMinusK_v2(n1, 4));
        assertEquals("3", nMinusK_v2(n1, 1).val);

    }

    static class Node {
        String val;
        Node next;

        public Node(String val) {
            this.val = val;
        }

        public Node then(String next) {
            this.next = new Node(next);
            return this.next;
        }
    }

    public static void prettyPrint(Node head) {
        // Skip null check
        System.out.print(head.val);
        Node n = head.next;
        while(n != null) {
            System.out.print(" > " + n.val);
            n = n.next;
        }
    }

    public static Node nMinusK(Node head, int k) {
        int count = 0;
        Node n = head;
        while(n != null) {
            count++;
            n = n.next;
        }

        if(count < k) {
            return null;
        }

        n = head;
        // Assuming k's index starts from 1 (not 0)
        for(int i = 0; i < count - k; i++) {
            n = n.next;
        }

        return n;
    }

    public static Node nMinusK_v2(Node head, int k) {
        Node n2 = head;
        for(int i = 0; i < k; i++) {
            if(n2 == null) {
                return null;
            }
            n2 = n2.next;
        }

        Node n1 = head;
        while(n2 != null) {
            n2 = n2.next;
            n1 = n1.next;
        }

        return n1;
    }

}

/**
 * Algorithm:
 *
 * Simplest way - Run through once to find size, then stop at n-k position to get the element
 * Make it better by:
 * - Store all - Needs O(n) storage
 * - At every position, check if you can go k+1 positions from there. Else, return current value
 * (This would O(nk) or (n^2) again)
 * - Use two iterators at 1x and kx speeds? Doesn't give any advantage
 * - Go with the simplest way
 *
 * run-once-and-stop-at-n-k:
 * while n!= null
 *    c+= 1
 *    n = n.next
 *
 * n = head
 * stop = c-k
 * for i = 1 to c-k
 *   n = n.next
 * return n
 */

/**
 * Algorithm 2
 * - Use two iterators at 1x speed, but spaced k nodes apart
 *
 * Start i1 at head
 * Iterate i2 for k times, and start there
 * while i2 != null
 *   i2 = i2.next
 *   i1 = i1.next
 * return i1
 */