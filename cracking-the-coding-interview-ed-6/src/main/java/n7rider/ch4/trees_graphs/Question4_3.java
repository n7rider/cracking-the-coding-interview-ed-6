package n7rider.ch4.trees_graphs;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * 4.3
 * List of Depths: Given a binary tree, design an algorithm which creates a linked list of all the nodes
 * at each depth (e.g., if you have a tree with depth D, you'll have D linked lists).
 * ---
 * Post-run observations:
 * Runs in O(N) time. Uses BFS
 *
 * After comparing with solution:
 * Same.
 * However, DFS is not too complex or inefficient. You just need to pass around the index of the LL where each value needs to go.
 *
 */
public class Question4_3 {
    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

        Tree tree = new Tree(root);
        var output = tree.createNodesLL();

        assertEquals(3, output.size());
        System.out.println(output);
    }

    static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    static class Tree {
        Node root;

        public Tree(Node root) {
            this.root = root;
        }

        public List<LinkedList<Node>> createNodesLL() {
            List<LinkedList<Node>> mainList = new ArrayList<>();
            addNodesToLL(mainList, List.of(root));
            return mainList;
        }

        private void addNodesToLL(List<LinkedList<Node>> mainList, List<Node> nodes) {
            LinkedList<Node> linkedList = new LinkedList<>();
            linkedList.addAll(nodes);
            mainList.add(linkedList);

            List<Node> nodesForNextLL = new ArrayList<>();
            for(Node node : nodes) {
                if(node.left != null) {
                    nodesForNextLL.add(node.left);
                }
                if(node.right != null) {
                    nodesForNextLL.add(node.right);
                }
            }

            if(!nodesForNextLL.isEmpty()) {
                addNodesToLL(mainList, nodesForNextLL);
            }
        }

    }
}

/**
 * Use BFS?
 *
 * An example algo:
 * Add root aka parent to linked list
 * Add children to queue
 * Create new linked list
 * for each child in root:
 *  Add to linked list
 *  add child's children to new queue (set it as current queue)
 *
 * root -> gets its own queue
 * create new queue -> set it as current
 * root's children -> add to current queue
 * create new queue -> set it as current
 * root's grand children -> add to current queue
 *
 * create List<LL<Node>> mainList
 * addToLL (List.of(root))
 *
 * addToLL (List<Node> ln):
 * set currentLLNode = LL<Node>
 * add ln to currentLLNode
 * add currentLLNode to mainList
 *
 * create new LN
 * for each node in ln
 *   newLN.add(node.children)
 * call addToLL(newLN)
 *
 *
 *
 *
 */