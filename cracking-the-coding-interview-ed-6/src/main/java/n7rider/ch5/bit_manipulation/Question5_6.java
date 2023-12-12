package n7rider.ch5.bit_manipulation;

import static org.junit.Assert.assertEquals;

/**
 * 5.6
 * Conversion: Write a function to determine the number of bits you would need to flip to convert
 * integer A to integer B.
 * EXAMPLE
 * Input:
 * 29 (or: 11101), 15 (or: 01111)
 * Output:
 * 2
 * Hints: #336, #369
 *
 * After finishing:
 * Can find XOR for a & b, and add 1s. Or can do XOR for each bit
 * The latter needs two zero checks in while(), so went with the former.
 *
 * After checking solution:
 * Same as mine
 */
public class Question5_6 {

    public static void main(String[] args) {
        int a1 = 29, a2 = 15;
        assertEquals(2, bitsToFlip(a1, a2));

        int b1 = 8, b2 = 15;
        assertEquals(3, bitsToFlip(b1, b2));

        int c1 = 8, c2 = 8;
        assertEquals(0, bitsToFlip(c1, c2));

    }

    static int bitsToFlip(int a, int b) {
        int c = a ^ b;
        int count = 0;
        while (c > 0) {
            if((c & 1) == 1) {
                count++;
            }
            c = c >> 1;
        }

        return count;
    }
}

/**
 * 11101 -> 01111
 * Needs 2 bits - index 1 and 4
 *
 * int c = a ^ b
 * int count = 0
 * while c > 0
 *   if ( c & 1 == 1)
 *     count++
 *   c = c >> 1
 * return count
 *
 */