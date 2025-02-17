package n7rider.ch10.sorting_and_searching;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Sorted Search, No Size: You are given an array-like data structure Listy which lacks a size
 * method. It does, however, have an elementAt ( i) method that returns the element at index i in
 * 0( 1) time. If i is beyond the bounds of the data structure, it returns -1. (For this reason, the data
 * structure only supports positive integers.) Given a Li sty which contains sorted, positive integers,
 * find the index at which an element x occurs. If x occurs multiple times, you may return any index.
 *
 * After finishing:
 * We just add another check to binary search. The trick with -1 returned for non-existing elements
 * is to avoid checking if the element is < x since you don't know if it's -1 or a value that is < x.
 *
 * After comparing:
 * The book finds the index, and then starts a binary search. I wanted to do it that way, then
 * wanted to do finish it all in one loop. But I guess either way is fine.
 */
public class Solution10_4 {
    public static void main(String[] args) {
        int[] arr1 = {1, 3, 4, 5, 7, 10, 14, 15, 16, 19, 20, 25};
        System.out.println(findInListy(arr1, 1));

        int[] arr2 = {1, 3, 4, 5, 7, 10, 14, 15, 16, 19, 20, 25};
        System.out.println(findInListy(arr2, 25));

        int[] arr3 = {1, 3, 4, 5, 7, 10, 14, 15, 16, 19, 20, 25};
        assertEquals(1, findInListy(arr3, 3));

        int[] arr4 = {1, 3, 4, 5, 7, 10, 14, 15, 16, 19, 20, 25};
        assertEquals(6, findInListy(arr4, 14));

        int[] arr5 = {1, 3, 4, 5, 7, 10, 14, 15, 16, 19, 20, 25};
        assertEquals(1, findInListy(arr5, 3));

        int[] arr6 = {1, 3, 4, 5, 7, 10, 14, 15, 16, 19, 20, 25};
        assertEquals(3, findInListy(arr6, 5));

        int[] arr7 = {14, 14, 14, 14, 14, 14, 14, 15, 16, 19, 20, 25};
        int result = findInListy(arr7, 14);
        assert(result >= 0 && result <= 6);
    }

    public static int findInListy(int[] arr, int x) {
        Listy listy = new Listy(arr);
        return find(listy, x, 0, 2);
    }

    static int find(Listy listy, int x, int left, int right) {
        System.out.printf("Left | Right: %d | %d \n", left, right);
        if(left > right) {
            return -1;
        }
        if(listy.elementAt(right) != -1 && listy.elementAt(right) < x) {
            System.out.printf("Doubling range to: %d %d \n", right + 1, right * 2);
            return find(listy, x, right + 1, right * 2);
        } else {
            int mid = (left + right) / 2;
            if(listy.elementAt(mid) == x) {
                return mid;
            }
            System.out.printf("Past checking mid i.e., %d\n", listy.elementAt(mid));
            if (listy.elementAt(mid) == -1 || x < listy.elementAt(mid)) {
                return find(listy, x, left, mid - 1);
            } else {
                return find(listy, x, mid + 1, right);
            }
        }
    }

    static class Listy {
        int[] arr;
        Listy(int[] arr) {
            this.arr = arr;
        }

        int elementAt(int i) {
            if(i >= arr.length) {
                return -1;
            } else {
                return arr[i];
            }
        }
    }

}

/*
Listy
 * elementAt(i)
 * There's no size method though

We need to find ele(x)

Simplest solution:
Go in powers of 2, and try to find it

Alg:
find(0, 2, x) // multiple of 2 is efficient for binary search, start with small number to find upper bound.
// If we start from high, there's no limit on where we can start

find(left, right, x)
int mid
if(eleAt(mid) == x)
  return mid
else
  if(eleAt(mid) == -1 || eleAt(mid) > x)
    return find(0, mid - 1)
  else
    return find(mid + 1, right * 2, x) // Can end up doing odd ranges like from 0.. 4 to a new call for 3...8

// Use right always
find(left, right, x)
int mid
if(eleAt(right) == x)
  return right
else
  if(eleAt(right) < x)
    return find(right + 1, right * 2)
  else
    if(x < mid)
        return find(left, mid - 1, x)
    else
        return find(mid + 1, right, x)

 */