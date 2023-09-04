package n7rider.chapter2.linked_lists;

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
 * Too simple, but couldn't think of a better solution
 *
 *  * After comparing with solution:
 * I had half the answer. Floyd's algorithm is used but they call it fast-runner/slow-runner approach.
 * The solution uses it to find the loop mathematically.
 * e.g., A - B - C - D - E - F - G - H - I - J - K - H
 *                                   |
 * With fast-runner, slow-runner, both will meet at H (i = 8).
 * When 1x entered the loop start (we don't know where yet):
 *   1x was at loop start
 *   2x was k nodes away from loop start i.e., loop_size - k nodes away from 1x [Solution-Pt-1: Move back
 *     k nodes or move forward loop_size-k nodes to find starting point, but we just don't know when this started]
 *   2x catches up with 1x at 1 node/turn, so will catch up in loop_size - k iterations
 * So at collision point (we know where this is):
 *   1x has moved loop_size-k nodes away from start, so is k nodes away from start
 *   What's another collision point? The start of the entire list, so it must also be k nodes away from start of loop
 *      [This is what I understood from the book]. Another way to figure this is, when 1x moves k nodes to reach start,
 *      2x moves 2k nodes i.e., k nodes away from the loop_start. Remember, 2x was also at k nodes away from loop_start when
 *      1x reached loop start first. So, moving k nodes from collision point or start would get us to the point.
 *   So, move k nodes from collision point, you get start of loop
 *   We don't know k, but we know 2 points - moving k nodes from collision point gets us there, moving k nodes from
 *      start also gets us there. So if we do both together, we'll meet at this point. Use the equality to stop here.
 *
 * I didn't know this derivation of Floyd's algorithm, so let's try it again.
 */
public class Question2_8 {

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
        n1.then(2).then(2).then(three).then(4).then(5).then(three);

        Node loopBeginning = findLoopBeginning(n1);
        assertEquals(three, loopBeginning);

    }

    static Node findLoopBeginning(Node n1) {
        Set<Node> nodeSet = new HashSet<>();

        while(n1 != null) {
            if(nodeSet.contains(n1)) {
                return n1;
            } else {
                nodeSet.add(n1);
            }
            n1 = n1.next;
        }

        return null;
    }
}


/**
 * Algorithm
 *
 * Find cycle - Use Floyd's algorithm
 * Find where loop is:
 * Simplest way - put all entries to set, see where it is repeated
 * Inside the loop - you can't find the starting point - it's like a circle
 */