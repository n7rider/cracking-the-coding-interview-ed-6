package n7rider.ch10.sorting_and_searching;

import static org.junit.Assert.assertEquals;

/**
 * Search in Rotated Array: Given a sorted array of n integers that has been rotated an unknown
 * number of times, write code to find an element in the array. You may assume that the array was
 * originally sorted in increasing order.
 *
 * After completion: Had to realize the side that has the inflection will have the smallest and the largest.
 * It's simple after that. Drawing always helps, and is better than typing for problems like this.
 *
 * After comparing:
 * The book doesn't use a utility method like isInflectionFound, but I guess this is also a neat way. This is
 * more readable than the bunch of if-else statements in the solution.
 */
public class Solution10_3 {
    public static void main(String[] args) {
        int[] arr1 = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
        assertEquals(8, findXInRotatedArray(arr1, 5));

        int[] arr2 = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
        assertEquals(0, findXInRotatedArray(arr2, 15));

        int[] arr3 = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
        assertEquals(6, findXInRotatedArray(arr3, 3));

        int[] arr4 = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
        assertEquals(11, findXInRotatedArray(arr4, 14));

        int[] arr5 = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
        assertEquals(5, findXInRotatedArray(arr5, 1));

        int[] arr6 = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
        assertEquals(8, findXInRotatedArray(arr6, 5));

        int[] arr7 = {15, 16, 19, 20, 25, 14, 14, 14, 14, 14, 14, 14};
        int result = findXInRotatedArray(arr7, 14);
        assert(result >= 5 && result <= 11);
    }

    static int findXInRotatedArray(int[] arr, int x) {
        return findRec(arr, x, 0, arr.length - 1);
    }

    private static int findRec(int[] arr, int x, int left, int right) {
        int mid = (left + right) / 2;
        if (arr[mid] == x) {
            System.out.println("Found at position: " + mid + "\n");
            return mid;
        }

        if (left > right) {
            return -1;
        }

        boolean inflectionFound = isInflectionFound(arr[left], arr[mid], arr[right]);
        Side sideChosen;

        //  {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
        //   0   1   2   3   4   5  6  7  8  9  10  11
        if (inflectionFound) {
            if (arr[left] < arr[mid]) { // Inflection on right side
                if (x < arr[left] || x > arr[mid]) {
                    sideChosen = Side.RIGHT;
                } else {
                    sideChosen = Side.LEFT; // Right has the extremes. Left has others.
                }
            } else { // Inflection on left side
                if (x < arr[mid] || (x > arr[right])) {
                    sideChosen = Side.LEFT;
                } else {
                    sideChosen = Side.RIGHT; // Left has the extremes. Right has others.
                }
            }
        } else {
            if (x < mid) {
                sideChosen = Side.LEFT;
            } else {
                sideChosen = Side.RIGHT;
            }
        }

        System.out.printf("left = %2d | mid = %2d | right = %2d ||| inf = %6s | side = %6s\n", left, mid, right, inflectionFound, sideChosen);
        if (sideChosen == Side.LEFT) {
            return findRec(arr, x, 0, mid - 1);
        } else {
            return findRec(arr, x, mid + 1, right);
        }
    }

    private static boolean isInflectionFound(int left, int mid, int right) {
        return !(left < mid && mid < right) &&
                !(left > mid && mid > right);
    }

    enum Side {
        LEFT, RIGHT;
    }
}

/*
Rotated unknown number of times, so list will be in ascending or descending order.

However, this doesn't matter to find an element. We can just do a binary search to find the element
in O(log n) time.

Algorithm:
Determine order:

int left = 0
int right = len - 1
if a[left] < a[right]
  Order = asc
else
  Order = desc

int left = 0
int right = len - 1
int mid = mid(left, right)
if mid = x
  Stop
if(mid < x)
  if(order == asc)
    L, R = 0, mid - 1
  else
    L, R = mid + 1, right
else
  if(order == desc)
    L, R = mid + 1, right
  else
    L, R = 0, mid - 1
// WE can squeeze the conditions to

Looked up solution. It seems the rotation can be in the middle
Input : find 5 in {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14}

Find if order is asc or desc
  Look at 3 consec until n-3, n-2, n-1 are reached (OR)
  Compare L+M+R consecutively by binary search until inflection is found

  How about straight-forward binary search?
  Find L, M, R
  if x is between L & M (LT or GT - either way)
    Look in this half - Consider order to find which half to go
  or
    Look in that half

However, this works only if there is only one inflection

Looked up solution. There is only one inflection point.

Algorithm:

int left = 0
int right = len - 1
int mid = mid(left, right)

if mid == x
  FOUND

if inflection(l, m, r)   // l < m < r is FALSE && l > m > r is also FALSE
  if (l < m && x < m)
    side-chosen = LEFT
  else if (l < m && x > m)
    side-chosen = RIGHT
  else if (l > m && x < m)
    side-chosen = RIGHT
  else // i.e., l > m && x > m
    side-chosen = LEFT
else
  if(x > mid)
    side-chosen = RIGHT
  else
    side-chosen = LEFT

This can be re-written to :

if inflection(l, m, r)   // l < m < r is FALSE && l > m > r is also FALSE
  if (l < m && x < m) || (l > m && x < m)
    side-chosen = RIGHT
  else
    side-chosen = LEFT
else
  if(x > mid)
    side-chosen = RIGHT
  else
    side-chosen = LEFT

Example show this is wrong. Just like looking at l, r doesn't get a decision, similarly
looking at l, m doesn't  tell us which way to go for x e.g., 15, ... 1, ... 14 where we are finding 14,
we'll chose side LEFT, but it's actually side right.

So we need to find inflection point, then a translation logic, then do binary sort
This takes O(2 log n) but still is simple and works.

Glanced at book solution, it has a simple if-else solution. So let's try one more time.

Algo:
  What if we deal with everything in terms of inflection

if !inflection(l, m, r)
  simple-binary-search
else
  if(!inflection(l, x, m) || inflection(
    side = LEFT
  if(!inflection(m, x, r) || inflection(
    side = RIGHT

Glanced at book solution, missed that the sorry was sorted in ascending order, and not descending.

find 5 in {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14}
if inflection(l, m, r)   // l < m < r is FALSE && l > m > r is also FALSE
  if (l < m)
    if(x < l)
        side-chosen = RIGHT
    else if( x > m)
        side-chosen = RIGHT
    else
        side-chosen = left // Right has the extremes. Left has others.
  else // l > m
    if (x < m)
        side-chosen = LEFT
    else if (x > r)
        side-chosen = LEFT
    else
        side-chosen = RIGHT // Left has the extremes. Right has others.



else
  if(x > mid)
    side-chosen = RIGHT
  else
    side-chosen = LEFT

 */