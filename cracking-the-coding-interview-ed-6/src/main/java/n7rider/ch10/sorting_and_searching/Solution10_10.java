package n7rider.ch10.sorting_and_searching;

/**
 * Rank from Stream: Imagine you are reading in a stream of integers. Periodically, you wish to be able
 * to lookup the rank of a number x (the number of values less than or equal to x). lmplement the data
 * structures and algorithms to support these operations. That is, implement the method track ( int
 * x), which is called when each number is generated, and the method getRankOfNumber(int
 * x), which returns the number of values less than or equal to x (not including x itself).
 * EXAMPLE
 * Stream (in order of appearance): 5, 1, 4, 4, 5, 9, 7, 13, 3
 * getRankOfNumber(1)
 * 0
 * getRankOfNumber(3) = 1
 * getRankOfNumber(4)
 * 3
 *
 * After finishing:
 * Works, but had to debug a couple of times to figure out because I didn't draw out a tree and was visualizing it.
 *
 * This solution requires creating an object and calling methods repeatedly, so I created a separate class in the
 * same file, but outside the main class. If I create an inner non-static class, then I need to create an object
 * for the main class to create an object for the inner class.  Which
 *
 * After comparing:
 * Similar.
 * I realized the `rank + n.count + 1` I do in the find() logic can be made simpler to rank + n.count if every element
 * has an implicit rank of it. But then, I need to subtract 1 at the end since the requirement is 0-index-based ranking.
 *
 *
 *
 */
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Solution10_10 {

    public static void main(String[] args) {
        // Not a perfect representation of Stream, but a non-static object comes close. It resembles a stream that adds data
        // to the tree at any time possible
        Solution s1 = new Solution();
        s1.readFromStream(List.of(5, 1, 4, 4, 5, 9, 7, 13, 3)); // Sorted - 1, 3, 4, 4, 5, 5, 7, 9, 13
        assertEquals(0, s1.findRank(1));
        System.out.println();

        s1.readFromStream(List.of(0));  // Sorted - 0, 1, 3, 4, 4, 5, 5, 7, 9, 13

        assertEquals(1, s1.findRank(1));
        System.out.println();

        assertEquals(4, s1.findRank(4));
        System.out.println();

        assertEquals(6, s1.findRank(5));
        System.out.println();

        assertEquals(8, s1.findRank(9));
        System.out.println();

        assertEquals(9, s1.findRank(13));
        System.out.println();

        assertEquals(-1, s1.findRank(132));
        System.out.println();

    }
}

class Solution {
    Node root;
    void readFromStream(List<Integer> list) {
        for(Integer i: list) {
            // Add null check for number if null input is possible
            insert(root, i);
        }
    }

    private void insert(Node n, Integer i) {
        if(n == null) {
            root = new Node(i);
            System.out.println("Inserted " + i + " at root \n");
            return;
        }

        // Just add count to the same entry. It's simpler without dupes in the tree.
        if(i == n.val) {
            n.count++;
            System.out.printf("Increment count of %d to %d \n", n.val, n.count);
            System.out.printf("Inserted %d at its existing position \n\n", n.val);
            return;
        }

        Node next = i < n.val ? n.left : n.right;
        if(i < n.val) { // Smaller value, so current element's rank goes up
            n.count++;
            System.out.printf("Increment count of %d to %d \n", n.val, n.count);
        }
        if(next == null) {
            Node newNode = new Node(i);
            if(i < n.val) {
                n.left = newNode;
            } else {
                n.right = newNode;
            }
            System.out.printf("Inserted %d under %d. Is on the left side? %s \n\n", i, n.val, i <= n.val);
        } else {
            insert(next, i);
        }
    }

    int findRank(int ele) {
        return find(root, ele, 0);
    }

    private int find(Node n, int ele, int rank) {
        System.out.printf("Finding %d. Now at %d. Curr. left is %d, Curr. right is %d, Curr. count is %d, | Sum : %d \n", ele, n == null ? -1 : n.val,
                n == null || n.left == null ? null : n.left.val, n == null || n.right == null ? null : n.right.val, n == null ? null : n.count, rank);
        if(n == null) {
            return -1;
        }

        if(n.val == ele) {
            return rank + n.count; // Rank found so far, plus rank of this item (i.e., smaller items under this item)
        } else {
            Node next = ele < n.val ? n.left : n.right;
            if(ele < n.val) {
                return find(next, ele, rank); // No smaller elements encountered, no change to rank
            } else {
                return find(next, ele, rank + n.count + 1); // Smaller item encountered, add its rank, then +1 for the item itself
            }
        }
    }
}

class Node {
    int val;
    int count;
    Node left;
    Node right;

    Node(int val) {
        this.val = val;
    }
}

/*
Usually min and max-heap are used for these operations when you want rank of one known element.

But we need to find rank of any item, so a heap (min-heap) can work.
Just keeping popping until you find the element, so storage is O(n), read time is O(n), insertion
time is O(log n) for each item

What about a tree? Can it result in O(log n)?

A tree that stores value and items under it (incremented when inserted) can give the rank when we find the
element. Storage is O(n), read time is O(log n) up to O(n), insertion time is O(log n) for each item

LL, array may not be fit for streaming data.

Can we make the heap better?
What if we add a min and max to each level of heap. The read time can be O(log n) + O(n / log (n)) then.

Peeked the book solution, and it uses a tree.

          4
       3     7
     2  3   5  11
Algorithm with tree:
Insert (Insert to tree and add counter)

insert(root)

insert(Node n):
  if n is null
    create n, count as 0
  next = new < n ? n.left : n.right
  n.count++
  call(next)

find(root, ele)

find(Node n, ele):
  if n == ele
    return n.left != null && n.left == ele ? n.left.count : n.count // additional check for adding rank for dupes
  next = new < n ? n.left : n.right
  find(n, ele)


Realized while coding that we need to update everything to the left if a value is inserted to the right, e.g. if we insert the
biggest no. so far to the right, everything in left needs to be updated, so this needs a O(n) update
potentially. So insertion time is O(n)

5, 1, 4, 4, 5, 9, 7, 13, 3

                         5 (4)
                1 (0)            9(0)
                    4 (1)
                  4 (0)  5 (0)

We need to increase rank of every item to the left if we insert to right, this is O(n)

Is there a way to determine this during runtime? What if we just use the count as count, not as rank? Can we derive the rank?

No 9(0) says nothing about its rank. Maybe summ of count everyhing until the root?
We need to add if we took a step to the right, because we have items smaller than this, else just keep going.

This works for non-dupes, for dupes we need to add twice, but rather then recursively checking left, let's just keep
one element for dupes and increase count e.g., let's keep one entry 4(2) in the above.

Recomputing the map again

              5 (1)

 */