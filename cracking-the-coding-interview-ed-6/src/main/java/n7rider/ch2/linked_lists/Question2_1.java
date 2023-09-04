package n7rider.ch2.linked_lists;


import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 2.1
 * Remove Dupes! Write code to remove duplicates from an unsorted linked list.
 * FOLLOW UP
 * How would you solve this problem if a temporary buffer is not allowed?
 * Hints: #9, #40
 * ---
 * Post-run observations:
 * - With buffer, runtime is O(n) with additional storage of O(n)
 * - Without buffer, runtime is O(n^2), but additional storage is O(1)
 *
 * After comparing with solution:
 * - Same as mine
 */
public class Question2_1 {
    public static void main(String[] args) {

        Node n1 = new Node("a");
        n1.then(new Node("b")).then(new Node("c")).then(new Node("a"));

        prettyPrint(deleteDupes(n1));

        Node n2 = new Node("a");
        n2.then(new Node("a"));
        prettyPrint(deleteDupes(n2));

        Node n3 = null;
        prettyPrint(deleteDupes(n3));

        Node n4 = new Node("a");
        n4.then(new Node("b")).then(new Node("c")).then(new Node("d"));
        prettyPrint(deleteDupes(n4));


        n1 = new Node("a");
        n1.then(new Node("b")).then(new Node("c")).then(new Node("a"));
        prettyPrint(deleteDupesWithoutBuffer(n1));

        n2 = new Node("a");
        n2.then(new Node("a"));
        prettyPrint(deleteDupesWithoutBuffer(n2));

        n3 = null;
        prettyPrint(deleteDupesWithoutBuffer(n3));

        n4 = new Node("a");
        n4.then(new Node("b")).then(new Node("c")).then(new Node("d"));
        prettyPrint(deleteDupesWithoutBuffer(n4));
    }

    static class Node {
        String val;
        Node next;

        public Node(String val) {
            this.val = val;
        }

        public Node then(Node next) {
            this.next = next;
            return next;
        }
    }

    public static Node deleteDupes(Node head) {
        if(head == null || head.next == null) {
            return head;
        }

        Set<String> uniqueElements = new HashSet<>();
        Node n = head;

        uniqueElements.add(n.val);

        while (n != null && n.next != null) {
            if(!uniqueElements.add(n.next.val)) {
                n.next = n.next.next;
            }
            n = n.next;
        }

        return head;
    }

    public static Node deleteDupesWithoutBuffer(Node head) {
        Node n = head;
        Node temp;
        while(n != null) {
            temp = n;
            while (temp != null && temp.next != null) {
                if(n.val.equals(temp.next.val)) {
                    temp.next = temp.next.next;
                }
                temp = temp.next;
            }
            n = n.next;
        }
        return head;
    }

    public static void prettyPrint(Node n) {
        int c = 0;
        StringBuilder out = new StringBuilder();
        while(n != null) {
            if(c++ == 0) {
                out.append(n.val);
            } else {
                out.append(" > ").append(n.val);
            }
            n = n.next;
        }
        System.out.println(out);
    }

}
/**
 * Approach:
 * ---
 * with temporary buffer:
 *
 * add head to dupe_check
 * while n.next != null
 *   temp = n.next
 *   if(dupe_check) == true
 *     n.next = temp.next
 *   n = n.next
 *
 * dupe_checK:
 *   // use boolean[52] for alphabets
 *   // use boolean[x] for small number limit
 *   // use set for everything else
 *
 * -- try using
 *
 * without temporary buffer:
 * go with O(n^2) with bubble sort
 *
 * while n != null
 *    temp = n
 *    while temp.next != null
 *       if n.val equals temp.next.val
 *          temp.next = temp.next.next
 *       temp = temp.next
 *    n = n.next
 *

 */