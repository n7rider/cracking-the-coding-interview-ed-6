package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stack of Boxes: You have a stack of n boxes, with widths wi, heights hi, and depths di. The boxes
 * cannot be rotated and can only be stacked on top of one another if each box in the stack is strictly
 * larger than the box above it in width, height, and depth. Implement a method to compute the
 * height of the tallest possible stack. The height of a stack is the sum of the heights of each box.
 *
 * After comparing with solution:
 * I forgot to add memoization. The solution creates a map and saves {idx, newMax}. This eliminates traversing
 * through the same entries.
 * I could have used Comparator rather than sort + reverse
 * No need to pass the entire sequence in the output (because we're not printing a sequence), we're just returning
 * the max count. The reason I'm carrying the entire sequence is to facilitate debugging, but it is just
 * carrying redundant data a lot.
 */
public class Question8_13 {
    public static void main(String[] args) {
        System.out.println(BoxStacker.findTallestStackHeight(new int[][]{
                {8, 9, 10}, {5, 6, 7}, {4, 5, 1}, {3, 3, 4}, {2, 3, 4}, {2, 3, 4}, {1, 2, 3}
        })); // Expected = 4
        System.out.println(BoxStacker.findTallestStackHeight(new int[][]{
                {0, 0, 0}, {1, 1, 1}, {2, 2, 10}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}, {6, 15, 15}, {7, 7, 7}
        })); // Expected = 6

    }

    static class BoxStacker {

        static List<Box> boxes = new ArrayList<>();

        static class Box implements Comparable {
            int width;
            int height;
            int depth;

            Box(int width, int height, int depth) {
                this.width = width;
                this.height = height;
                this.depth = depth;
            }

            @Override
            public int compareTo(Object o) {
                if (o == null) {
                    return -1;
                } else if (!(o instanceof Box)) {
                    return -1;
                } else {
                    Box box2 = (Box) o;
                    if (this.width > box2.width && this.height > box2.height && this.depth > box2.depth) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }

            public boolean isGreaterThan(Box other) {
                if (this.compareTo(other) > 0) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String toString() {
                return String.format("[%d, %d, %d]", this.width, this.height, this.depth);
            }
        }

        static int findTallestStackHeight(int[][] inp) {
            boxes = new ArrayList<>(); // init or cleanup
            for (int i = 0; i < inp.length; i++) {
                boxes.add(new Box(inp[i][0], inp[i][1], inp[i][2]));
            }

            Collections.sort(boxes);
            Collections.reverse(boxes);
            printSortedBoxes();

            List<Integer> outputSoFar = new ArrayList<>();
            return findTallestStackHeight(0, outputSoFar, 0);
        }

        private static int findTallestStackHeight(int idx, List<Integer> output, final int max) {
            int newMax = max;
            if (idx >= boxes.size()) {
                System.out.printf("End of stack %s \n", output.toString());
            } else {
                int outSize = output.size();
                // output.get(outSize - 1) is the last ele in output list
                if (output.isEmpty() || boxes.get(output.get(outSize - 1)).isGreaterThan(boxes.get(idx))) {
                    output.add(idx);
                    newMax = findTallestStackHeight(idx + 1, output, Math.max(max, output.size()));
                } else {
                    List<Integer> newOut = cloneAndSkipLastEntry(output);
                    // Noodling the max through recursion looks a little odd with newMax1, newMax2 here.
                    int newMax1 = findTallestStackHeight(idx + 1, newOut, Math.max(max, newOut.size()));
                    int newMax2 = findTallestStackHeight(idx + 1, output, Math.max(max, output.size()));
                    newMax = Math.max(newMax1, newMax2);
                }
            }
            return Math.max(newMax, output.size());
        }

        private static List<Integer> cloneAndSkipLastEntry(List<Integer> output) {
            if (output.isEmpty()) {
                return new ArrayList<>();
            } else {
                List<Integer> newOut = new ArrayList<>(output);
                newOut.remove(newOut.size() - 1);
                return newOut;
            }
        }

        private static void printSortedBoxes() {
            System.out.println("Sorted boxes");
            for (Box box : boxes) {
                System.out.println(box.toString());
            }
            System.out.println("-----");
        }

    }
}

/*
To clarify, we'll be given n boxes but the stack can be n only if w, h, d are all greater than prev at each level
e.g., 1*2*3, 2*3*4, 3*2*3 will only return 2

All 3, w|h|d must be higher, so if one is lower, we can't proceed

Simple solution:
Sort them by comparator (w, then h, then d) in asc order
start at each pos skipping boxes that are not clearly bigger

NOT EFFICIENT:
If n is found or curr-highest > n-i, then stop  (because it won't beat the highest anyway)
else keep going
return the highest

Execution time:
O(n log n) - for sort
O(n ^ 2) - for computing
    But this can be improved
    e.g., if 1-5, 15-20 work

1 2 3 4 5/5/5 | 5/6/5 | 5/7/5 | 5/8/6

EFFICIENT:
We keep counting from 0 to n while skipping wastes

So, O(n) - for making the stack

So, total is O(n + n log n)

Without sort:
O(n ^ n) -
Each stack building attempt takes O(n)
We try this with O(n) boxes

Is it really that big?
e.g., 5 2 3 4 1
Well, we run through the elements O(n) times.
Adding to stack isn't much

But simplest solution isn't DynProg or recursion.

So, can we make the fn. without sort better?
e.g., 5 2 4 3 1

What if we cache results after each run?
Let's create a DoubLL

5 - Start at 5
2 - Add 2 before 5
4 - Start from 2 or 5 and find its pos?
    Takes O(n) time

Can we cache positions?
4/5/5 4/6/6 5/6/6.
In this case, adding 4/5/5 gives a better result than 4/6/6, but it's random if we cache only by len

If 4/6/6 came first, we would have added it, skipped 5/6/6, so adding 4/5/5 very late doesn't undo this.

So we need to add everything? If we add everything, it's similar to sort

What if we just do sort but use cache?

e.g., 466 222 566 333 111 455

466 - Creates nothing
222 - Creates 222, 333, 455
566 - N/A
333 - Look up cache. Someone created 333, 455, so skip
111 - Not in cache, So build 111, 455.
Now merge 111, 222, 333, 455
This doesn't add 566
So we do graph joins? That's also costly since we need to look at O(m) graph but not as bad as O(n^n)

e.g., 466 222 566 333 111 455 999

466 - Creates 466 999
222 - Creates 222, 333, 455 999
566 - 566
333 - Look up cache. Someone created 333, 455, 999 so skip
111 - Not in cache, So build 111, 455. 999

So far it is, O(n^n), but still it's O(n^n) with cache (assuming n items were skipped thanks to cache)
Graphs 466 999 | 222, 333, 455 999 | 566 |  333, 455 | 111, 455
We ended up with more than O(n) items. And we need a doub-LL-ish add for elements in the middle
We do a merge-sort-ish alg for the graph, so there's sort anyway
However adding in some ways affect the output e.g., how to decide 466 is to be eliminated.
    smallest goes first or largest goes first is not right always, depending on the ordering.

Peeked the solution, and I was right with the first step, sorting is the way to go
However, there's a problem after sorting.
0,0,0 | 1,1,1 | 2,2,10 | 3,3,3 | 4,4,4 | 5,5,5 | 6,15,15 | 7,7,7

Simplest way to find combination after sorting:
Start at 1.
Skip 0 boxes
Skip 1 boxes... continue until we skip 1...n-1 boxes

Recursively do the same for i+1 boxes

So this takes O(n^2) time

Optimization - There's no comb. to try if we're not skipping anything
in the above e.g., 0, 1, 2, 6 | 0, 1, 3, 4, 5, 6 | 0, 1, 3, 4, 5, 7
(It's desc in the problem, will flip order in the code)

Decision points - 2&3 | 6&7
Too complex to use this. Will just do recursion

init:
  box(0, []):

box(idx, output):
  if(box[idx] > out.last) // add validation if out is empty
    out += box[idx]
    box(idx + 1, out)
  else
    newOut = clone output
    newOut.delete last
    newOut.add(curr)
    box(idx + 1, out)
    box(idx + 1, newOut)

0 1 2
0 1 2 x4 |                   0 1 3
0 1 2 x5 |         0 1 4     0 1 3
0 1 2 x6 | 0 1 5   0 1 4 5   0 1 3 4
0 1 2 7 | 0 1 5 6
          0 1 5 7            0 1 3 4 5 6 | 0 1 3  4 5 7

We can memoize 3, 4,5 running straight
but looking up the map probably has the same cost as comparing the numbers.
Computation is less, but storage is more.
 */