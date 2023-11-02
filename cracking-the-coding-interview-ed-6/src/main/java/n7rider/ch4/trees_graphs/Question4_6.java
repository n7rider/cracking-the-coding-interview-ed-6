package n7rider.ch4.trees_graphs;

import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

/**
 * 4.6
 * Successor: Write an algorithm to find the "next" node (i.e., in-order successor) of a given node in a
 * binary search tree. You may assume that each node has a link to its parent.
 * ---
 * Post-run observations:
 * Takes O(log n) time.
 * Missed the corner case with right children but could have caught it if I had drawn up an example.
 *
 * After comparing with solution:
 *
 *
 */
public class Question4_6 {
    public static void main(String[] args) {
        Node root = new Node(30);
        Tree t1 = new Tree(root);

        // Set up the tree
        Node l1 = new Node(20);
        l1.parent = root;
        root.left = l1;

        Node r1 = new Node(40);
        r1.parent = root;
        root.right = r1;

        Node l2_11 = new Node(10);
        l2_11.parent = l1;
        l1.left = l2_11;

        Node l2_12 = new Node(25);
        l2_12.parent = l1;
        l1.right = l2_12;

        Node l3_11 = new Node(22);
        l3_11.parent = l2_12;
        l2_12.left = l3_11;

        Node l3_12 = new Node(28);
        l3_12.parent = l2_12;
        l2_12.right = l3_12;

        // Set up complete
        assertEquals(r1.val, t1.getInOrderSuccessor(root).val);
        assertEquals(t1.getInOrderSuccessor(l3_12).val, root.val);
        assertEquals(l2_12.val, t1.getInOrderSuccessor(l3_11).val);
        assertEquals(null, t1.getInOrderSuccessor(r1));
    }

    static class Node {
        int val;
        Node left;
        Node right;
        Node parent;

        public Node(int val) {
            this.val = val;
        }
    }

    static class Tree {
        Node root;

        public Tree(Node node) {
            this.root = node;
        }

        Node getInOrderSuccessor(Node n) {

            if(n.right != null) {
                return n.right;
            }

            while(true) {
                if(isLeftChild(n) == true || n == root) {
                    break;
                } else {
                    n = n.parent;
                }
            }

            return n.parent;
        }

        private boolean isLeftChild(Node n) {
            if(n.parent == null || n.parent.left != n) {
                return false;
            } else {
                return true;
            }
        }
    }

}

/**
 * in_order(n):
 *   in_order(n.left)
 *   print n
 *   in_order(n.right)
 *
 * in-order-successor:
 *
 * if n has right child:
 *  print n.right
 *
 *   if isNleftChild(n)
 *     print n.parent
 *   else
 *     while(1)
 *       n = n.parent
 *       isNleftChild(n) == true || n == root
 *         break
 *       else n = n.parent
 *       return n.parent

 * isNleftChild(n)
 *   if n.parent == null || n.parent.left != n
 *     return false
 *   else
 *     return true;
 *
 *            30
 *      20        40
 *   10   25
 *      22  28
 *
 *
 *
 */