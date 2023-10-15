package n7rider.ch4.trees_graphs;

/**
 * 4.2
 * Minimal Tree: Given a sorted (increasing order) array with unique integer elements, write an algorithm
 * to create a binary search tree with minimal height.
 * ---
 * Post-run observations:
 *
 * Runs in O(N) time. The boolean[] used and direction checks look odd though
 *
 * After comparing with solution:
 * On comparing, I found I shouldn't include mid-number on recursive calls. If I don't include it, there is no chance of
 * never getting access to a number (probably my code will still work if I use leftIndex <= rightIndex). However, the
 * boolean[] used is still needed. Without it, numbers get reused. It makes sense. We're passing a range that includes
 * the middle, so it doesn't guarantee that the mid-point is not used.
 *
 */
public class Question4_2 {
    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Tree tree = new Tree(arr);
        System.out.println("Hi");

    }

    static class Tree {
        Node root;

        Tree(int[] arr) {
            this.root = new Node();
            input = arr;
            isUsed = new boolean[arr.length];
            convertToTree(arr);
        }

        boolean[] isUsed;
        int[] input;

        class Node {
            int val;
            Node left;
            Node right;
        }

        void convertToTree(int[] arr) {
            input = arr;
            boolean[] isUsed = new boolean[arr.length];

//            getAndSetNode(this.root, 0, arr.length - 1, Direction.CENTER);
            getAndSetNode_V2(this.root, 0, arr.length - 1);
        }

        enum Direction {
            LEFT,
            RIGHT,
            CENTER
        }

        void getAndSetNode(Node node, int leftIndex, int rightIndex, Direction direction) {
            int mid = (leftIndex + rightIndex) / 2;
            boolean isCleanDiv = (leftIndex + rightIndex) % 2.0 == 0;

            // Take the ceil value for right side. Otherwise, mid of 7 + 8 would use the 7 (floor value), and 8 would never be put in the tree
            if(direction == Direction.RIGHT && !isCleanDiv) {
                mid++;
            }
            if(!isUsed[mid]) {
                node.val = input[mid];
                isUsed[mid] = true;

                if(leftIndex < rightIndex) {
                    node.left = new Node();
                    getAndSetNode(node.left, leftIndex, mid, Direction.LEFT);
                    node.right = new Node();
                    getAndSetNode(node.right, mid, rightIndex, Direction.RIGHT);
                }
            }
        }

        void getAndSetNode_V2(Node node, int leftIndex, int rightIndex) {
            int mid = (leftIndex + rightIndex) / 2;

            if(!isUsed[mid]) {
                node.val = input[mid];
                isUsed[mid] = true;

                if(leftIndex < rightIndex) {
                    node.left = new Node();
                    getAndSetNode_V2(node.left, leftIndex, mid - 1);
                    node.right = new Node();
                    getAndSetNode_V2(node.right, mid + 1, rightIndex);
                }
            }
        }
    }

}

/**
 * 0 & 8 | mid = 4
 * 0 & 4 | mid = 2                                              4 & 8 | mid = 6
 * 0 & 2 | mid = 1                              2 & 4 | mid = 3                 4 & 6 | mid = 5             6 & 8 | mid = 7
 * 0 & 1 | mid = 0   1 & 2 | mid = [1]  2 & 3 | mid = [2]   3 & 4 | mid = [3]  .......         6 & 7 | mid = 6 | 7 & 8 | mid = 7
 *       5
 *    3   7
 *   2 4 6 8
 *  1 2
 *
 *
 *  1, [2], [3], [4], [5], [6], [7], [8], 9
 */

/**
 * A binary search tree (not balanced) from an array
 * Input: array in increasing order e.g., 1, 2, 3, 4, 5
 * Simplest tree: From root, all nodes will be to the right of other
 *
 * A binary search tree (with minimal height)
 * Input: array in increasing order e.g., 1, 2, 3, 4, 5, 6, 7, 8, 9
 * For minimal height, put root at the center, distribute elements evenly
 *
 * Ex: Find smallest no. 5, put it at root
 *
 *    5
 *  3   7
 * 2 4 6 8
 *1       9
 *
 * setNode(index, val):
 *  treeNode.val = val;
 *
 * left = 0, right - size - 1
 * setNode(root, mid-of-array);
 * int leftC = mid of (o to mid)
 * if(left == right || left > right) skip
 * else recursive(left, mid)
 * // similar for the right
 *
 * Tree - Node root
 * Node: int val. lNode, rNode
 * int[] - input
 *
 * convertToTree
 *  -> findAndSetMidRec
 *      -> setNode
 *     recursive
 *     return Tree
 * return Tree
 */

