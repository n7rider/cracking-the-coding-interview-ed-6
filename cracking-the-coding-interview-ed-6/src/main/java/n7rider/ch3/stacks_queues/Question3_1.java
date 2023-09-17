package n7rider.ch3.stacks_queues;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class Question3_1 {

    /**
     * 3.1
     * Three in One: Describe how you could use a single array to implement three stacks.
     * ---
     * Post-run observations:
     * I tried solving start and end meticulously (too detailed), could have just created all stack in one util and returned them,
     * but I was focused on creating an equally spaced system that can work for any number.*
     * I jumped into the program too soon - just take time to do it first
     *
     * After comparing with solution:
     * The book doesn't account for 11/3 or 10/3 stacks, just went with a 12/3 set.
     *
     * The book has also attempted to write an example to have variable sizes but agrees it is too complex for an interview.
     *
     */

    final static int STACK_COUNT = 3;
    static class Stack {
        int startIndex;
        int endIndex;
        int currIndex;
        int[] arr;

        Stack(int[] arr, int stackNum) { // stackNum is also 0 based
            this.arr = arr;

            int actualStackLength = (arr.length / STACK_COUNT);
            int additionalLength = findAdditionalLength(stackNum); // e.g., if size is 11, give stack0 one more length
            int totalStackLength = actualStackLength + additionalLength;

            this.startIndex = stackNum * (arr.length / STACK_COUNT) + findPrevAdditionalLengths(stackNum);
            this.endIndex = startIndex + totalStackLength - 1; // -1 to not include the landing number too
            this.currIndex  = endIndex + 1; // starts at bottom of its allocation
        }

        int findAdditionalLength(int stackNum) {
            return arr.length % STACK_COUNT > (stackNum) ? 1 : 0;
        }

        int findPrevAdditionalLengths(int currentStackNum) {
            int count = 0;
            for(int i = 0; i < currentStackNum; i++) {
                count += findAdditionalLength(i);
            }
            return count;
        }

        boolean push(int val) {
            if(currIndex - 1 < startIndex) {
                return false; // Can't insert. It's full
            } else {
                currIndex--; // Move cursor ready for popping
                arr[currIndex] = val;
                return true;
            }
        }

        int pop() {
            if(currIndex > endIndex) {
                return -1; // Nothing in the stack
            } else {
                 int out = arr[currIndex];
                 currIndex++;
                 return out;
            }
        }

        int peek() {
            if(currIndex > endIndex) {
                return -1; // Nothing in the stack
            } else {
                return arr[currIndex];
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[11];
        Stack s0 = new Stack(array, 0);
        Stack s1 = new Stack(array, 1);
        Stack s2 = new Stack(array, 2);

        assertEquals(0, s0.startIndex);
        assertEquals(3, s0.endIndex);
        assertEquals(4, s1.startIndex);
        assertEquals(7, s1.endIndex);
        assertEquals(8, s2.startIndex);
        assertEquals(10, s2.endIndex);

        s0.push(3);
        s0.push(2);
        s0.push(1);
        s0.push(0);
        assertFalse(s0.push(0));

        System.out.println(Arrays.toString(array));

        s1.push(6);
        s1.push(5);
        s1.push(4);
        assertEquals(0, s0.pop());

        System.out.println(Arrays.toString(array));

        s2.push(10);
        s2.push(9);
        s2.push(8);
        assertEquals(4, s1.peek());
        assertEquals(4, s1.pop());
        assertEquals(5, s1.pop());
        System.out.println(Arrays.toString(array));
        assertEquals(6, s1.pop());
        assertEquals(-1, s1.pop());

        System.out.println(Arrays.toString(array));

    }


}

/**
 * having 2 is easy - one from the start, one from the end
 * Having 3:
 *   Can divide into 3? e.g., 10-index array has 3 stacks at indices 0, 4, 7. Let's think it through
 *
 * Construct():
 *   idx1 = 0
 *   idx2 = arr_size / 3
 *   idx3 = idx2 + arr_size / 3
 *
 *   push():
 *     if idx[i-1] = null: Error // Out of size
 *     idx[i+1] = item;
 *     idx[i]++
 *
 *   pop:
 *     if idx[i]= null: null // No element
 *     if i+1 == idx[i+1] start, then i= idx[i] start // roll over
 *     else idx[i]--
 *
 * Notes:
 * While typing code, I realized these:
 * - Adding start and end index keeps push() and pop() simple
 * - Adding start and end index is complex if you account for 11/3 and 10/3 stacks (11 and 10 are array sizes. If size
 * is not perfectly divisible, you need to split out remaining size carefully)
 */