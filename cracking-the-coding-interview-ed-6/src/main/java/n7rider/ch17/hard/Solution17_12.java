package n7rider.ch17.hard;

/**
 * BiNode: Consider a simple data structure called BiNode, which has pointers to two other nodes.
 * public class BiNode {
 * }
 * public BiNode nodel, node2;
 * public int data;
 * The data structure BiNode could be used to represent both a binary tree (where nodel is the left
 * node and node2 is the right node) or a doubly linked list (where nodel is the previous node and
 * node2 is the next node). Implement a method to convert a binary search tree (implemented with
 * BiNode) into a doubly linked list. The values should be kept in order and the operation should be performed
 * in place (that is, on the original data structure).
 *
 * After finishing:
 * I tried to insert nodes in order-traversal but looks like require going to child level, then flipping the top levels to go left-center-right upto the top.
 * This is tricky to achieve only with node2 being used for 2LL. At root, you can need to set the leftMost on this side as its most. This can go too
 * procedural, so I wonder if there is a simple trick.
 * Inserting level by level can be simple, can reconstructing is tricky.
 *
 * Considering that we are doing similar to in-order traversal made is simpler. The tricky parts to figure out to do things before and especially after
 * the recursive call. Also, the part that can a node is not be connected to its right but the left most in its right side.
 *
 * After comparing:
 * I missed to implement backwards chain of the 2LL. My approach wouldn't work for making it backwards because if I assign prev as
 * center for the findFirstNode(), then there will be a chain unless I create a set to monitor what entries I've visited already.
 * The book deals with it elegantly with recursion - rather than doing findFirstNode() - do recursive call.
 *
 * The book solution works because it is piecing together a 2LL from the beginning then at the center merges them by creating a new
 * 2LL that starts from left.head to right.tail with center in the middle. With only nodes being passed up, my logic faces a problem here
 * - it connect center.next = rightSide.lowest, but not rightSide.lowest.next = center. If you assign it rightaway, there will be an infinite
 * loop. Having a parallel structure running is how the book solution does it.
 */
public class Solution17_12 {
    public static void main(String[] args) {
        BiNode l3N1 = new BiNode(null, null, 6);
        BiNode l3N2 = new BiNode(null, null, 18);
        BiNode l3N3 = new BiNode(null, null, 31);
        BiNode l3N4 = new BiNode(null, null, 43);
        BiNode l3N5 = new BiNode(null, null, 55);
        BiNode l3N6 = new BiNode(null, null, 68);
        BiNode l3N7 = new BiNode(null, null, 81);
        BiNode l3N8 = new BiNode(null, null, 100);
        BiNode l2N1 = new BiNode(l3N1, l3N2, 12);
        BiNode l2N2 = new BiNode(l3N3, l3N4, 37);
        BiNode l2N3 = new BiNode(l3N5, l3N6, 62);
        BiNode l2N4 = new BiNode(l3N7, l3N8, 87);
        BiNode l1N1 = new BiNode(l2N1, l2N2, 25);
        BiNode l1N2 = new BiNode(l2N3, l2N4, 75);
        BiNode head = new BiNode(l1N1, l1N2, 50);

        /*
                      50
             25                   75
         12      37          62        87
       6  18   31 43       55  68    81   100
         */

        BiNode head2LL = treeToNodeV3(head);
        print2LL(head2LL);
    }

    static void print2LL(BiNode node) {
        int c = 0;
        while(node != null && c < 16) {
            System.out.print(" --> " + node.data);
            node = node.node2;
            c++;
        }
    }

    static BiNode leftMost = null;
    static BiNode rightMost = null;

    static class BiNode {
        BiNode node1;
        BiNode node2;
        int data;

        BiNode(BiNode node1, BiNode node2, int data) {
            this.node1 = node1;
            this.node2 = node2;
            this.data = data;
        }

        @Override
        public String toString() {
            return (node1 == null ? null : node1.data) + " | " + this.data + " | " + (node2 == null ? null : node2.data);
        }
    }

    /*

Ex tre:
         5
    3           10
1     4      7       20


                      50
             25                   75
         12      37          62        87
       6  18   31 43       55  68    81   100
     */
    static BiNode treeToNodeV3(BiNode head) {
        // Skip asserting that the head is not null
        leftMost = findFirstNode(head);
        rightMost = findLastNode(head);
        helperV2(head);
        rightMost.node2 = null; // Marks the end of the 2LL

        int c = 0;
        BiNode node = rightMost;
        while(node != null && c < 16) {
            System.out.print(" --> " + node.data);
            node = node.node1;
            c++;
        }
        System.out.println("----");

        return leftMost;
    }

    private static BiNode helperV2(BiNode currNode) {
        if(currNode == null) {
            return null;
        }

        BiNode left = helperV2(currNode.node1);
        if(left != null) {
            left.node2 = currNode;
        }

        BiNode right = helperV2(currNode.node2); // This will return right-most in the branch. But we need to connect to the left-most
        BiNode leftMostInRight = findFirstNode(currNode.node2);
        currNode.node2 = leftMostInRight;

        return right != null ? right : currNode;
    }

    private static BiNode findFirstNode(BiNode node) {
        if(node == null) {
            return null;
        }
        if(node.node1 == null) {
            return node;
        }
        return findFirstNode(node.node1);
    }

    private static BiNode findLastNode(BiNode node) {
        if(node.node2 == null) {
            return node;
        }
        return findLastNode(node.node2);
    }



    /*
    helper_conv(node, prev) // prev = null in the beginning, prev entry in the 2LL otherwise
    if node == null
        exit
    left = helper_conv(node.left)
    left.next = node // null check & skip
    node.next = helper_conv(node.right)  // null check & skip
    return node.next != null ? node.next : node // return right node if it is not != null
     */

//    static BiNode treeToNode(BiNode head) {
//        helper(head, null, null);
//        rightMost.node2 = null; // Marks the end of the 2LL
//
//        System.out.printf("LeftMost: %d | RightMost: %d \n", leftMost.data, rightMost.data);
//        return leftMost;
//    }
//
//    private static void helper(BiNode curr, BiNode parent, BiNode gp) {
//        if(curr == null) {
//            return;
//        }  // 6
//        BiNode left = curr.node1;
//        boolean isLeftLeaf = isLeafNode(left);
//        if(isLeftLeaf) { // Leaf node
//            left.node1 = parent;
//            left.node2 = curr;
//
//            if(leftMost == null) {
//                leftMost = left; // Starting node of 2LL. Can also find by traversing the tree but this is easier
//            }
//        } else {
//            helper(left, curr, parent);
//        }
//
//        BiNode right = curr.node2;
//        boolean isRightLeaf = isLeafNode(right);
//        if(isRightLeaf) { // Leaf node
//            right.node1 = curr;
//            right.node2 = right == parent.node2.node2 ? gp : parent;
//
//            rightMost = right; // Ending node of 2LL.
//        } else {
//            helper(right, curr, parent);
//        }
//
//        boolean isLastChild = (parent != null) && ((parent.node2 == null && parent.node1 == curr) || (parent.node2 == curr));
//        if(isLastChild) {
////            left = parent.node1;
////            right = parent.node2;
//            parent.node2 = left;
//
//        }
//    }

    private static boolean isLeafNode(BiNode node) {
        return node != null && node.node1 == null && node.node2 == null;
    }
}

/*
Simplest way to convert a tree to a 2LL (no need to do in-place):

Consider 2LL's prev = TreeNode.left, 2LL's next = TreeNode.right
Ex tre:
         5
    3           10
1     4      7       20


5.prev = 3, 3.prev = 1, and so on.
What will the 2LL become now?

1 no child
3 prev = 1, next = 4
4 no child
5  prev = 3, next = 10
7 no child
10 prev = 7, next= 20
20 no child

1 3 4
4
7
3 5 10 20

1 3 5 10 4 7 10 20

Since leaf nodes all have no child (next), there is no way any one is the last one in 2LL
So, we keep updating to the latest prev, and next as new info comes in.
We keep pushing items further before or ahead.

The above will make reconversion not possible - a requirement is for the values to be kept in order

So how do we go level by level?

So, 2LL is
3   5   10
1 3 4   5   7 10 20
Even this make reconversion difficult
We need to group by 3 but leave the middle one as the node, but it's difficult to find the middle if one of the leaf nodes is missing
We can set explicit null values for these maybe

Open questions:
What structure do we want for 2LL? What does in order mean? In order by levels, by values? Levels make more sense since that is how the nodes are linked.

Pseudocode:
// Assume leaf null nodes have BiNode int = -1, data1 = data2 = null.

Lessons from above example:
For left leaf, leaf.node1 = grand-parent, leaf.node2 = parent,
For right leaf, leaf.node1 = parent, leaf.node2 = grand parent

visit (tree.head, null)

visit(n, n-parent):
while(n != null)
    lc = n.left;
    if(lc.n1 == null && lc.n2 == null) // Changes only for leaf nodes, else no changes needed
        lc.n1 = n's parent
        lc.n2 = n;

    rc = n.right;
    if(rc.n1 == null && rc.n2 == null) // Changes only for leaf nodes, else no changes needed
        rc.n1 = parent
        rc.n2 = n's parent;

    if neither is null
        call visit(n.left, n)
        call visit(n.right, n)

go to tail - right-most and set next as null // to prevent infinite loop

--

Since this misses nodes that are on the inside of the right side, let's look at it in an abstract way - like inorder traversal

Ex tre:
         5
    3           10
1     4      7       20


                      50
             25                   75
         12      37          62        87
       6  18   31 43       55  68    81   100

Inorder traversal:

helper(node)
    if node == null
        exit

    helper(node.left)
    print node
    helper(node.right)

In similar terms:

helper_conv(node, prev) // prev = null in the beginning, prev entry in the 2LL otherwise
    if node == null
        exit
    left = helper_conv(node.left)
    left.next = node // null check & skip
    node.next = helper_conv(node.right)  // null check & skip
    return node.next != null ? node.next : node // return right node if it is not != null





 */