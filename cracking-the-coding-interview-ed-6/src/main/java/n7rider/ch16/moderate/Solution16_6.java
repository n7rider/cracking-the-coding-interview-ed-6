package n7rider.ch16.moderate;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Smallest Difference: Given two arrays of integers, compute the pair of values (one value in each
 * array) with the smallest (non-negative) difference. Return the difference.
 * EXAMPLE
 * Input: {1, 3, 15, 11, 2}, {23, 127,235, 19, 8}
 * Output: 3. That is, the pair (11, 8).
 *
 * After comparing:
 * My attempt to stop before and after the place it stops at inner while loop is odd.
 *
 * After finishing:
 * I tried to stop before and after the inner loop of the merge sort. Letting the comparison run through
 * both arrays is still O(n), and I could have done it. THat makes the code simple (comparing code vs
 * commented code)--
 */
public class Solution16_6 {
    public static void main(String[] args) {
        int[] a1 = {1, 3, 15, 11, 2};
        int[] a2 = {23, 127,235, 19, 8};
        assertEquals(3, smallestDiff(a1, a2));

        // [8, 19, 23, 127, 235] | [1, 2, 3, 11, 15]
        assertEquals(3, smallestDiff(a2, a1));
    }

    public static int smallestDiff(int[] a1, int[] a2) {
        Arrays.sort(a1);
        Arrays.sort(a2);
        System.out.println(Arrays.toString(a1));
        System.out.println(Arrays.toString(a2));

        int idx1 = 0, idx2 = 0;
        int diff = Integer.MAX_VALUE;

        while(idx1 < a1.length && idx2 < a2.length) {
            int tempDiff = Math.abs(a1[idx1] - a2[idx2]);
            diff = Math.min(tempDiff, diff);
            if(a1[idx1] < a2[idx2]) {
                idx1++;
            } else {
                idx2++;
            }

//            if(idx1 > 0) {
//                int tempIdx = idx1 - 1;
//                System.out.printf("Comparison 1: %d & %d \n", a1[tempIdx], a2[idx2]);
//                int tempDiff = Math.abs(a2[idx2] - a1[tempIdx]);
//                diff = Math.min(tempDiff, diff);
//            }
//
//            if(idx1 < a1.length) {
//                System.out.printf("Comparison 2: %d & %d \n\n", a1[idx1], a2[idx2]);
//                int tempDiff = Math.abs(a1[idx1] - a2[idx2]);
//                if(a1[idx1] < a2[idx2]) {
//                    idx1++;
//                } else {
//                    idx2++;
//                }
//                diff = Math.min(tempDiff, diff);
//            }

        }
        return diff;
    }
}

/*
Simplest solution:
Do n^2 computations like bubble sort to find the smallest difference
O(n) = n^2

How do we optimize?
Sort both arrays [1, 2, 3, 11, 15] [8, 19, 23, 127, 235] (O(2 n log n)
Merge sort - final step, create parallel array to hold owns what O(n)
    [1, 2, 3, 8, 11, 15, 19, 23, 127, 235]
    [0, 0, 0, 1, 0,  0,  1,  1,  1,    1] // 0 - array 0 | 1 - array 1
Iterate and check consecutive elements from different arrays? Not bulletproof e,g., 1 vs 8 across 7 elements can
still be smaller than 23 vs 50 across 2 elements.

How about comparing sorted arrays? Will still be O(n^2)

How about cutting off cons. numbers from the merged array if they are some from the same one?
e.g., remove, 1, 2 because we already have 3 from array 0, and comparing that with 8 will give the result anyway

Merge with cons removed = [1, 8, 11, 19]

Or don't merge, and just create smaller new arrays?
[1, 11]   [8, 19]
Now, we can do bubble sort comparison of O(n^2), but it is still smaller than the simplest solution.

Limiting factors:
We always need to compare a bunch of numbers with another bunch
Without comparison, we can't eliminate or decide on anything within one array, so I think this is the simplest we can go.


Comparing book solution:
Same as mine, but rather than creating new smaller arrays,  the book compares and holds the difference inline. Probably
could have got the logic if I write. But I wanted to check if it's a good approach before I go with details.
An actual interview helps you in that case, but here, probably, I should finish my approach before looking up.

Pseudocode:
Sort both arrays
n1 = 0, n2 = 0
int diff = Int.MAX;
int prevSmaller = -1;
while(n1 < a1.length && n2 < a2.length)
  if(n1 < a1.length && a1[n1] < a2[n2])
    if(prevSmaller != 1) // last smallest not from this array, so compare diff
       tempDiff = a2[n2] - a1[n1]
       diff = tempDiff < diff = tempDiff : diff;
    else
       diff =

  while n1 < a1.length && a1[n1] < a2[n2]
     n1++
  // We are at highest n1 < a2[n2]
  tempDiff = a2[n2] - a1[n1]
  if tempDiff < 0
    n2++
    tempDiff = |tempDiff|
  else
    n1++
  diff = tempDiff < diff = tempDiff : diff;








 */