package n7rider.chapter2.linked_lists;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * 2.7
 * Intersection: Given two (singly) linked lists, determine if the two lists intersect. Return the
 * intersecting node. Note that the intersection is defined based on reference, not value. That is, if the kth
 * node of the first linked list is the exact same node (by reference) as the jth node of the second
 * linked list, then they are intersecting.
 * Hints:#20, #45, #55, #65, #76, #93, #111, #120, #129
 * ---
 * * Post-run observations:
 * Adding to set and checking is slightly complex but it is needed for lists of uneven size. A simpler way is to
 * find the length of the list and compensate i.e., skip d turns in smaller list n2 where d = n1-n2
 *
 * * After comparing with solution:
 * The solution finds the length while iterating the first time, then uses the difference to start at the right place
 * (same distance to end). Then it goes each step until we find an intersecting node.
 * Not much difference performance-wise (excluding recursion, but the solution is simpler).
 *
 * If you can club along something with the initial run, do it and go for the simpler way. In other words, see if you
 * can find a way to incorporate the simpler way.
 *
 * UPDATE: Updated based on solution. Much simpler.
 */
public class Question2_7_Attempt3 {

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }

        Node then(Node n) {
            this.next = n;
            return n;
        }
    }

    public static void main(String[] args) {
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3); //----
        Node four = new Node(4);
        Node five = new Node(5);
        Node anotherFive = new Node(55);
        Node six = new Node(6); // _|
        Node seven = new Node(7);

        Node n1 = one;
        n1.then(two).then(six).then(seven);

        Node n2 = three;
        n2.then(four).then(five).then(anotherFive).then(six).then(seven);

        Node intersectingNode = findIntersectingNode(n1, n2);
        assertNotNull(intersectingNode);
        assertEquals(6, intersectingNode.val);

        Node preA = new Node(100);
        Node a = new Node(101);
        Node b = new Node(102);
        Node c = new Node(103);
        Node d = new Node(104);
        Node e = new Node(105);
        Node n3 = preA;
        n3.then(a);

        Node n4 = b;
        n4.then(c).then(d).then(e);

        Node intersectingNode2 = findIntersectingNode(n3, n4);
        assertNull(intersectingNode2);
    }


    static Node findIntersectingNode(Node n1, Node n2) {
        init();
        return findIfIntersectsAndIntersectingNode(n1, n2);
//        recursivelyFindIntersectingNode(n1, n2);
//        return lastIntersectingNode;
    }

    static Node lastIntersectingNode = null;
    static boolean notIntersecting = false;
    static Set<Node> set = new HashSet<>();

    static void init() {
        set = new HashSet<>();
        lastIntersectingNode = null;
        notIntersecting = false;
    }


    static class Result {
        int length;
        Node tail;
        Result(int length, Node tail) {
            this.length = length;
            this.tail = tail;
        }
    }
    static private Node findIfIntersectsAndIntersectingNode(Node n1, Node n2) {
        Result n1Result = findTailAndLength(n1);
        Result n2Result = findTailAndLength(n2);

        if(n1Result.tail != n2Result.tail) {
            System.out.println("Lists don't intersect");
            return null;
        }

        return findIntersectionByIteration(n1, n2, n1Result.length - n2Result.length);
    }

    static private Node findIntersectionByIteration(Node n1, Node n2, int diff) {
        if(diff > 0) {
            n1 = getListPosAfterOffsetting(n1, diff);
            System.out.println("After offsetting, n1 is " + n1.val);
        } else {
            n2 = getListPosAfterOffsetting(n2, -diff);
            System.out.println("After offsetting, n2 is " + n2.val);
        }

        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
        }
        System.out.println("End of iteration. Intersection at: " + n1.val);
        return n1;
    }

    static private Node getListPosAfterOffsetting(Node n, int diff) {
        for(int i = 0; i < diff; i++) {
            n = n.next;
        }
        return n;
    }

    static private Result findTailAndLength(Node n) {
        int length = 0;
        Node tail = null;
        while(n != null) {
            length++;
            tail = n;
            n = n.next;
        }
        return new Result(length, tail);
    }

    static private void recursivelyFindIntersectingNode(Node n1, Node n2) {
        boolean bothListsEnd = n1.next == null && n2.next == null;
        if (!bothListsEnd) {
            n1 = n1.next == null ? n1 : n1.next;
            n2 = n2.next == null ? n2 : n2.next;
            recursivelyFindIntersectingNode(n1, n2);

            if(notIntersecting) {
                return;
            }

            if (n1 == n2) {
                lastIntersectingNode = n1;
                System.out.println("Intersection found at n1 with val: " + n1.val);
            } else {
                // Add to set since the lists could be uneven in size.
                // We can find size first, and offset, or do this.
                addToSetAndCheck(n1, n2);

                if (set.size() >= 3) {
                    System.out.println("Not intersecting anymore. Last intersected at " + lastIntersectingNode.val);
                    notIntersecting = true;
                }
            }
        } else {
            System.out.print("Reached end of lists. ");
            System.out.println("n1: " + n1.val + " | n2: " + n2.val);
            if (n1 == n2) {
                lastIntersectingNode = n1;
            } else {
                notIntersecting = true;
                System.out.println("Lists don't intersect");
            }
        }
    }

    private static void addToSetAndCheck(Node n1, Node n2) {
        addToSetAndCheck(n1);
        addToSetAndCheck(n2);
    }

    private static void addToSetAndCheck(Node n1) {
        if (set.contains(n1)) {
            set.remove(n1);
            lastIntersectingNode = n1;
            System.out.println("Removed n1 with val: " + n1.val);
        } else {
            set.add(n1);
        }
    }


}

/**
 * Algorithm
 * <p>
 * The lists intersect by reference, so node.val, node.next will be the same. So, the list must be a Y shaped
 * join with the merging starting midway and going till the end.
 * <p>
 * Find if they intersect? If they intersect, they'll surely meet at end.
 * Find point of intersection? Keep retracing from back, and find the point at which they diverge.
 * <p>
 * Rec_iterate(l1, l2):
 *   bothEnd = l1.next == null && l2.next == null
 *   if(!bothEnd)
 *     l1 = l1.next OR l1
 *     l2 = l2.next OR l2
 *     rec_iterate(l1, l2)
 *   else
 *     // End
 *     if l1 != l2 return false OR null
 *     <p>
 *     if l1 == l2
 *       store last known intersection
 *     else
 *       return last known intersection
 *   end iteration
 */