package n7rider.chapter2.linked_lists;

import static org.junit.Assert.assertNull;

/**
 * 2.4
 * Partition: Write code to partition a linked list around a value x, such that all nodes less than x come
 * before all nodes greater than or equal to x. If x is contained within the list, the values of x only need
 * to be after the elements less than x (see below). The partition element x can appear anywhere in the
 * "right partition"; it does not need to appear between the left and right partitions.
 * EXAMPLE
 * Input:
 * Output:
 * 3 -> 5 -> 8 -> 5 -> 10 -> 2 -> 1 [partition= 5]
 * 3 -> 1 -> 2 -> 10 -> 5 -> 5 -> 8
 * Hints: #3, #24
 * ---
 *  * Post-run observations:
 *  Order is O(n) with three distinct phases - finding partition, iterating, swapping x times
 *
 *  * After comparing with solution:
 * If you move to the start and end of list (there is no need to find partition, so it saves O(n) time
 * Using two linked lists and merging them is the simplest way
 * Preserving order is an added bonus that can be done here
 * Remember: With linked list, there is no need to think in terms of in-place computations
 * TRY AGAIN
 */
public class Question2_4 {

    public static void main(String[] args) {
        Node n1 = new Node(3);
        n1.then(5).then(8).then(5).then(10).then(2).then(1);

        Node head1 = partition(n1, 5);
        prettyPrint(head1);

        Node n2 = new Node(3);
        n1.then(5).then(8).then(5).then(10).then(2).then(1);

        Node head2 = partition(n1, 15);
        assertNull(head2);
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
    }

    static void prettyPrint(Node head) {
        Node node = head;
        System.out.print(node.val); // Skip null check
        while(node.next != null) {
            node = node.next;
            System.out.print(" > " + node.val);
        }
        System.out.println();
    }

    static Node partition(Node head, int pVal) {
        // Find partition
        Node partition = null;
        Node n = head;
        while(n != null) {
            if(n.val == pVal) {
                partition = n;
                break;
            }
            n = n.next;
        }

        // Skip if no partition
        if(partition == null) {
            return null;
        }

        // Iterate and do partition
        Node curr = head;
        Node preHead = new Node(0);
        preHead.next = head;
        Node prev = preHead;

        boolean reachedPartition = false;
        while (curr != null) {
            System.out.print(String.format("Before. prev = %d curr = %d | ", prev.val, curr.val));
            prettyPrint(preHead);

            Node temp = curr.next;

            if(curr.val == pVal) {
                reachedPartition = true;
            } else if(curr.val >= pVal && !reachedPartition) {
                // moveToRight
                prev.next = curr.next;
                Node tempNext = partition.next;
                partition.next = curr;
                curr.next = tempNext;
            } else if(curr.val < pVal && reachedPartition) {
                // moveToLeft
                prev.next = curr.next;
                preHead.next = curr;
                curr.next = head;
                head = curr;
            } else {
                prev = curr;
            }
            curr = temp;
        }

        return head;
    }
}

/**
 * Algorithm:
 * Element < partition - Before partition
 * Element >= partition - After partition
 *
 * Find partition - get its reference, index
 * iterate from head
 *      store prev, current
 *      temp = next node i.e., curr.next // store if it curr moves somewhere else
 *      if element == partition
 *         reachedPartition = true
 *      else
 *      if element < partition && reachedPartition
 *          moveToRight
 *          continue // prev remains the same
 *      else
 *      if element >= partition && !reachedPartition
 *          moveToLeft
 *          continue // prev remains the same
 *      else
 *          prev = current
 *
 * move basics:
 * attach parent to child (detach from current pos)
 * detach new parent from child (attach to current pos)
 * attach child (get the link going)
 *
 *  moveToRight
 *     prev.next = curr.next
 *     temp = partition.next
 *     partition.next = curr
 *     curr.next = partition.next
 *
 *  moveToLeft
 *     prev.next = curr.next
 *     prehead.next = curr
 *     curr.next = head
 *     head = curr
 */