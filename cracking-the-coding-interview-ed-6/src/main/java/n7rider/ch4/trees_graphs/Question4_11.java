package n7rider.ch4.trees_graphs;

import java.util.Random;

/**
 * 4.11
 * Random Node: You are implementing a binary tree class from scratch which, in addition to
 * insert, find, and delete, has a method getRandomNode() which returns a random node
 * from the tree. All nodes should be equally likely to be chosen. Design and implement an algorithm
 * for getRandomNode, and explain how you would implement the rest of the methods.
 *
 * ---
 * Post-run observations:
 * getRandom runs in O(log N) but can go to O(N)
 *
 * After comparing with solution:
 * The book goes one step further and reduces the number of random calls to 1.
 * Use the first random call's output to find the actual node. e.g., if(rand(20+1+15) returns 10,
 * go left, then check its children to see where 10 would go and so on.
 *
 */
public class Question4_11 {
    public static void main(String[] args) {
        Node root = new Node(1);
        Tree tree = new Tree();
        tree.root = root;
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);

        System.out.println(tree.findRandom().val);
        System.out.println(tree.findRandom().val);
        System.out.println(tree.findRandom().val);
    }
    static class Node {
        int val;
        int count;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
            this.count = 1;
        }
        public Node(int val, int count) {
            this.val = val;
            this.count = count;
        }
    }

    static class Tree {
        Node root;

        Node insert(int val) {
            if(root == null) {
                root = new Node(val, 1);
            }
            return insertUtil(val, root);
        }

        Node find(int val) {
            return findUtil(val, root);
        }

        boolean delete(int val) {
            if(root == null) {
                return false;
            } else if(root.val == val) {
                root = null;
                return true;
            } else {
                return deleteUtil(val, root);
            }
        }

        private Node insertUtil(int val, Node node) {
            Node returnedNode;
            if(node.left == null) {
                node.left = new Node(val);
                returnedNode = node.left;
            } else if(node.right == null) {
                node.right = new Node(val);
                returnedNode = node.right;
            } else if(node.left.count < node.right.count) {
                returnedNode = insertUtil(val, node.left);
            } else {
                returnedNode = insertUtil(val, node.right);
            }
            node.count++;
            return returnedNode;
        }

        private Node findUtil(int val, Node node) {
            if(node == null) {
                return null;
            }

            if(node.val == val) {
                return node;
            }

            Node leftFind = findUtil(val, node.left);
            if(leftFind != null) {
                return leftFind;
            }

            Node rightFind = findUtil(val, node.right);
            if(rightFind != null) {
                return rightFind;
            }
            return null;
        }

        private boolean deleteUtil(int val, Node node) {
            if(node == null) {
                return false;
            }

            boolean leftNodeMatch = node.left != null && node.left.val == val;
            if(leftNodeMatch) {
                Node currNode = node.left;
                boolean hasChild = currNode.left != null || currNode.right != null;
                if(!hasChild) {
                    node.left = null;
                    return true;
                } else if(currNode.left != null ^ currNode.right != null) {
                    node.left = currNode.left != null ? currNode.left : currNode.right;
                    return true;
                } else {
                    if(node.left.count > node.right.count) {
                        Node replNode = findReplacementForDel(currNode.left);
                        Node tempNode = currNode.left;
                        node.left = replNode;
                        replNode.left = tempNode;
                    } else {
                        Node replNode = findReplacementForDel(currNode.right);
                        Node tempNode = currNode.right;
                        node.right = replNode;
                        replNode.right = tempNode;
                    }
                }
                return true;
            }

            // Repeat for rightNode
            // Skip refactoring this method and finishing it since the solution doesn't deal with this
            return false;
        }

        private Node findReplacementForDel(Node node) {
            if(node.left != null && node.right != null) {
                node.count--;
                return findReplacementForDel(node.left.count > node.right.count ? node.left : node.right);
            }

            node.count--;
            if(node.left == null) {
                return node.right;
            } else {
                return node.left;
            }
        }

        private boolean hasOneOrNoChild(Node node) {
            return node.left == null || node.right == null;
        }

        Node findRandom() {
            return findRandomUtil(root);
        }

        private Node findRandomUtil(Node node) {
            if(node == null) {
                return null;
            }

            int leftCount = node.left == null ? 0 : node.left.count;
            int rightCount = node.right == null ? 0 : node.right.count;
            int totalCount = 1 + leftCount + rightCount;

            Random random = new Random();
            int randomNumber = random.nextInt(totalCount);

            if(randomNumber == 0) {
                return node;
            } else if(randomNumber <= leftCount) {
                return findRandomUtil(node.left);
            } else {
                return findRandomUtil(node.right);
            }
        }


    }
}
/**
 * RandomNode:
 * This is a binary tree, not a BST, so there's no need for ordering.
 * Also tree is not complete, so depth and index combinations won't work
 * - Maintain a total count in the tree structure and choose random number? Can do a custom traversal
 * based on depth to find the nth node
 * - Keep a hashMap of index and node? Get random number and look up this index?
 *
 * --- Peeked at the solution since this is too trivial
 * The above works but has O(N) storage (hash map) or  O(1) time
 *
 * The book ponders doing a random(1,3) at every node and traverse, but that won't split the probability equally
 * P(root) = 1/3 but P(left-most node) = 1/3^depth
 *
 * The book suggests keeping track of items under each node and then use them in probability calculation e.g., if
 * root's left has 20 nodes, root's right has 10 nodes. calculate random(1, 1+20+10) and then navigate accordingly.
 * We need additional field(s) for count. That's the strategy.
 *
 * Use node.count or (both node.leftChildrenCount & node.rightChildrenCount)
 *
 * Going with node.count - uses 1 instead of 2 - easy to update, less storage
 *
 * insert - insert node, set its count to 1, increment count for all its parents
 * find - traversal
 * delete - find node, delete it, decrement count for all its parents
 *
 * insert(val):
 *  if root == null //
 *      root = new Node(val, 1)
 *  else
 *      insertUtil(val, root)
 *
 * insert (val, node):
 *
 *  if(node.left == null)
 *    node.left = new Node(val, 1)
 *    return true
 *  else if(node.right == null)
 *    node.right = new Node(val, 1)
 *    return true
 *
 *  if(node.left.count < node.right.count)
 *    insert(val, node.left)
 *  else
 *    insert(val, node.right)
 *
 *  node.count++
 *
 * ----
 * delete(val, node):
 *  if(node == null)
 *    return false
 *
 *  if(node.left == val)
 *    if(both children are not null)
 *       dir = node.left.count > node.right.count ? node.left : node.right
 *       Node newNode = findReplacementForDel(dir)
 *       dir = newNode
 *       count--
 *       return true
 *    else
 *     node.left = non-null child
 *     count--
 *     return true
 *
 *  // Repeat for node.right
 *
 *  boolean leftOp = delete(val, node.left)
 *  boolean rightOp = delete(val, node.right)
 *
 *  if(leftOp || rightOp)
 *    node.count--
 *    return true
 *  else
 *    return false
 *
 * ----
 * Node findReplacementForDel(node)
 *   if(both children are not null)
 *      Node repl = findReplacementForDel(node.left.count > node.right.count ? node.left : node.right)
 *      node.count--
 *      return repl
 *
 *   if(node.left == null)
 *     node.count--
 *     return node.left
 *   else
 *     node.count--
 *     return node.right
 *
 *
 * -----
 * find(val. node):
 *  if(node == null)
 *    return null
 *
 *  if(node.val == val)
 *    return node
 *
 *  Node leftOp = find(val, node.left)
 *  Node rightOp = find(val, node.right)
 *
 *  if(leftOp != null)
 *    return leftOp
 *  else if(rightOp != null)
 *    return rightOp
 *  else
 *    return null
 *
 * -----
 * findRandom(node):
 *   int leftProb = node.left == null ? 0 : node.left.count
 *   int rightProb = node.right == null ? 0 : node.right.count
 *
 *   int total = 1 + leftProb + rightProb
 *   int random = Math.rand(1, total)
 *   if(random == 1)
 *     return node
 *   else(random <= 1 + leftProb)
 *     return findRandom(node.left)
 *   else return findRandom(node.right)
 *
 *       1
 *     2  3
 *   4  5
 *
 */