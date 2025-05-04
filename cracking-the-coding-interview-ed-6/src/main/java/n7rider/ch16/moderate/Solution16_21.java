package n7rider.ch16.moderate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Sum Swap: Given two arrays of integers, find a pair of values (one value from each array) that you
 * can swap to give the two arrays the same sum.
 * EXAMPLE
 * Input: {4, 1, 2, 1, 1, 2} and {3, 6, 3, 3}
 * Output: {1, 3}
 *
 * After finishing:
 * Can't see a better way runtime, but once can make the while loop recursive if we want to make it look simpler.
 *
 * After comparing:
 * It's the look. The book also argues that sorting the arrays and then comparing isn't too bad, and is worth
 * discussing. The runtime will be O(a log b + b log b), and still better than the initial O(a * b).
 *
 */
public class Solution16_21 {
    public static void main(String[] args) {
        int[] m1 = {4, 1, 2, 1, 1, 2};
        int[] m2 = {3, 6, 3, 3};
        int[] result1 = findValuePair(m1, m2);
        assertTrue(result1[0] == 1 && result1[1] == 3);
    }

    static int[] findValuePair(int[] m1, int[] m2) {

        // Validate for null, empty arrays

        Set<Integer> m1Set = new HashSet<>();
        int s1 = Arrays.stream(m1).map(ele -> {
            m1Set.add(ele);
            return ele;
        })
                .sum();
        int s2 = Arrays.stream(m2).sum();

        int diff = s1 - s2;
        // a + b = diff / 2
        // a = (diff / 2) - b

        int i = 0;
        while (i < m2.length) {
            int currB = m2[i];
            int expA = (diff / 2) + currB;

            if(m1Set.contains(expA)) {
                return new int[] { expA, currB };
            }
            i++;
        }

        return new int[] {};
    }
}
/*
>Find one value from each array that you can swap to give the two arrays the same sum
Not sure I got that. If you swap 1 and 3, the sum a[0] changes from 11 -> 17, a[1] changes from 15 to 9.

Going to read the solution.

Read the explanation. If you swap only one 1 and 3, sum a[0] changes from 11 -> 13, a[1] changes from 15 to 13.

So, we want a value such that sum(m1) - a + b = sum(m2) - b + a

Simplest solution:
sum(m1), sum(m2)
We need a and b  where sum(m1) - sum(m2) = 2 ( a - b)
So a - b = diff / 2

Find sum of both matrices - O(n) each
Sort both arrays - O(n log n)
Compare one by one until we find a + b. - In terms of O(n ^2) ave but will be much smaller

how about we simplify this?
Using a hash map <Int, boolean> to add entries from m1 and see if m2 can find something taht fits?

Go through m1 - add each entry to hash map (actually a set would do)
Go through m2
    for each entry curr_b
        find a  i.e., a = diff/2 + b
        Check if a exists in the set
        Return ele

Total run time is O(n)




 */