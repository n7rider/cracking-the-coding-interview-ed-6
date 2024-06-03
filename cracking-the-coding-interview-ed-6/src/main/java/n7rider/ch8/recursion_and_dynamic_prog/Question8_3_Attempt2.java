package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.HashSet;
import java.util.Set;

/**
 Magic Index: A magic index in an array A [ 0 ••• n -1] is defined to be an index such that A[ i] =
 i. Given a sorted array of distinct integers, write a method to find a magic index, if one exists, in
 array A.
 FOLLOW UP
 What if the values are not distinct?

 After finishing:
 Missed a tricky by using arr[mid-1] and arr[mid+1] in bounds. I was quick to check the book thinking
 they might have missed validations too, but obviously don't. Should just walk through my code next time.
 Looking at my interview experience, not walking through all cases might cost.

 After comparing with solution:
 Same as mine.

 */
public class Question8_3_Attempt2 {
    public static void main(String[] args) {
        System.out.println(MagicIndexFinder.findMagicIdx(new int[] { -10, -5, -3, 0, 2, 3, 6, 27, 28, 30 }));
        System.out.println(MagicIndexFinder.findMagicIdx(new int[] { -10, -5, 2, 2, 2, 3, 4, 7, 9, 12, 13 }));





    }

    static class MagicIndexFinder {
        static int findMagicIdx(int[] arr) {
            return findMagicIdxInternal(arr, 0, arr.length - 1);
        }

        static int findMagicIdxInternal(int[] arr, int a, int l) {
            if(a > l) {
                return -1;
            }

            int mid = (a + l) / 2;
            if (mid == arr[mid]) {
                return mid;
            }

            int bound1 = Math.min(mid - 1, arr[mid]);
            var out1 = findMagicIdxInternal(arr, a, bound1);

            if(out1 != -1) {
                return out1;
            } else {
                int bound2 = Math.max(mid + 1, arr[mid]);
                return findMagicIdxInternal(arr, bound2, l);
            }
        }
    }
}

/*
  0  1  2 3 4 5 6  7  8  9
-10 -5 -3 0 2 3 6 27 28 30  // Sorted

Something like a binary search could work

e.g., in above, mid=4. It isn't the output. Do recursion through 0,3 and 5,9
In 0,3 even the biggest < magic Idx, can we reject these? Yes, for distinct, no for not-distinct

Let's try solving even if no.s are not distinct.

val at 3 = 0
This means nothing greater than 0 will be the answer.
So reduce (0,3) to (0,0) // TODO A logic to add i.e., check Math.min() for inside bounds, Math.max for outside bounds


(5,9)
mid=7 // Not terminal
(5,6) | (8,9)

Take 8,9 first.
 Math.max(lb, val(lb) means this becomes (28, 9) - so we totally skip this.

findMagicIdx(arr)
  findMagicIdxInternal(arr, 0, arr.length - 1)

int findMagicIdxInternal(int[] arr, int a, int l)
  if a > l
    return -1

  int mid = (a + l) / 2
  if mid == arr[mid]
    return mid

  bound1 = Math.min(mid-1, arr[mid-1])
  var out = findMagicIdxInternal(a, bound1)
  if out != -1

    bound2 = Math.max(mid+1, arr[mid+1])
    return findMagicIdxInternal(bound2, l)


(0,0)
mid = 0
(0,-1) | (1,0)

 */