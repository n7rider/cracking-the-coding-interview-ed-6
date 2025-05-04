package n7rider.ch16.moderate;

import static org.junit.Assert.assertEquals;

/**
 * Contiguous Sequence: You are given an array of integers (both positive and negative). Find the
 * contiguous sequence with the largest sum. Return the sum.
 * EXAMPLE
 * Input: 2, -8, 3, -2, 4, -10
 * Output: 5 ( i. e • , { 3, -2, 4})
 *
 * After finishing:
 * ---
 *
 * After comparing:
 * I didn't consider the case for -ve numbers (Though the answer is acceptable)
 * The book doesn't use the prevSum. Rather it uses the idea that if sum becomes < 0, the prevSum will never
 * be the max., and can be ignored. Because even if there's a big value to 3, -4, just considering the big value
 * will always be better 3 + (-4) + new-no.
 *
 */
public class Solution16_17 {
    public static void main(String[] args) {
        assertEquals(5, findMaxContiguousSum(new int[] {2, -8, 3, -2, 4, -10}));

        assertEquals(0, findMaxContiguousSum(new int[] {0}));

        assertEquals(200, findMaxContiguousSum(new int[] {2, 10, -100, 7, -1, 5, 11, -32, 200}));

        assertEquals(22, findMaxContiguousSum(new int[] {2, 10, -100, 7, -1, 5, 11, -32}));

        // 0 is supported right off the bat, and the book acks is a valid answer. Need additional logic with initializing
        // sum if we want the actual number
        assertEquals(0, findMaxContiguousSum(new int[]  { - 3, -10, - 5}));
    }

    static int findMaxContiguousSum(int[] a) {
        // Validation: null, empty check

        int sum = 0, prevSum = 0, max = Integer.MIN_VALUE;
        for(int i = 0; i < a.length; i++) {
            if(a[i] >= 0) {
                sum = sum + a[i];
            } else {
                int prevToCurrSum = prevSum + sum;
                max = findMax(sum, prevToCurrSum, max);
                prevSum = Math.max(sum + a[i], prevSum + sum + a[i]);
                sum = 0;
            }
        }

        return Math.max(max, sum);
    }

    private static int findMax(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }
}

/*
Try go for a basic solution

-ve numbers are the only trick factors here, else keep going
So the largest number will always be between boundaries or negative numbers.

Basic alg:
1. Go from start
2. Stop if end is reached or neg number is reached
3. Check if curr.sum > max, and set if true
4. Return max

Caveat: This misses scenarios with very small neg. no.s in the middle but big overall
e.g., output in the given example

what if we remember max, prev. sum, prev. neg, curr sum?
In example, when we are at -2,
prev.sum = 2
prev. neg = -8
curr sum = 3
added-sum = if prev.sum == curr-sum, add curr-sum + curr-index, else add all above = -3 // This is smaller than max, so ignore

Now, prev. sum = Math.max(curr.sum, added-sum) = 3 // If bringing all the history is smaller, just use curr.sum
prev.neg = curr. idx.value i.e., -2
curr sum = 0
added-sum = prev sum = curr-sum, so 1
..

At -10
prev. sum = max (4, 1) = 4
prev. neg = -10 // becomes prev. for the next iteration
curr-sum = 0 // for next iter
added-sum = 1

prev-sum = Math.max(5, 0) = 5
return 5

Algorithm:
For i = 0 to len-1
    if a[i] is not negative
        sum = sum + a[i] // Continue until neg is reached
    else
        // Choose max of 3 contenders - sum, sum + prev-to-curr-sum, max
        // sum is just sum so far
        prev-to-curr-sum = prev-sum + curr-sum
        find and set max.
        // Decide what to store in prev.sum - this iteration or include the past (which might include its own past)
        set prev.sum = max (curr-sum + a[i], prev-sum + curr-sum + a[i])
        sum = 0
    return max (max, sum)
 */