package n7rider.chapter2.linked_lists;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 2.6
 * Palindrome: Implement a function to check if a linked list is a palindrome.
 * Hints:#5, #13, #29, #61, #101
 * ---
 *  * Post-run observations:
 *  Runs in O(N) time but creates O(N) stack too
 *  I'm using static variables to make it simpler, but we can return and use returned value too.
 *
 *  * After comparing with solution:
 *  - Solution uses recursion too.
 *  - Can use stack structure (if allowed) to store last half, then compare with the first half
 * 
 */
public class Question2_6 {

    public static void main(String[] args) {
        // a b c d c b a
        Node n1 = new Node('a');
        n1.then('b').then('c').then('d').then('c').then('b').then('a');
        assertTrue(isPalindrome(n1));

        Node n2 = new Node('a');
        n2.then('b').then('c').then('d').then('d').then('c').then('b').then('a');
        assertTrue(isPalindrome(n2));

        Node n3 = new Node('c');
        n3.then('b').then('a');
        assertFalse(isPalindrome(n3));
    }

    static class Node {
        char val;
        Node next;

        public Node(char val) {
            this.val = val;
        }

        public Node then(char val) {
            this.next = new Node(val);
            return this.next;
        }
    }

    private static Node findMidNode(Node head) {
        // TODO Add null check, I'm assuming the list is at least 1 node long
        Node x1 = head;
        Node x2 = head;
        while(true) {
            if(x2.next != null && x2.next.next != null) {
                x2 = x2.next.next;
            } else {
                return x1;
            }

            if(x1.next == null) {
                return x1;
            } else {
                x1 = x1.next;
            }
        }
    }

    static Node forwardNode = null;
    static Node midNode = null;
    static boolean isPalindrome = true;
    static boolean reachedMidPoint = false;
    static boolean isPalindrome(Node head) {
        // TODO Add null check
        forwardNode = head;
        midNode = findMidNode(head);
        isPalindrome = true;
        reachedMidPoint = false;

        getNextAndCheck(head);

        return isPalindrome;
    }

    private static void getNextAndCheck(Node n) {
        if (n != null) {
            getNextAndCheck(n.next);
            System.out.println("Node is " + n.val);
            if (!reachedMidPoint && isPalindrome) {
                checkNodes(n);
            }
        }
    }

    private static void checkNodes(Node currentNode) {
        System.out.println("Check nodes start. currNode " + currentNode.val + " | forwardNode " + forwardNode.val);
        if(currentNode.val == forwardNode.val) {
            if(forwardNode.val == midNode.val) {
                System.out.println("Reached mid point");
                reachedMidPoint = true;
            }
            forwardNode = forwardNode.next;
            System.out.println("Good so far. Moving to next node");
        } else {
            System.out.println("IsPalindrome is false");
            isPalindrome = false;
        }
    }

}

/**
 * Algorithm
 *
 * ex: a b c d c b a
 * Simplest: Rearrange list in reverse and check with original (Needs a duplicate list though)
 *
 * Characteristics of palindrome:
 * - A middle, then equally distributed on either side
 *
 * Recursion returning results backward, then compare with a regular iterator?
 *
 * Node forwardNode = head;
 * Node backwardNode;
 *
 * checkInReverse(n):
 *   if n.next = null
 *      return null
 *   else
 *      Node temp = getNext(n.next)
 *      // A downside, can't abandon all the stacks immediately. have to run through them
 *      boolean stillPalindrome = doCheck(temp, forward)
 *      if false
 *         break;
 *
 * Node lastNode = checkInReverse(n)
 *
 * doCheck(temp, forward)
 *    if(forward == temp)
 *       forward = forward.next
 *       return true
 *    else
 *       return false
 *
 *
 * Find middle node and stop there - we don't need to compare all the way
 * a b c d d c b a
 *
 */