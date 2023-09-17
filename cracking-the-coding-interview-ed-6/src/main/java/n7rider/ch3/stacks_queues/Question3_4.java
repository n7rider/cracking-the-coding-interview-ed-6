package n7rider.ch3.stacks_queues;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Question3_4 {

    /**
     * 3.4
     * Queue via Stacks: Implement a MyQueue class which implements a queue using two stacks.
     * 
     * ---
     * Post-run observations:
     * Works on the first try, but I could have thought it out myself
     *
     *
     * After comparing with solution:
     * dequeue can be simplified. You can always call move() and have the predicate there
     * rather than have an if check in both places
     */

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    static class Stack {
        private Node head;

        void push(Node n) {
            var temp = head;
            head = n;
            n.next = temp;
        }

        Node pop() {
            var temp = head;
            if(head != null) {
                head = head.next;
            }
            return temp;
        }
    }

    static class Queue {
        Stack left;
        Stack right;

        Queue() {
            this.left = new Stack();
            this.right = new Stack();
        }

        void enqueue(Node n) {
            left.push(n);
        }

        Node dequeue() {
            Node n = right.pop();
            if(n == null) {
                move(left, right);
                n = right.pop();
            }
            return n;
        }

        private void move(Stack left, Stack right) {
            Node n = left.pop();
            while(n != null) {
                right.push(n);
                n = left.pop();
            }
        }
    }

    public static void main(String[] args) {
        Queue queue1 = new Queue();
        queue1.enqueue(new Node(1));
        queue1.enqueue(new Node(2));
        queue1.enqueue(new Node(3));
        queue1.enqueue(new Node(4));
        queue1.enqueue(new Node(5));

        assertEquals(1, queue1.dequeue().val);
        assertEquals(2, queue1.dequeue().val);

        queue1.enqueue(new Node(6));
        queue1.enqueue(new Node(7));
        queue1.enqueue(new Node(8));

        assertEquals(3, queue1.dequeue().val);
        assertEquals(4, queue1.dequeue().val);
        assertEquals(5, queue1.dequeue().val);
        assertEquals(6, queue1.dequeue().val);
        assertEquals(7, queue1.dequeue().val);
        assertEquals(8, queue1.dequeue().val);
        assertNull(queue1.dequeue());
    }

}

/**
 * Use back to back stack with overlapping boundaries
 * Have a doubly linked list with 3 elements - 0 1 2
 * stackTop - insert
 * stackBottom - delete
 *
 * stackTop : insert new n.
 * head = n
 * n.next = 0
 *
 * stackBottom : delete 3
 * head = 3.prev
 * return 3
 *
 * Pseudocode:
 *
 * Domain - Stack : pop(), push() | Queue - enqueue, dequeue | Node : prev, next
 *
 * init()
 *   StackTop.head = null = StackBottom.head
 *
 * enqueue()
 *   In stack top: new.next = curr_head
 *   In stack bottom: head == null ? curr_head
 *
 * dequeue()
 *   In stack bottom: print head, head=head.prev
 *
 * Using prev is not really a stack's style. If we only use next, we need recursion, that's like flipping one on another.
 *
 * So, additions = keep adding to stack 1
 * removal = add everything to stack 2, pop, then add back to stack 1
 *
 * After looking at hint, once again I got the initial idea but writing an example would haave worked
 *
 * Enqueue - Keep adding to stack
 * Dequeue - Move to other stack, and pop
 * Enqueue/Dequeue - Keep doing from stack1 or stack2 until stack 2 is over. Move to other stack only when 2 is empty.
 *
 */