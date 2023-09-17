package n7rider.ch3.stacks_queues;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Question3_5 {

    /**
     * 3.5
     * Sort Stack: Write a program to sort a stack such that the smallest items are on the top. You can use
     * an additional temporary stack, but you may not copy the elements into any other data structure
     * (such as an array). The stack supports the following operations: push, pop, peek, and is Empty.
     * 
     * ---
     * Post-run observations:
     * Used insertion sort
     *
     * After comparing with solution:
     * Same
     */

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    static class Stack {
        Node head;

        void push(Node n) {
            var temp = head;
            head = n;
            head.next = temp;
        }

        Node pop() {
            var temp = head;
            if(head != null) {
                head = head.next;
            }
            return temp;
        }

        Node peek() {
            return head;
        }
    }

    static class SortStack {
        Stack s;
        Stack tempStack;

        SortStack() {
            this.s = new Stack();
            this.tempStack = new Stack();
        }

        void push(Node n) {
            Node head = s.peek();
            while(null != head && head.val < n.val) {
                tempStack.push(s.pop());
            }
            s.push(n);
            while (tempStack.peek() != null) {
                s.push(tempStack.pop());
            }
        }

        Node pop() {
            return s.pop();
        }
    }

    public static void main(String[] args) {
        SortStack s1 = new SortStack();
        s1.push(new Node(10));
        s1.push(new Node(5));
        s1.push(new Node(3));
        s1.push(new Node(7));

        assertEquals(3, s1.pop().val);
        assertEquals(5, s1.pop().val);
        assertEquals(7, s1.pop().val);
        assertEquals(10, s1.pop().val);
        assertNull(s1.pop());
    }
}

/**
 * Do something like an insertion sort?
 *
 * e.g., 10 5 3 -> 7
 * Keep popping to temp stack until spot is found, then push back to stack
 *
 * Algorithm:
 * push()
 * if s.peek > n
 *   just push
 * else
 *   while s.peek <= n
 *     push to temp stack
 *     insert n
 *     push items from temp to s
 *
 */