package n7rider.ch4.trees_graphs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 4.4
 * Check Balanced: Implement a function to check if a binary tree is balanced. For the purposes of
 * this question, a balanced tree is defined to be a tree such that the heights of the two subtrees of any
 * node never differ by more than one.
 * ---
 * Post-run observations:
 * Runs in O(N) time
 *
 * After comparing with solution:
 * Same but the solution is simpler
 * - I avoided noodling a boolean through recursive frames to make it simple, but the solution does it and it doesn't look bad.
 * - The code looks simpler but tries to find the height at every node, I try to find it only at each leaf nodes. The solution
 * needs O(N log N) but mine takes just O(N)
 *
 */
public class Question4_4 {

    public static void main(String[] args) {
        Node root1 = new Node(1);
        root1.left = new Node(2);
        root1.right = new Node(3);
        root1.left.left = new Node(4);
        root1.left.right = new Node(5);
        root1.right.left = new Node(6);
        root1.right.right = new Node(7);

        Tree tree1 = new Tree(root1);
        assertTrue(tree1.isBalanced());

        Node root2 = new Node(1);
        root2.left = new Node(2);
        root2.right = new Node(3);
        root2.left.left = new Node(4);
        root2.left.left.left = new Node(5);

        Tree tree2 = new Tree(root2);
        assertFalse(tree2.isBalanced());
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
        boolean balancedFlag = true;
        int otherLeafDepth = -1;

        public Tree(Node root) {
            this.root = root;
        }

        boolean isBalanced() {
            checkIfBalanced(root, 1);
            return balancedFlag;
        }

        private void checkIfBalanced(Node node, int currentDepth) {
            if(!balancedFlag) {
                return;
            }

            if(node.left != null) {
                checkIfBalanced(node.left, currentDepth + 1);
            } else {
                compareDepths(currentDepth + 1);
            }
            if(node.right != null) {
                checkIfBalanced(node.right, currentDepth + 1);
            } else {
                compareDepths(currentDepth + 1);
            }
        }

        private void compareDepths(int currentLeafDepth) {
            if(otherLeafDepth == -1) {
                otherLeafDepth = currentLeafDepth;
                return;
            }

            if(Math.abs(currentLeafDepth - otherLeafDepth) > 1) {
                System.out.println(String.format("Tree is not balanced. currentLeafDepth=%d, otherLeafDepth=%d",
                        currentLeafDepth, otherLeafDepth));
                balancedFlag = false;
            }
        }
    }
    /**
     *     1
     *    2 3
     *   4
     *  5
     */
}

/**
 * DFS - keep measuring depth
 * Compare diff of depth at leaf is <= 1. an int var
 *
 * init BranchDepth = -1
 * flag isBalanced = true
 *
 *
 *
 * dfs(node, int depth):
 *
 * if(!isBalanced):
 * return
 *
 * if(!null):
 * dfs(node.left, depth + 1)
 * else:
 * isStillBalanced(depth+1)
 *
 * if(!null):
 * dfs(node.right, depth + 1) // add null check
 * else:
 * isStillBalanced(depth+1)
 *
 *
 * isStillBalanced(depth):
 * if branchDepth == -1:
 * BranchDepth = depth
 * return true
 * else:
 * return abs (branchDepth - depth) <= 1; // set isBalanced = false
 *
 *
 *
 */