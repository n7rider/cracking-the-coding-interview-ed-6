package n7rider.ch5.bit_manipulation;

import static org.junit.Assert.assertTrue;

/**
 * 5.5
 * Debugger: Explain what the following code does: ( ( n & ( n-1)) == 0).
 * Hints:#757,#202,#267,#302,#346,#372,#383,#398
 */
public class Question5_5 {
    public static void main(String[] args) {
        int n1 = 8;
        int result1 = n1 & (n1 - 1);
        assertTrue(result1 == 0 && n1 % 2 == 0);

        int n2 = 512;
        int result2 = n2 & (n2 - 1);
        assertTrue(result2 == 0 && n2 % 2 == 0);
    }
}

/**
 * Checked solution.
 *
 * An example might be:
 * 111 & 110 won't work
 * 111 & 1000 works
 * 1010 & 0101 returns 0 but these are not consecutive numbers
 *
 * For consecutive numbers, only 1000 & 111, 100 & 11 etc. works
 * Therefore, n is a multiple of 2.
 */
