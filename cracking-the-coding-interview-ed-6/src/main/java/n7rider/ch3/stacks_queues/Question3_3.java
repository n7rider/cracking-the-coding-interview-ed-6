package n7rider.ch3.stacks_queues;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Question3_3 {

    /**
     * 3.3
     * Stack of Plates: Imagine a (literal) stack of plates. If the stack gets too high, it might topple.
     * Therefore, in real life, we would likely start a new stack when the previous stack exceeds some
     * threshold. Implement a data structure SetOfStacks that mimics this. SetO-fStacks should be
     * composed of several stacks and should create a new stack once the previous one exceeds capacity.
     * SetOfStacks. push() and SetOfStacks. pop() should behave identically to a single stack
     * (that is, pop () should return the same values as it would if there were just a single stack).
     * FOLLOW UP
     * Implement a function popAt ( int index) which performs a pop operation on a specific sub-stack.
     *
     * ---
     * Post-run observations:
     * Rollover worked, but it was too complex to implement in an interview
     *
     * Lesson: With LL, while copying from one stack to another, copy the value, and not the next reference.
     *
     *
     * After comparing with solution:
     * The book implemented stacks using ArrayList. That leaves a lot of the logic to the ArrayList utility.
     */

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    static class StackSet {
        List<Stack> stacks;
        int currStackIndex;

        class Stack {
            Node head;
            int length;
            int currentSize;
            int index;

            Stack(int length, int index) {
                this.length = length;
                this.index = index;
            }

            boolean push(Node newNode) {
                if(currentSize >= length) {
                    return false;
                } else {
                    Node temp = head;
                    head = newNode;
                    newNode.next = temp;

                    currentSize++;

                    if(index > currStackIndex) {
                        currStackIndex = index;
                    }

                    System.out.printf("Pushed %d to stack %d | currIndex %d\n", newNode.val, index, currStackIndex);
                    return true;
                }
            }

            Node pop() {
                Node toReturn = head;
                if(head != null) {
                    head = head.next;
                    currentSize--;

                    if(index < currStackIndex) {
                        rollOver(index);
                    }
                    System.out.printf("Popped %d from stack %d | currIndex %d\n", toReturn.val, index, currStackIndex);
                } else {
                    System.out.printf("Popped null from stack %d | currIndex %d\n", index, currStackIndex);
                }

                return toReturn;
            }

            Node peek() {
                return head;
            }

            void peekAll() {
                Node temp = head;
                System.out.printf("Contents of stack %d : ", index);
                while(temp != null) {
                    System.out.print(temp.val + " | ");
                    temp = temp.next;
                }
                System.out.println();
            }
        }

        StackSet(int stackCount, int stackLength) {
            this.stacks = new ArrayList<>(stackCount);
            for(int i = 0; i < stackCount; i++) {
                stacks.add(new Stack(stackLength, i));
            }
        }

        void rollOver(final int index) {

            System.out.println("--- Starting rollover at index : " + index);
            int toIndex = index;
            int fromIndex = index + 1;
            while(toIndex < stacks.size() - 1 && fromIndex < stacks.size()) { // Source stack should be at least last. So, target is one less than that.
                StackSet.Stack toStack = stacks.get(toIndex);
                StackSet.Stack fromStack = stacks.get(fromIndex);
                Node node = fromStack.peek();
                if(node == null) {
                    System.out.printf("Found nothing to rollover in stack %d. Heading to next stack\n", fromIndex);
                    fromIndex++;
                    continue;
                }
                boolean result = toStack.push(new Node(node.val));
                if(result) {
                    fromStack.pop();
                    System.out.printf("Transferred a node from %d to %d. Continuing\n", fromIndex, toIndex);
                } else { // Out of space, try inserting to the next stack
                    toIndex++;
                    if(fromIndex == toIndex) {
                        toIndex++;
                    }
                }
            }

            System.out.println("--- Ended rollover");
        }

        Stack get(int index) {
            return stacks.get(index);
        }

        boolean push(Node n) {
            boolean result = stacks.get(currStackIndex).push(n);
            while (!result && currStackIndex < stacks.size() - 1) {
                currStackIndex++;
                result = stacks.get(currStackIndex).push(n);
            }
            if(result) {
                return true;
            } else {
                return false;
            }
        }

        Node pop() {
            Node toReturn = stacks.get(currStackIndex).pop();
            while(toReturn == null && currStackIndex > 0) {
                currStackIndex--;
                toReturn = stacks.get(currStackIndex).pop();
            }
            return toReturn;
        }
    }

    public static void main(String[] args) {
        StackSet stackSet = new StackSet(4, 4);

        stackSet.push(new Node(0));
        stackSet.push(new Node(1));

        StackSet.Stack stack2 = stackSet.get(2);
        stack2.push(new Node(20));

        StackSet.Stack stack1 = stackSet.get(1);
        stack1.push(new Node(10));

        StackSet.Stack stack3 = stackSet.get(3);
        stack3.push(new Node(30));
        stack3.push(new Node(31));
        stack3.push(new Node(32));
        stack3.push(new Node(33));
        assertFalse(stackSet.push(new Node(34)));

        stack1.peekAll();
        stack2.peekAll();
        stack3.peekAll();

        assertEquals(20, stack2.pop().val);

        stack1.peekAll();
        stack2.peekAll();
        stack3.peekAll();

        assertEquals(30, stack2.pop().val);
        assertNull(stack3.pop());

        stack1.peekAll();
        stack2.peekAll();
        stack3.peekAll();

        assertEquals(31, stackSet.pop().val);
    }
}

/**
 * Key requirements:
 * Set.pop and stack2.pop should do the same (Delegate to the right stack when set.pop is called)
 * Set.push should also be similar
 *
 * Domain - Node, Stack (LL<Node>), StackSet(Array<Stack>)
 * // For Stackset, LL is not good to get prev stack if one is done. We can use Double LL but end-user needs a create endpoint.
 * // Array allows index based retrieval e.g., get me a node from 5th stack
 * Operations
 * Node - val, next
 * Stack LL - insert at the head (not tail) - has push, pop
 * Stackset - can iterate through to find a stack, maybe keep track of current stack - has get(i), push, pop
 *
 * Stackset.push:
 * if(currStack.push())
 * Success
 * else
 * stackset.next.push (Repeat until we get one)
 * currStack = this stack
 *
 * Stackset.pop:
 * if(currStack == null): tail
 *
 * rollover logic:
 * toStack = this stack
 * fromStack = toStack.next
 *
 * while fromStack.index < lastStack in set:
 * if(toStack.push(fromStack.peek) // Rolling over, pop and keep going
 * else
 * fromStack = toStack
 *   if toStack.next doesn't exist: break // end of stack set
 *   else
 *   toStack = toStack.next
 *
 *
 *
 *
 */
