package n7rider.ch2.linked_lists;

import static org.junit.Assert.*;

/**
 * 2.7
 * Intersection: Given two (singly) linked lists, determine if the two lists intersect. Return the
 * intersecting node. Note that the intersection is defined based on reference, not value. That is, if the kth
 * node of the first linked list is the exact same node (by reference) as the jth node of the second
 * linked list, then they are intersecting.
 * Hints:#20, #45, #55, #65, #76, #93, #111, #120, #129
 * ---
 *  * Post-run observations:
 *  Runs in O(N) + O(xN) time to detect intersection. Finding intersecting node too takes O(xN) time
 *  The xN is for Floyd's algorithm running through a couple of times to detect cycles.
 *
 *  * After comparing with solution:
 *  The solution found intersecting point by finding the point at which the values are not the same.
 *  If nodes match by reference, it is always a Y intersection, and in them, the values are going to be the same
 *  after a point. Just keep going backwards using recursion, until you find the point of non-equality.
 *  That is much simpler than my solution.
 *  TODO Attempt this.
 *
 * 
 */
public class Question2_7_Attempt2 {

    static class Node {
        int val;
        Node next;
        Node(int val) {
            this.val = val;
        }

        Node then(Node next) {
            this.next = next;
            return this.next;
        }
    }

    static void prettyPrint(Node n) {
        // Skip null check
        System.out.print(n.val);
        while(n.next != null) {
            System.out.print(" > " + n.next.val);
            n = n.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3); //----
        Node four = new Node(4);
        Node five = new Node(5);
        Node six = new Node(6); // _|
        Node seven = new Node(7);

        Node n1 = one;
        n1.then(two).then(five).then(six).then(seven);

        Node n2 = three;
        n2.then(four).then(five).then(six).then(seven);

        Node intersectingNode = findIntersectingNode(n1, n2);
        assertNotNull(intersectingNode);
        assertEquals(5, intersectingNode.val);

        Node preA = new Node(100);
        Node a = new Node(101);
        Node b = new Node(102);
        Node c = new Node(103);
        Node d = new Node(104);
        Node n3 = preA;
        n3.then(a).then(b);

        Node n4 = c;
        n4.then(d);

        Node intersectingNode2 = findIntersectingNode(n3, n4);
        assertNull(intersectingNode2);
    }

    static Node findIntersectingNode(Node n1, Node n2) {

        isSolved = false;

        Node n1PreHead = new Node(-1);
        n1PreHead.next = n1;
        Node n2PreHead = new Node(-2);
        n2PreHead.next = n2;

//        prettyPrint(n1PreHead);
//        prettyPrint(n2PreHead);
        System.out.println();

        return findFirstIntersectingNode(n1, n1PreHead, n2, n2PreHead);

    }

    static boolean isSolved = false;

    static Node findFirstIntersectingNode(Node n1Head, Node n1Current, Node n2Head, Node n2Current) {

        if (n1Current != null && n2Current != null) {
            Node previous = findFirstIntersectingNode(n1Head, n1Current.next, n2Head, n2Current.next);
            System.out.println("Init: " + n1Current.val + " " + n2Current.val + " " + isSolved + " | " + (previous != null ? previous.val : null));

            if(!isSolved) {
                // prettyPrint(n1Head);
                // prettyPrint(n2Head);
                System.out.println();

                n2Current.next = null;
                n1Current.next = n2Head;

                if (floydCheck(n1Head)) {
                    return n1Current;
                } else {
                    isSolved = true;
                    return previous;
                }
            } else {
              return previous;
            }
        } else {
            System.out.println("Returning null");
            return null;
        }
    }

    static boolean floydCheck(Node n1Head) {
        Node x1 = n1Head;
        Node x2 = x1 != null && x1.next != null ? x1.next : null;

        while(x1 != null && x2 != null) {
            if(x1.equals(x2)) {
                System.out.println("Floyd check returned true");
                return true;
            }
            x1 = x1.next != null ? x1.next : null;
            x2 = x2.next != null && x2.next.next != null ? x2.next.next : null;
        }

        System.out.println("Floyd check returned false");
        return false;
    }
}

/**
 * Algorithm
 *
 * Connect the lists i.e., a.end to b.start
 * Decided to use Floyd's algorithm to detect cycle
 * If cycle exists,
 *  set b.start - b.start.next.
 *  Connect the lists  i.e., a.end to b.start
 *  Continue until cycle is not detected
 *  The previous one is where they were linked
 *
 * But this will not work for a Y style intersection. We'll have a loop going till the end.
 * Therefore, removing from the tail is the best move. It works for both possibilities - X style and Y style
 *
 * Connect the lists i.e., a.end to b.start
 * Decided to use Floyd's algorithm to detect cycle
 *  If cycle exists,
 *    set a.tail = a.tail.prev
 *    Connect the lists i.e., a.end to b.start
 *    Decided to use Floyd's algorithm to detect cycle
 *    Repeat
 *  The last node to run cycle is where they intersect first
 *
 *  So, the first time - detecting cycle is a special case of the if loop where set a.tail = a.tail
 *
 *  Pseudocode:
 *
 *  Node firstIntersectingNode;
 *  findIntersectingNode(n1head, n1, n2):
 *     if (n1 != null)
 *       n1 = n1.next
 *       findIntersectingNode(n1head, n1, n2)
 *       n1.next = n2.head
 *       if(floydCheck(n1head)):
 *          firstIntersectingNode =  n1;
 *       else
 *          return null
 *
 *  floydcheck(n1head):
 *     x1 = n1head.next OR null
 *     x2 = x1.next OR null
 *     while(x1 != null && x2 != null)
 *       if x1 == x2
 *         return true
 *       x1 = n1head.next OR null
 *       x2 = x1.next OR null
 *
 *     return false
 *
 *
 *  Realized while coding:
 *  - If lists always intersect by reference, it'll always be a Y merge, and never be an X merge
 *  - In Y merge, connecting n1.prev = n2.head is not enough (since they shared a common path,
 *  this assignment will keep the chain running). The only way to stop is to truncate both lists
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */