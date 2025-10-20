package n7rider.ch17.hard;

/**
 * Count of 2s: Write a method to count the number of 2s that appear in all the numbers between O
 * and n (inclusive).
 * EXAMPLE
 * Input: 25
 * Output: 9 (2, 12, 20, 21, 22, 23, 24 and 25. Note that 22 counts for two 2s.)
 *
 * After finishing:
 * Works for a=1 to 9, but breaks for 0.
 *
 * Can replace the if part with adding 0 for idx1, max 1 for idx2 if n>= 10, max 10 for idx3 if n>=100 etc
 *
 * After comparing:
 * My hunch is right, the book replaces the complexity in the if() with two ifs(), one to just return pow10(idx)
 * if dig < 2, else do the actual n%pow10...
 * Also, starting the idx with 1 looked intuitive, but I have a lot of -1 in the code. As a rule of thumb, I guess it is best to keep it 0
 * i.e.,  according to conventions unless there's a strong need that we see later.
 *
 */
public class Solution17_6 {
    public static void main(String[] args) {
        System.out.println(countOfN(2567, 2));
        System.out.println(countOfN(25, 2));
        System.out.println(countOfN(25, 5));
        System.out.println(countOfN(61523, 2));
        System.out.println(countOfN(20, 0)); // Breaks for 0, but not part of the question.
    }

    public static int countOfN(int n, int a) {
        return countOfNHelper(n, a, 1); // Using 1 based everywhere
    }

    private static int countOfNHelper(int n, int a, int idx) {

        if(n < pow10(idx - 1)) { // e.g., if n = 2527, run for 4 times, and stop when 2527 < 10,000
            return 0;
        }

        int count = 0;
        // Whole part
        count = n / pow10(idx) * pow10(idx - 1);

        // Final part for the digit
        if(n % pow10(idx) >= a * pow10(idx - 1)) {
            count = count + Math.min(
                    n % pow10(idx) - a * pow10(idx - 1) + 1,
                    pow10(idx - 1)
            );
        }
        System.out.println("Count so far: " + count);

        return count + countOfNHelper(n, a, idx + 1);
    }

    private static int pow10(int x) {
        return (int) Math.pow(10, x);
    }

}

/*
Brute force solution:
Run a loop i from 0 to n    O(n)
    Check each digit of i
        Rec. division by 10 -- O(log10 n)
    Add counter if digit = 2

This is approx O(n log n)

To improve?
    Find a logic for finding 2s if n is at each digit * n
    e.g., if n = 25, the answer is countOf2(1's digit, 0, 25) + countOf2(2's digit, 10, 25)
    i.e., for i = 0 to n
        countOf2(nth digit, 10^1, n)

    countOf2 - 1's dig (0, n):
        0-10 = 1, 11-20 = 1, 21-25 = 1
        So, output = n/10 + c (where c = 1 if n% 10 >= 2 )

    countOf1 - 2's dig (0, n): (n = 255)
        000-100 = 10, 100-200=10, 200-250=10
        So, output = n/100 + c (to find c, if n%100 >= 20, c= min(n%100 - 20 + 1, 10) else 0

    What's the recursive function here?
        countOf2 - upto ith dig (0, n):
            output = (n/10^i) * 10^(i-1)     + c + countOf2(i-1th dig) // 1 based index. Added the multiplier to the n/10^i
                To find c:
                    if n % 10^i >= 2 * 10^(i-1)   // 1 based index
                        c = min(n % 10^i - 2 * 10^(i-1) + 1, 10^(i-1))
                    else
                        0


    Check if this works for 3rd dig. n = 2527
        1th dig = 2527/10 + 1 = 252 + 1 = 253
        2nd dig = 2527/100 *  10 + 8 = 250 + 8 = 258
        3rd dig = 2527/1000 * 100 + 100 = 200 + 100 = 300
        4th dig = 2527/10000 * 1000 + 527 = 0 + 527 = 527

    Check if this works for n = 25
        1th dig = 25/10 * 1 + 1 = 2 + 1 = 3
        2nd dig = 25/100 * 10 + 6 = 6

        Sum = 9

    This will run in O(log10 n) time

    For a = 0, this will break
    e.g., for n = 200
        1th dig = 200 / 10  * 1  + 0 >= 0 ? min 1, 1 = 20 + 1 = 21
        2nd dig = 200 / 100 * 10 + 0 >= 0 ? min 2, 10 = 20 + 10 = 12

 */