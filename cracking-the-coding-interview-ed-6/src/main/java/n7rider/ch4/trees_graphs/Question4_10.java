package n7rider.ch4.trees_graphs;

import static org.junit.Assert.*;

/**
 * 4.10
 * Check Subtree: Tl and T2 are two very large binary trees, with Tl much bigger than T2. Create an
 * algorithm to determine if T2 is a subtree of Tl.
 * A tree T2 is a subtree of Tl if there exists a node n in Tl such that the subtree of n is identical to T2.
 * That is, if you cut off the tree at node n, the two trees would be identical.
 *
 * ---
 * Post-run observations:
 * Runs in O(n) time.
 *
 * After comparing with solution:
 * The solution copies the trees to an array and checks for a substring there. Simpler, but there won't be any
 * improvement performance-wise.
 * Solution warns against using in-order traversal while comparing all nodes in T2 because t1={3, 4, x} and
 * t2={3, x, 4} will result in the same. So pre-order traversal is recommended. I make both move together, so
 * this doesn't apply.
 *
 */
public class Question4_10 {
    public static void main(String[] args) {

        Node t1Root = new Node(1);
        Node t1L1Left = new Node(2);
        Node t1L1Right = new Node(3);
        Node t1L2Left1 = new Node(4);
        Node t1L2Left2 = new Node(5);
        Node t1L2Right1 = new Node(6);
        Node t1L2Right2 = new Node(7);

        t1Root.left = t1L1Left;
        t1Root.right = t1L1Right;
        t1L1Left.left = t1L2Left1;
        t1L1Left.right = t1L2Left2;
        t1L1Right.left = t1L2Right1;
        t1L1Right.right = t1L2Right2;

        Node t2Root = new Node(3);
        Node t2L1Left = new Node(6);
        Node t2L1Right = new Node(7);
        t2Root.left = t2L1Left;
        t2Root.right = t2L1Right;

        Tree t1 = new Tree(t1Root);
        Tree t2 = new Tree(t2Root);

        assertTrue(Tree.isSubTree(t1.root, t2.root));

        t2L1Right.val = 8;
        assertFalse(Tree.isSubTree(t1.root, t2.root));
    }

    static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }
    }

    static class Tree {
        Node root;

        public Tree(Node root) {
            this.root = root;
        }

        static boolean isSubTree(Node t1Node, Node t2Node) {
            if(t1Node == null || t2Node == null) {
                return false;
            }

            if(t1Node.val == t2Node.val) {
                boolean result = checkNode(t1Node, t2Node);
                if(result) {
                    return true;
                }
            }

            return isSubTree(t1Node.left, t2Node) || isSubTree(t1Node.right, t2Node);
        }

        static boolean checkNode(Node t1Node, Node t2Node) {
            if(t1Node == null ^ t2Node == null) {
                return false;
            } else if(t1Node == null && t2Node == null) {
                return true;
            }

            if(t1Node.val != t2Node.val) {
                return false;
            } else {
                return checkNode(t1Node.left, t2Node.left) && checkNode(t1Node.right, t2Node.right);
            }
        }
    }




}

/**
 * Repeat until traversal of T1 is done:
 *   Do in-order traversal in T1 to find T2's root
 *   Do in-order traversal for both trees and see if they match all nodes
 *   Exit if true
 *   Continue if false
 *
 * Tree : root
 *
 * isSubTree(t1Node, t2Node):
 *
 *   // null check
 *   isSubTree(t1Node.left, t2Node)
 *   if(t1Node == t2Node)
 *     boolean result = checkNode(t1Node, t2Node)
 *     if(result)
 *       return true
 *
 *   isSubTree(t1Node.right, t2Node)
 *
 * checkNode(t1Node, t2Node):
 *      // Add null check
 *      if(t1Node != t2Node)
 *        return false
 *      else
 *        bool left = checkNode(t1Node.left, t2Node.left)
 *        bool right = checkNode(t1Node.right, t2Node.right)
 *        return left && right
 *
 *
 */