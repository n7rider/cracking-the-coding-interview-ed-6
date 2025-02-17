package n7rider.ch10.sorting_and_searching;

import static org.junit.Assert.assertEquals;

public class Solution10_0_BinarySearch {

    public static void main(String[] args) {

        int[] a = {0, 12, 24, 36, 48, 60, 72, 84, 96 };
        assertEquals(5, binarySearch(a, 60));

        int[] b = {};
        assertEquals(-1, binarySearch(b, 60));

        int[] c = {0, 12, 24, 36, 48, 60, 72, 84, 96 };
        assertEquals(-1, binarySearch(c, 61));
    }

    public static int binarySearch(int[] a, int x) {
        if(a == null || a.length == 0) {
            return -1;
        }
        else {
            return binarySearchUtil(a, x, 0, a.length - 1);
        }
    }

    private static int binarySearchUtil(int[] a, int x, int start, int end) {
        int mid = (start + end) / 2;

        if(start > end) {
            return -1;
        }

        if(a[mid] == x) {
            return mid;
        }

        if(x < a[mid]) {
            return binarySearchUtil(a, x, start, mid - 1);
        } else {
            return binarySearchUtil(a, x, mid + 1, end);
        }
    }
}

/*
int mid = (curr_st + curr_end) / 2
if mid == ele
  return ele

if ele < mid
  return rec_call(curr_st, mid - 1)
else
  return rec_call(mid + 1, curr_end)


rec_call(...)
  return rec_call(a, x, 0, l - 1)

 */
