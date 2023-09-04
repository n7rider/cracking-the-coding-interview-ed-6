package n7rider.chapter2.linked_lists;

/**
 * 2.5
 * Sum Lists: You have two numbers represented by a linked list, where each node contains a single
 * digit. The digits are stored in reverse order, such that the 1 's digit is at the head of the list. Write a
 * function that adds the two numbers and returns the sum as a linked list.
 * EXAMPLE
 * Input: (7-> 1 -> 6) + (5 -> 9 -> 2).That is,617 + 295.
 * Output: 2 -> 1 -> 9. That is, 912.
 * FOLLOW UP
 * Suppose the digits are stored in forward order. Repeat the above problem.
 * EXAMPLE
 * Input:(6 -> 1 -> 7) + (2 -> 9 -> 5).That is,617 + 295.
 * Output: 9 -> 1 -> 2. That is, 912.
 * Hints: #7, #30, #71, #95, #109
 * ---
 * Post-run observations:
 * - Reverse order works, but forward order's carry will not work
 * - Value that makes carry go up multiple digits e.g., 99 + 9999 = 10098 will not work
 * with a and a.next references.
 *
 *
 *  * After comparing with solution:
 *  - The solution uses recursion, should try it.
 * 
 */
public class Question2_5 {

    public static void main(String[] args) {
        Node n1 = new Node(7);
        n1.then(1).then(6);
        Node n2 = new Node(5);
        n2.then(9).then(2);

        Node out1 = add(n1, n2);
        prettyPrint(out1);

        Node n3 = new Node(7);
        n3.then(1).then(2);
        Node n4 = new Node(9);
        n4.then(1).then(9);
        Node out2 = addForward(n3, n4);
        prettyPrint(out2);

        Node n5 = new Node(9);
        n5.then(9);
        Node n6 = new Node(9);
        n6.then(9).then(9).then(9);
        Node out3 = addForward(n5, n6);
        prettyPrint(out3);
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

    public static void prettyPrint(Node n) {
        // TODO Add null check
        System.out.print(n.val);
        while(n.next != null) {
            n = n.next;
            System.out.print(" > " + n.val);
        }
        System.out.println();
    }

    public static int findSize(Node n) {
        int c = 0;
        while (n != null) {
            c++;
            n = n.next;
        }
        return c;
    }

    public static Node add(Node a, Node b) {
        int carry = 0;
        Node preOut = new Node(-1);
        Node c = preOut;

        while(a != null || b != null) {

            c.next = new Node(0);
            c = c.next;
            int val1 = a == null ? 0 : a.val;
            int val2 = b == null ? 0 : b.val;
            int sum = val1 + val2 + carry;
            carry = sum / 10;
            c.val = sum % 10;

            a = a == null ? null : a.next;
            b = b == null ? null : b.next;
        }

        return preOut.next;
    }

    public static Node addForward(Node a, Node b) {
        int aSize = findSize(a);
        int bSize = findSize(b);
        // pad with zeros

        int diff = aSize - bSize;
        if(diff != 0) {
            if ((aSize < bSize)) {
                a = padWithZeros(a, diff);
            } else {
                b = padWithZeros(b, diff);
            }
        }

        // Init
        Node preA = new Node(0);
        preA.next = a;
        a = preA;
        Node preB = new Node(0);
        preB.next = b;
        b = preB;
        Node preC = new Node(0);
        Node c = preC;

        int carry = 0;
        while(a.next != null || b.next != null) {
            int n1 = a.next == null ? 0 : a.next.val;
            int n2 = b.next == null ? 0 : b.next.val;
            int sum = n1 + n2;

            // Add and carry
            c.next = new Node(sum % 10);
            carry = sum / 10;
            c.val = c.val + carry;

            a = a.next == null ? a : a.next;
            b = b.next == null ? b : b.next;
            c = c.next;
        }

        return preC.val == 0 ? preC.next : preC;
    }

    private static Node padWithZeros(Node smallerNode, int diff) {
        for(int i = 0; i < Math.abs(diff); i++) {
            Node tempHead = new Node(0);
            tempHead.next = smallerNode;
            smallerNode = tempHead;
        }
        return smallerNode;
    }

}

/**
 * Algorithm:
 *
 * carry = 0
 * while a.next != null || b.next != null
 *   default either to 0 if next is null
 *   c.val = a.val + b.val + carry
 *   point to next for a, b, c
 *
 * return c
 *
 *
 */

/**
 * Algorithm 2 - In forward order
 * 7 1 2 + 9 1 9 -> 1 6 3 1
 * 9 9 9 9 + 9 9 9 9 -> 1 9 9 9 8
 *
 * Simplest way - rotate and add, then rotate back
 * Adding 2 num will never make you change digit 2 places before (seen in 9999 + 9999)
 * So keeping n, n.parent references should be enough
 *
 * fill zeros at the head for smaller list
 * carry = 0
 * create preHead for a, b , c
 * n1 = preHead of a
 * n2 = preHead of b
 * n3 = preHead of c
 *
 * while n1.next!= null || n2.next !=null
 *    n3.next = n1.next + n2.next
 *    if carryover => n3 = carry
 *
 * return preHead of c.next
 *
 */