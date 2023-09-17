package n7rider.ch3.stacks_queues;

import java.nio.file.AtomicMoveNotSupportedException;

import static org.junit.Assert.assertEquals;

public class Question3_2 {

    /**
     * 3.2
     * Stack Min: How would you design a stack which, in addition to push and pop, has a function min
     * which returns the minimum element? Push, pop and min should all operate in 0(1) time.
     * ---
     * Post-run observations:
     * Had a look at the initial steps of the solution for a hint.
     * All operations run at O(1)
     *
     * Lesson: Visualize with examples if you're stuck. The scenarios I wrote helped, but I did it after
     * getting the hint
     *
     *
     * After comparing with solution:
     * Similar to Mine
     */
    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    static class Stack {
        Node stack; // Using Integer.MAX_VALUE is simpler but slightly off-convention
        Node min;

        void push(Node newEntry) {
            if(stack == null) {
                stack = newEntry;
                min = newEntry;
            } else {
                Node temp = stack;
                stack = newEntry;
                newEntry.next = temp;

                if(newEntry.val < min.val) {
                    temp = min;
                    min = newEntry;
                    newEntry.next = temp;
                }
            }
        }

        Node pop() {
            if(stack == null) {
                return null;
            } else {
                Node toReturn = stack;
                stack = stack.next;

                if(toReturn == min) {
                    min = min.next;
                }
                return toReturn;
            }
        }

        Node min() {
            return min;
        }
    }

    public static void main(String[] args) {
        Stack s1 = new Stack();
        s1.push(new Node(10));
        s1.push(new Node(9));
        s1.push(new Node(8));

        assertEquals(8, s1.min().val);
        assertEquals(8, s1.pop().val);
        assertEquals(9, s1.min().val);
        assertEquals(9, s1.pop().val);
        assertEquals(10, s1.min().val);
        assertEquals(10, s1.pop().val);
        assertEquals(null, s1.min());
        assertEquals(null, s1.pop());

        Stack s2 = new Stack();
        s2.push(new Node(8));
        s2.push(new Node(9));
        s2.push(new Node(10));

        assertEquals(8, s2.min().val);
        assertEquals(10, s2.pop().val);
        assertEquals(8, s2.min().val);
        assertEquals(9, s2.pop().val);
        assertEquals(8, s2.min().val);
        assertEquals(8, s2.pop().val);
        assertEquals(null, s2.min());
        assertEquals(null, s2.pop());
    }
}

/**
 * Simplest way
 *  - Use another state variable min that keeps lowest? It'll stop after lowest is popped out
 *  - No data structure can do this even heap takes O(log n)
 *  - Have a linked list for min - inserts each element to start or end ? Iteration not easy after a while
 *
 *  Hint: Book says minimum doesn't change often, so consider keeping min at each state?
 *
 *  Expanding on my third idea,
 *  Use linked list for min - but smaller elements just get added to the tail?
 *
 * Scenarios:
 *  - Push n items with smallest at the first, we update min the first time, then do nothing. SIMPLE
 *
 *  - Push n items with each getting smaller, we add min to linked list each time.
 *  - On each pop, if it equals min, do min = min.next
 *
 * Pseudocode:
 * Create one-way linked list for stack
 * Create one-way linked list for min
 * Each push:
 * - creates node, becomes the new head
 * - if smaller than min, becomes the new head
 *
 * Each pop:
 * - navigate to the next element in stack LL
 * - if popped = min, navigate to next element. Else nothing
 *
 * Min:
 * - Return head of min LL
 *
 */
