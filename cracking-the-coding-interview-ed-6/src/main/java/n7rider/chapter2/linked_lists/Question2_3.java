package n7rider.chapter2.linked_lists;

/**
 * 2.3
 * Delete Middle Node: Implement an algorithm to delete a node in the middle (i.e., any node but
 * the first and last node, not necessarily the exact middle) of a singly linked list, given only access to
 * that node.
 * EXAMPLE
 * Input: the node c from the linked list a->b->c->d->e->f
 * Result: nothing is returned, but the new linked list looks like a->b->d->e->f
 * Hints:#72
 * ---
 *  * Post-run observations:
 *
 *
 *  * After comparing with solution:
 * - Same as mine
 */
public class Question2_3 {

    public static void main(String[] args) {
        Node n1 = new Node("a");
        n1.then("b").then("c").then("d").then("e").then("f");

        deleteNode(n1.next.next);
        prettyPrint(n1);

    }

    static void deleteNode(Node c) {
        c.val = c.next.val;
        c.next = c.next.next;
    }

    static class Node {
        String val;
        Node next;

        Node(String val) {
            this.val = val;
        }

        public Node then(String val) {
            Node newNode = new Node(val);
            this.next = newNode;
            return newNode;
        }
    }

    public static void prettyPrint(Node n) {
        System.out.print(n.val);
        while(n.next != null) {
            n = n.next;
            System.out.print(" > " + n.val);
        }
    }
}

/**
 * Algorithm
 * ---
 * There is no way to get b, but we know that b.next = c
 * So we can change c's internals i.e., val to d, then set c's next to e (A shallow clone)
 *
 * removeFromList:
 * c.val = c.next.val
 * c.next = c.next.next
 */