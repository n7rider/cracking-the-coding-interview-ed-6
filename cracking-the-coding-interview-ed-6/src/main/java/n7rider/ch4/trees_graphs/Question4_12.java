package n7rider.ch4.trees_graphs;

/**
 * 4.12
 * Paths with Sum: You are given a binary tree in which each node contains an integer value (which
 * might be positive or negative). Design an algorithm to count the number of paths that sum to a
 * given value. The path does not need to start or end at the root or a leaf, but it must go downwards
 * (traveling only from parent nodes to child nodes).
 *
 * ---
 * Post-run observations:
 * Runtime is O(log N) to check from each node. For checking from n nodes, it's O(N log N).
 *
 * After comparing with solution:
 * The solution does it in O(xN) i.e., O(N) time.
 * It does DFS of each path of tree, then creates a hashmap of Map<sum-so-far, index>. This takes O(N)
 * time and you'll have 4 paths in a tree with depth as 3. In each map, iterate through each entry, and
 * check if Map.get(entry)-Map.get(entry-reqSum) has an entry e.g., if Map entry is 24, req. sum is 8,
 * if Map.get(16) returns null, then you have a valid path.
 * I see it'll work well for a straight path but it won't catch a path that zig-zags down. If we add DFS
 * for such paths too, there'll be almost O(N) hash maps. That's a huge compromise memory-wise, but it's
 * good to know such techniques exist.
 * TLDR, if you want to improve runtime, consider expanding a tree into an array or a hash map.
 *
 *
 */
public class Question4_12 {
    public static void main(String[] args) {
        Node node1 = new Node(1, null, null);
        Node node2 = new Node(2, null, null);
        Node node3 = new Node(3, null, null);
        Node node4 = new Node(4, null, null);
        Node node5 = new Node(5, node1, node2);
        Node node6 = new Node(6, node3, node4);
        Node node7 = new Node(7, node5, node6);

        Tree tree = new Tree();
        tree.root = node7;

        System.out.println(tree.findNodeCount(6));

    }

    static class Node {
        int val;
        Node left;
        Node right;

        Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    static class Tree {
        Node root;

        int findNodeCount(int sum) {
            return findNodeCountAdapter(root, sum);
        }

        private int findNodeCountAdapter(Node node, int sum) {
            int count = 0;
            count += findNodeCountUtil(node, sum, 0);

            if(node != null) {
                count += findNodeCountAdapter(node.left, sum);
                count += findNodeCountAdapter(node.right, sum);
            }
            return count;
        }

        private int findNodeCountUtil(Node currNode, final int reqSum, int currSum) {
            if(currNode == null) {
                return 0;
            }

            int count = 0;
            currSum = currSum + currNode.val;
            if(currSum == reqSum) {
                System.out.println(String.format("Path found. It ends at %d", currNode.val));
                count++;
            }

            if(currSum < reqSum) {
//                System.out.println(String.format("Current sum %d is smaller than %d", currSum, reqSum));
                count += findNodeCountUtil(currNode.left, reqSum, currSum);
                count += findNodeCountUtil(currNode.right, reqSum, currSum);
            }

            return count;
        }

        /**
         *       7
         *   5        6
         * 1  2     3  4
         */


    }




}

/**
 *      1
 *   2     3
 *  4 5   6 7
 *
 *  Integer method1(sum, node, count)
 *    sum = sum + node.left
 *    if(sum == req)
 *      sout
 *      return count + 1
 *    if(sum < req)
 *      method1(sum, node.left, count + 1)
 *    else
 *      return null
 *
 *    // repeat for right side
 *
 *  callerIntermediate (node):
 *    Integer result = method1(0, node, 0)
 *    if(result != null || node != null)
 *      result = callerIntermediate(0, node.left, 0)
 *      if(result != null)
 *      result = callerIntermediate(0, node.right, 0)
 *    return result
 *
 *
 *
 *  callerEndUser()
 *    callerIntermediate(root)
 *
 *
 */