package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 Magic Index: A magic index in an array A [ 0 ••• n -1] is defined to be an index such that A[ i] =
 i. Given a sorted array of distinct integers, write a method to find a magic index, if one exists, in
 array A.
 FOLLOW UP
 What if the values are not distinct?

 After finishing:
 I tried adding a cache but it won't help since each number and each set of a,l is called only once

 After comparing with solution:
 Binary search is best done with (a, mid-1) | (mid+1, l) in the recursive cases. I missed that.
 I also didn't consider moving index on one side of the variable if mid idx is smaller or bigger than mid value
 I almost did it correct but I'd like to do it again to revise binary sort and working around it.
 */
public class Question8_3 {
    public static void main(String[] args) {
        System.out.println(MagicIndexFinder.findMagicIndex(new int[] { -3, -1, 2, 2, 2, 5, 8, 9}));

        System.out.println(MagicIndexFinder.findMagicIndex(new int[] { -10, -5, 2, 2, 2, 3, 4, 7, 9, 12, 13 }));

        System.out.println(MagicIndexFinder.findMagicIndex(new int[] { -10, -5, 1, 2, 2, 3, 4, 8, 9, 12, 13, 14, 15, 16, 16, 20 }));

    }

    static class MagicIndexFinder {
        static Set<String> excludeSet;
        static int findMagicIndex(int[] arr) {
            // Validations for arr length
            excludeSet = new HashSet<>();
            return findMagicIdxInternal(arr, 0, arr.length - 1);
        }

        static int findMagicIdxInternal(int[] arr, int a, int l) {
            int midIdx = (a + l) / 2;

            if(excludeSet.contains(a + ":" + l)) {
                System.out.println(String.format("%d %d in exclude list. Skipping", a, l));
                return -1;
            }

            if(arr[midIdx] == midIdx) {
                return midIdx;
            } else if(a == l) {
                return -1;
            } else if (arr[a] > l) {
                System.out.println(String.format("Skipped due to impossible scenario: arr[%d] has %d, arr[%d] has %d", a, arr[a], l, arr[l]));
                System.out.println(String.format("Add %d %d to exclude list", a, l));
                excludeSet.add(a + ":" + l);
                return -1;
            } else {
                // int leftIdx = Math.min(midIdx, arr[midIdx]); // Added after looking at solution
                int result1 = findMagicIdxInternal(arr, a, midIdx);
                if(result1 != -1) {
                    System.out.println(String.format("Add %d %d to exclude list", a, l));
                    excludeSet.add(a + ":" + l);
                    return result1;
                }

                // int rightIdx = Math.max(midIdx, arr[midIdx]);  // Added after looking at solution
                int result2 = findMagicIdxInternal(arr, midIdx, l);
                if(result2 != -1) {
                    System.out.println(String.format("Add %d %d to exclude list", a, l));
                    excludeSet.add(a + ":" + l);
                }
                return result2;
            }
        }


    }
}

/*
I looked at the solution before starting this. I was not 100% sure what the magic index meant.
Also, I may not have realized how significant having not distinct values is.

Now I see that we need to find if A[i] = i exists, and find it if it does. That's magic index.

Brute force is the simplest, but the array is sorted.
If values are distinct, we can do a simple binary search

findMagicIdx(arr)
  findArrInternal(arr, 0, arr.length - 1)
  cache[]

findArrInternal(int[] arr, int a, int l)
  int midIdx = (a + l) / 2;
  int midVal = arr[midIdx];


  if midVal in cache
    return -1

  if(arr[midIdx] == midIdx)
    return midIdx
  else if(a == l)
    add midVal To Cache
    return -1 // Reached the end of the line e.g., (0,2) will become (0,1)(2,2). (0,1) becomes (0,0)(1,1) and we can stop here to prevent infinite recursions
  else
     //if midIdx = 5, assume value = 55. We might find a (56, 56) or a (4,4). so lookup both sides
     // if midIdx has no reminder, you can put midIdx | midIdx+1 in another e.g., 0,4.
     // However, if it has reminder e.g., 0, 3. You should put midIdx | midIdx+1. Using midIdx-1 in first, midIdx in next will make it imbalanced
    if(arr[a] <= a || arr[mid] <= mid)
      int result1 = findArrInternal(arr, a, midIdx)
        if(result1 != -1)
          return result1

    if(arr[mid] >= mid || arr[l] >= l)
      int result2 = findArrInternal(arr, midIdx + 1, l)
        if(result2 != -1)
          return result2



What if items are distinct?

Idx  0  1 2 3 4 5 6 7
Val -3 -1 2 2 2 5 8 9

// ! arr[a] > a && ! arr[mid] > mid . Not going to work
Idx 0      4     8
Val 100   100   120

Idx 0      4     8
Val 100   -15   7


 */