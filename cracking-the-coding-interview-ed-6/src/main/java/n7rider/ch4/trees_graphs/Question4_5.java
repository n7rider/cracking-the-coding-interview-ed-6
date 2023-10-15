package n7rider.ch4.trees_graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 4.5
 * Validate BST: Implement a function to check if a binary tree is a binary search tree.
 * ---
 * Post-run observations:
 * Runs in O(N) time - Just using in-order traversal
 *
 * After comparing with solution:
 * Same as mine.
 * I tried and gave up approach of max and min but that's one of the solutions in the book. It worked easily because the author
 * uses null for maxValue (i.e., using Integer). Don't be afraid to use null or mix up primitives with wrapped types if it makes corner cases easier.
 *
 *
 */
public class Question4_5 {
    public static void main(String[] args) {
        Node root = new Node(10);
        root.left = new Node(2);
        root.left.left = new Node(1);
        root.left.right = new Node(7);
        root.left.left.left = new Node(0);
        root.left.right.right = new Node(35);

        root.right = new Node(13);
        root.right.left = new Node(11);
        root.right.right = new Node(17);
        root.right.right.right = new Node(19);

        Tree tree1 = new Tree();
        tree1.root = root;
        assertFalse(tree1.isBST());

         // Fix bad element
        root.left.right.right = new Node(9);
        System.out.println("------");
        assertTrue(tree1.isBST());

    }

    static class Node {
        int val;
        Node left;
        Node right;

        Node(int val) {
            this.val = val;
        }
    }

    static class Tree {
        Node root;

        boolean isBST() {
            // Skip null check for root
            return isBST(root, null, null);
//            LinkedList<Integer> list = new LinkedList<>();
//            return traverseInOrder(root, list);
        }

        private boolean isBST(Node node, Integer minAllowed, Integer maxAllowed) {
            if(node == null) {
                return true;
            }

            int minAllowedInt = minAllowed == null ? Integer.MIN_VALUE : minAllowed;
            int maxAllowedInt = maxAllowed == null ? Integer.MAX_VALUE : maxAllowed;

            if(node.val < minAllowedInt || node.val > maxAllowedInt) {
                System.out.println("Failed at node: " + node.val);
                return false;
            }

            var leftSideCheck = isBST(node.left, minAllowed, node.val);
            var rightSideCheck = isBST(node.right, node.val, maxAllowed);

            return leftSideCheck && rightSideCheck;
        }

        private boolean traverseInOrder(Node node, LinkedList<Integer> list) {

            if(node == null) {
                return true;
            }

            var leftCheck = traverseInOrder(node.left, list);
            if(list.isEmpty() || node.val > list.getLast()) {
                list.add(node.val);
            } else {
                return false;
            }
            var rightCheck = traverseInOrder(node.right, list);

            return leftCheck && rightCheck;
        }
    }
}

/**
 *      10
 *   2      13
 *  1 7   11  17
 * 0   35       19
 *
 *
 * init:
 * check(root, root.val, root.val)
 *
 * check(node, maxSoFar, minSoFar):
 *   assert node.left <= node //else return false
 *   assert node.right >= node // else return false
 *
 *
 **  if node.left < minSoFar: minSoFarTemp = node.left
 *   check(node.left, maxSoFar, node.left)
 *
 *   if node.right > maxSoFar: maxSoFarTemp = node.right
 *   check(node.right, maxSoFarTemp, minSoFar)
 *
 *
 *  - Just checking left and right with parent doesn't work
 *  - Just having maxSoFar and minSoFar won't work for nodes at zig zag spots - You'll never know when we turned left or right upstream.
 *  So maxSoFar and minSoFar are not enough to determine.
 *
 *  Alternate algo:
 *  Do in-order traversal
 *  Keep adding to array
 *  Check if item > prev item
 *
 *
 *
 *
 */