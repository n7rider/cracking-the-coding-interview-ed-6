package n7rider.ch2.linked_lists;

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
 * - Much simpler, easier to check
 *
 *
 * After comparing with solution:
 * - Can use insertBefore() method to move all temp <-> c assignment
 * 
 */
public class Question2_5_Attempt2 {

    public static void main(String[] args) {
        Node n1 = new Node(7);
        n1.then(1).then(6);
        Node n2 = new Node(5);
        n2.then(9).then(2);
        Node out1 = addForward(n1, n2);
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

    private static Node padWithZeros(Node smallerNode, int diff) {
        for(int i = 0; i < Math.abs(diff); i++) {
            Node tempHead = new Node(0);
            tempHead.next = smallerNode;
            smallerNode = tempHead;
        }
        return smallerNode;
    }

    static Node c;
    static Node ZERO = new Node(0);

    public static Node addForward(Node a, Node b) {

        int diff = findSize(a) - findSize(b);
        if (diff != 0) {
            if (diff < 0) {
                a = padWithZeros(a, diff);
            } else {
                b = padWithZeros(b, diff);
            }
        }

        prettyPrint(a);
        prettyPrint(b);

        c = new Node(0);
        int finalCarry = addAndReturnCarry(a, b);
        if(finalCarry > 0) {
            c.val = finalCarry;
            return c;
        } else {
            return c.next;
        }
    }



    private static int addAndReturnCarry(Node a, Node b) {
        if(a == null && b == null) {
            return 0;
        }

        a = a == null ? ZERO : a;
        b = b == null ? ZERO : b;

        int sum = a.val + b.val + addAndReturnCarry(a.next, b.next);
        c.val = sum % 10;
        Node temp = new Node(-1);
        temp.next = c;
        c = temp;

        int carry = sum / 10;
        return carry;
    }



}

/**
 * Algorithm 2 - In forward order
 * 7 1 2 + 9 1 9 -> 1 6 3 1
 * 9 9 9 9 + 9 9 9 9 -> 1 9 9 9 8
 *
 * Use recursion, so curr_c = curr_a + curr_b + prev_carry
 * So return carry from op
 *
 * Pad smaller one with zeros (to start both from one's digit)
 * while (a != null || b != null):  // Can use and since we pad with zeros
 *   c.val = a.val + b.val + addAndReturnCarry(a.next, b.next);
 *   curr_carry = c.val / 10
 *   return curr_carry
 *
 * if(curr_carry > 0)
 *      temp = new Node(-1)
 *      temp.next = c
 *      c = temp
 * return c
 *
 * addAndReturnCarry(a, b, carr)
 *   if a and b are null
 *      return 0
 *   else
 *      c.val = getPrevCarry() + a.val + b.val;
 *      temp = new Node(-1)
 *      temp.next = c
 *      c = temp
 *      curr_carry = c.val / 10
 *      return curr_carry
 *
 *
 *
 *
 *
 *
 */