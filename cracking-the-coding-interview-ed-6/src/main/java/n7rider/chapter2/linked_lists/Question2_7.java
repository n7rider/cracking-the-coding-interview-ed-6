package n7rider.chapter2.linked_lists;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 2.7
 * Intersection: Given two (singly) linked lists, determine if the two lists intersect. Return the
 * intersecting node. Note that the intersection is defined based on reference, not value. That is, if the kth
 * node of the first linked list is the exact same node (by reference) as the jth node of the second
 * linked list, then they are intersecting.
 * Hints:#20, #45, #55, #65, #76, #93, #111, #120, #129
 * ---
 *  * Post-run observations:
 *  Floyd's algorithm is much better to detect cycle but it can't find the place of intersection.
 *
 *  * After comparing with solution:
 *  Looks like I misunderstood the question. They talk about paths intersecting and remain intersected
 *  I know from data structures lessons that truncation is the key, but did it at the wrong place.
 *  Was truncating inside the cycle but it would just go on, and we modify the cycle. Rather, I need
 *  to truncate at the tail of cycle. Re-do and try to do both together in a method now.
 * 
 */
public class Question2_7 {

    public static void main(String[] args) {

        Node x12 = new Node(3);
        Node n1 = new Node(1);
        n1.then(2).then(x12).then(4).then(5);

        Node n2 = new Node(12);
        n2.then(10).then(8).then(x12).then(-1);

        Node cycleFoundNode = findIntersectingNodeIfIntersects(n1, n2);
        System.out.println("Lists intersect at " + cycleFoundNode);
        assertNotNull(cycleFoundNode);
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

        public Node then(Node node) {
            this.next = node;
            return this.next;
        }
    }

    private static Node isCycleFound(Node head1, Node head2) {
        Node tail1 = head1;
        while(tail1.next != null) {
            tail1 = tail1.next;
        }
        tail1.next = head2;

        // TODO Add null check for head1, head1.next
        Node iter1x = head1;
        Node iter2x = head1.next;
        boolean iter1xNotAtStart = true;

        while(iter1xNotAtStart && iter2x.next != null && iter2x.next.next != null) {
            if(iter1x == iter2x) {
                System.out.println("Cycle found at " + iter1x.val);
                return iter1x;
            } else {
                iter1x = iter1x.next;
                iter2x = iter2x.next.next;
            }
            if(iter1x == head1) {
                iter1xNotAtStart = false;
            }
        }

        System.out.println("No cycle found");
        return null;
    }

    public static Node findIntersectingNodeIfIntersects(Node head1, Node head2) {
        Node cycleFoundAt = isCycleFound(head1, head2);
        if(cycleFoundAt != null) {
            Node intersectingNode = findIntersectingNode(cycleFoundAt, head1, head2);
            return intersectingNode;
        } else {
            return null;
        }
    }

    private static Node findIntersectingNode(Node cycleFoundNode, Node head1, Node head2) {

        Node skippedNode;
        do {
            skippedNode = cycleFoundNode.next;
            cycleFoundNode.next = cycleFoundNode.next.next;
            cycleFoundNode = findIntersectingNodeIfIntersects(head1, head2);
        }
        while(cycleFoundNode != null);
        return skippedNode;
    }


}

/**
 * Algorithm
 *
 * Simplest - Check each in l1 vs each in l2. O(N^2) time
 * l1 - 6 [Intersect at 5th element]
 * l2 - 10 [Intersect at 8th element]
 *
 * Find if interesects?
 * Floyd cycle algorithm
 * Navigate to l2's end
 * l2.tail = l1.head
 * If they intersect, we get a cycle
 *
 * We always check by reference, so we can store to find when we are back at a visited node
 *
 * Start at 1x from l1
 * Start at 2x from l2
 * while l1 has not reached l1.head again
 *    if l1 == l2
 *       return true
 * return false
 *
 * Return intersecting node?
 * Simplest - Add each .next to set, iterate both until a dupe is found
 *
 * Idea:
 * At point of intersection, do curr.next = curr.next.next
 * If you still keep intersecting, it's dispensable
 * If no, the node jumped over is the intersecting node
 *
 * 1 2 3 4 5
 * 12 10 8 3 -1
 *
 * 1 2 3 4 5 12 10 8
 *     ^-----------|
 *
 * 1 2 3 4 5 12 8
 *     ^--------|
 *
 * Start at 8 - 8,3 | 3,5 | 4,8 | 5,4 | 12,12
 * Met, so 10 is not
 *
 * 1 2 3 4 5 12
 *     ^------|
 * Start at 3 - 3,4 | 4,12 | 5,4 | 12,12
 * Met, so 8 is not
 *
 * 1 2 4 5 12
 *     ^---|
 * Start at 4 - 4,5 | 5,4, 12,12
 *
 * Change, cut some but always start from head1, so repeat algo
 *
 */