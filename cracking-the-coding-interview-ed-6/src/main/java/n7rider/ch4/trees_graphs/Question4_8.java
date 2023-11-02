package n7rider.ch4.trees_graphs;

import static org.junit.Assert.assertEquals;

/**
 * 4.8
 * First Common Ancestor: Design an algorithm and write code to find the first common ancestor
 * of two nodes in a binary tree. Avoid storing additional nodes in a data structure. NOTE: This is not
 * necessarily a binary search tree.
 *
 * ---
 * Post-run observations:
 * O(n) to find node 1, then O(n) to find node 2.
 * Got rid of the odd string concat with path so far since it's not actually required
 * Noodled two outputs through two recursive functions
 * Still feel the algorithm is complex but I didn't want to use any data structures or add parent to each node (More details
 * in the question could have helped)
 *
 * After comparing with solution:
 * Using depth of a tree is often helpful and is worth considering
 * Solution is similar to mine but it went top down from root to node. It makes the code smaller i.e., combines both
 * traversals into one method. However, the runtime can be slightly higher because you go from root to all the way everytime.
 *
 */
public class Question4_8 {
    public static void main(String[] args) {
        Node n8 = new Node("19", null, null);
        Node n7 = new Node("17", null, n8);
        Node n6 = new Node("1", null, null);
        Node n5 = new Node("15", null, n7);
        Node n4 = new Node("5", n6, null);
        Node n3 = new Node("30", null, null);
        Node n2 = new Node("10", n4, n5);
        Node n1 = new Node("20", n2, n3);

        Tree t = new Tree(n1);

        assertEquals("10", t.findCommonNode("1", "19").commonNode);

    }

    static class Node {
        String name;
        Node left;
        Node right;

        Node(String name, Node left, Node right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }
    }
    static class Output {
        boolean foundNode1;
        String commonNode;

        Output(boolean foundNode1, String commonNode) {
            this.foundNode1 = foundNode1;
            this.commonNode = commonNode;
        }
    }

    static class Tree {
        Node root;

        public Tree(Node root) {
            this.root = root;
        }

        Output findCommonNode(String node1, String node2) {
            return traverse(node1, node2, root);
        }

        /*
         *  *
         *  *            20
         *  *         10    30
         *  *       5   15
         *  *      1     17
         *  *              19
         */
        Output traverse(String node1, String node2, Node currNode) {
            if(currNode == null) {
                return null;
            }

            if(currNode.name.equals(node1)) {
                var output = traverseForNode2(node2, currNode, currNode);
                return generateFromNode2Traversal(output);
            }

            var outputLeft = traverse(node1, node2, currNode.left);
            if(outputLeft != null && outputLeft.commonNode != null) {
                return outputLeft;
            } else if(outputLeft != null && outputLeft.foundNode1) {
                var output = traverseForNode2(node2, currNode, currNode);
                return generateFromNode2Traversal(output);
            }

            var outputRight = traverse(node1, node2, currNode.right);
            if(outputRight != null && outputRight.commonNode != null) {
                return outputRight;
            } else if(outputLeft != null && outputRight.foundNode1) {
                var output = traverseForNode2(node2, currNode, currNode);
                return generateFromNode2Traversal(output);
            }

            return null;
        }

        private Output generateFromNode2Traversal(Output output) {
            if(output != null && output.commonNode != null) {
                return output;
            } else {
                return new Output(true, null);
            }
        }

        Output traverseForNode2(String node2, Node currNode, Node startNode) {
            if(currNode == null) {
                return null;
            }

            if(currNode.name.equals(node2)) {
                System.out.println("Common node found: " + startNode.name);
                return new Output(true, startNode.name);
            }

            var leftOut = traverseForNode2(node2, currNode.left, startNode);
            if(leftOut != null) {
                return new Output(true, startNode.name);
            }

            var rightOut = traverseForNode2(node2, currNode.right, startNode);
            if(rightOut != null) {
                return new Output(true, startNode.name);
            }

            return null;
        }
    }


}


/**
 * Simplest solution:
 * Trace path of node 1 - from node to root
 * Trace path of node 2 - from node to root
 *
 * Add node 1's path to set/map
 * Keep adding node 2's path to set until insert fails. That's the common point
 *
 * Without data structure:
 * - Keep adding parent info to nodes as we trace the path
 * - Not a BST, so  can't traverse using left right rules
 * - Use DFS?
 *
 * Using DFS:
 * Trace path of node 1 - from node to root using DFS
 * When answer is found, check from upper stacks if there is a path to node 2 from those
 *
 * Node - val, left, right
 * Since structure is binary tree, DFS can be replaced with pre-order traversal
 *
 *
 * node1Path = traverse(node1, root)
 *
 *
 * traverse(node1, currNode, pathSoFar):
 * if(currNode == null)
 *   return null
 *
 *  if(currNode == node1):
 *   out = traverse2(node2, currNode, currNode)
 *   return out
 *
 *  var leftOut = traverse(node1, currNode.left)
 *  if(leftOut.commonNode):
 *    return out
 *  else
 *  if(leftOut.foundNode1):
 *    out = traverse2(node2, currNode, currNode)
 *    return out
 *  else
 *    return null
 *
 *  repeat for right
 *
 *  return null
 *
 *
 * traverse2(node2, currNode, startedNode):
 *  if(currNode == null)
 *    return null
 *
 *  if(currNode == node2)
 *    return startedNode
 *
 *  var leftOut = traverse2(node2, currNode.left, startedNode)
 *  if(leftOut != null)
 *    return startedNode
 *
 *  var rightOut = traverse2(node2, currNode.right, startedNode)
 *  if(rightOut != null)
 *    return startedNode
 *
 *
 * Use a type to pass result:
 * Result: node1Found (boolean), commonNode (String)
 *
 *
 *            20
 *         10    30
 *       5   15
 *      1     17
 *              19
 */
