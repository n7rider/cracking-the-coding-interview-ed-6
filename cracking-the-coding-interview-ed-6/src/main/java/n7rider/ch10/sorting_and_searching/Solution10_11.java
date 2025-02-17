package n7rider.ch10.sorting_and_searching;

import java.util.Arrays;

/**
 * Peaks and Valleys: In an array of integers, a "peak" is an element which is greater than or equal to
 * the adjacent integers and a "valley" is an element which is less than or equal to the adjacent integers.
 * For example, in the array {5, 8, 6, 2, 3, 4, 6}, {8, 6} are peaks and {5, 2} are valleys. Given an array
 * of integers, sort the array into an alternating sequence of peaks and valleys.
 * EXAMPLE
 * Input: {5, 3, 1, 2, 3}
 * Output: {5, 1, 3, 2, 3}
 *
 * After finishing:
 * Runs in O(n) time. Is simpler once you realize that everytime you create a peak at 'B' in sequence ABCD, you
 * make C a valley on one side automatically. If C<D, the valley is complete. Else if D<C, switch them around to
 * complete the valley.
 *
 * After comparing:
 *
 */
public class Solution10_11 {
    public static void main(String[] args) {
        int[] a = {5, 3, 1, 2, 3};
        createPeaksAndValleys(a);
        System.out.println(Arrays.toString(a));

        int[] b = {5, 3, 6, 2, 1}; // 5, 3, 6, 1, 2
        createPeaksAndValleys(b);
        System.out.println(Arrays.toString(b));

        int[] c = {55, 33, 21, 11, 13, 14, 15, 18, 43};
        createPeaksAndValleys(c);
        System.out.println(Arrays.toString(c));

        int[] d = {2, 1, 1, 1, 1, 2};
        createPeaksAndValleys(d);
        System.out.println(Arrays.toString(d));
    }

    static void createPeaksAndValleys(int[] a) {
        // TODO Null check for array
        for(int i = 0; i < a.length - 1; i++) { // For size 5, stop at idx 3. There's nothing to compare a[4] with.
            if(a[i] > a[i + 1]) {
                ensure(Type.VALLEY, a, i + 1); // Ensure we have a valley at this idx
            } else {
                ensure(Type.PEAK, a, i + 1); // Ensure we have a peak at this idx
            }
        }
    }

    private static void ensure(Type requiredType, int[] a, int i) {
        if(i == a.length - 1) { // There is nothing after this ele
            return;
        }
        if(requiredType == Type.VALLEY) {
            if(a[i] > a[i + 1]) {
                swap(a, i, i + 1);
            }
        } else { // Peak is needed at this idx
            if(a[i] < a[i + 1]) {
                swap(a, i, i + 1);
            }
        }
    }

    private static void swap(int[] a, int idx1, int idx2) {
        int temp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = temp;
    }

    enum Type {
        PEAK, VALLEY
    }


}

/*
Input:
|
|
|  |        |
|  |     |  |
|  |  |  |  |

Output:
|
|
|     |      |
|     |  |   |
|  |  |  |   |

Simplest solution:

check either side to find if it's peak or a valley or nothing (first ele will always a P or a V)
if P or V
    set last_type = P or V
    repeat
else
    set temp_type = P or V (based on prev element)
    if temp_type = P
      while loop until we find arr[i] > curr
      swap arr[i] with curr + 1
      repeat

Questions:
Can we just keep swapping until we find what we want 55 33 21 11 13 14 15 18 43
We need to swap 21 and 43 to make it 55 33 43 11 13 14 15 18 21
Here, at 13, you need a smaller no. on the right, but you won't get any.
But, the algorithm can just swap 13 and 14. In the prev case, similarly, we can swap 33 and 21 to make it
 55 21 33 11 13 14 15 18 43

So, each peak creates a valley, so as long as create peaks, we're good. Valleys can take care of themselves.

So, when we find 5, 3, 1, 2, 3
Need a peak at index 2? Just swap index 1 and 2.

Consider 1, 3, 5, 2, 3
Need a valley at index 2? Just swap index 2 and 1.

So, the underlying idea is we start with a P or V.
Consider, we start with P, we'll get P V. If you see a P next, then it's all good.
If you see a V next, then swap index 1 and 2.
Same applies for starting with V.

So the algorithm is:

Compare arr[i] and arr[i+1]
for
    if arr[i] > arr[i+1]
        TYPE_WANTED = VALLEY
        sort(VALLEY, arr, i + 1) // need a valley here, ensure it
    else
        TYPE_WANTED = PEAK
        sort(VALLEY, arr, i + 1) // need a valley here, ensure it
end

sort(TYPE_WANTED, arr, idx)
  if TYPE_WANTED = VALLEY
    if (arr[idx] > arr[idx+1] // TODO null checks
      swap these
  if TYPE_WANTED = PEAK
    if( arr[idx] < arr[idx+1])
      swap these

 */
