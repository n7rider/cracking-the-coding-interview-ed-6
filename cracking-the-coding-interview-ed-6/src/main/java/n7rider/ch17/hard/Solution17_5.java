package n7rider.ch17.hard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Letters and Numbers: Given an array filled with letters and numbers, find the longest subarray with
 * an equal number of letters and numbers.
 *
 * After finishing:
 * I was earlier doing this with two for loops, but shrank it to 1. The array that encoded the diff is not needed
 * after merging the loops.
 * Also, I realized if we set 1 to a char arr's element, it is shorthand for ASCII 1. For the numeric 1, either
 * set it as '1' or use 49.
 *
 * I got this from idea from Solution16.10. It runs in O(n) time.
 *
 * After comparing:
 * Same as mine. The book kept the two loops separate - most likely because it is still O(n).
 *
 * A useful way to simplify while reducing the brute force solution is to try to start from biggest subarray to smallest.
 * This way, if you find one matching the condition, we can stop right away.
 */
public class Solution17_5 {
    public static void main(String[] args) {
        // Longest is B C
        char[] arr;

        arr = new char[] { 'A', 'B', 'C', '1', '2', '3', 'D', 'E', '4', '5', '3', '2' };
        System.out.println(Arrays.toString(findLongestSubArray(arr)));

        arr = new char[] { 'A', 'B', 'C', '1', '2'};
        System.out.println(Arrays.toString(findLongestSubArray(arr)));

        arr = new char[] { 'A', 'B', 'C', 'D', '1', '2'};
        System.out.println(Arrays.toString(findLongestSubArray(arr)));

        // 4 5  6  7  F G H
        arr = new char[] { 'A', 'B', 'C', '1', '2', '3', 'D', 'E', '4', '5', '6', '7', 'F', 'G', 'H' };
        System.out.println(Arrays.toString(findLongestSubArray(arr)));

        arr = new char[] { 'A', 'B', 'C', 'F', 'G', 'H' };
        System.out.println(Arrays.toString(findLongestSubArray(arr)));
    }

    public static char[] findLongestSubArray(char[] arr) {
        // Validation - arr not null or empty
        int curr = 0;
        int max = -1;
        int start = -1, end = -1;

        Map<Integer, Integer> lastIdxMap = new HashMap<>();
        // Diff is 0 at 0, but this is not in the array, so manually adding this.
        // Value is -1 here because we start the range from start+1, explained in the comment near constructing out
        lastIdxMap.put(0, -1);

        for (int i = 0; i < arr.length; i++) {
            if (isNumeric(arr[i])) {
                curr--;
            } else {
                curr++;
            }

            Integer subArrayStart = lastIdxMap.get(curr);
            if (subArrayStart == null) {
                lastIdxMap.put(curr, i);
            } else {
                if (i - subArrayStart > max) {
                    max = i - subArrayStart;
                    start = subArrayStart;
                    end = i;
                }
            }
        }

        if (max != -1) {
            /* Why start from start + 1? Because the change in length takes place from next letter. Consider idx start=0, end=4 is the output
            A B C 1 2.
            1 2 3 2 1
            If we take range [0, 4], we'll get one more alpha. But [1, 4] actually gives an equal length for alpha, num.
            The reason is, the init state starts (diff = 1) starts after A is set, so when we try to find the range within which diff = 1,
            we need to consider only elements after A.
            */
            char[] out = new char[end - start];
            System.arraycopy(arr, start + 1, out, 0, end - start);
            return out;
        } else {
            return null;
        }
    }


    private static boolean isNumeric(char c) {
        return c >= 48 && c <= 57;
    }
}

/*
A B C 1 2 3 D E 4 5 3 2

Simplest solution:
for i = 0 to n-1
    for j = 1 to n-1
        consider subarray a[i to j]
        compute count of letters and numbers
        compare and set as largest if count of both are same
return largest

Cons:
    O(n^2) iterations, O(n) for counting, so total is O(n^3)

Can be done:
    Reduce iterations -> by making incremental additions and compare?
    Create a reduced array version for future run through?

Incremental additions across two variables? How about always keeping the diff in a new array

e.g., with alph-num, the above input becomes 1 2 3 2 1 0 1 2 1 0 -1 -2 -1 0 1
                                             A B C 1 2 3 D E 4 5  6  7  F G H

0 to 0 are not the only valid outputs, any n to n are.
e.g., 1 to 1 from B C 1 2 => only exception is start from next number for the subarray. (Even with 0, we do it, we start from A where
alph becomes 1).

So, iterate through the new array and create a third array that stores max count for each num.
arr3 length = max value in arr2 (find out when creating arr2)

need to preserve max, then last. max can be universal, so last can be in arr3

arr3 - init with -1
int a, l = 0, max = 0
for int k = 0 to n-1
    if arr3[arr2[k]] != -1
        int len = k - arr3[arr2[k]]
        if len > max
            set len = max
            l = k, a = arr3[arr2[k]]



 */