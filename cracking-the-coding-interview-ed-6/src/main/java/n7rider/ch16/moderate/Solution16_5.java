package n7rider.ch16.moderate;

import static org.junit.Assert.assertEquals;

/**
 * Factorial Zeros: Write an algorithm which computes the number of trailing zeros in n factorial.
 *
 * After finishing:
 * There was a gotcha starting at 5! and I missed it because finding the numbers became too cumbersome.
 * If examples are getting difficult, maybe I can try to divert that time towards the logic to look for a gotcha.
 *
 * After comparing:
 * Forgot to add validation for negative numbers, but the same otherwise.
 */
public class Solution16_5 {
    public static void main(String[] args) {
        assertEquals(1, trailingZeroes(5));
        assertEquals(6, trailingZeroes(25));
        assertEquals(31, trailingZeroes(125));
    }

    public static int trailingZeroes(int n) {
        int tz = 0;
        // Return -1 if n is < 0
        while (n > 0) {
            n = n / 5;
            tz = tz + n;
        }
        return tz;
    }
}
/*
2! = 2
3! = 6
4! = 24
5! = 120
6! = 720
7! = 5040
8! = 40320
9! = 362880
10! = 3628800
..
So, 5!, 10! etc. add a trailing 0

0-4 - 0
5-9 - 1
10-14 - 2
15-19 - 3
20-24 - 4
25-29 - 5 + 1

So, just dividing n / 5 ?

Looked up solution: Apparently this starts from change from 25 onwards, because 25 = 5 * 5, so it adds 2 zeroes.

Algorithm:
tz = 0
while n > 0
  n /= 5
  tz += n

Works for 25! = 5 the normal way + 1 = 6
For 125!, 25 the normal way + 6 = 31, so works
 */
