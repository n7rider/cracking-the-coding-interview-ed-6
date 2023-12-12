package n7rider.ch5.bit_manipulation;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;

/**
 * 5.1
 * Insertion: You are given two 32-bit numbers, N and M, and two bit positions, i and
 * j. Write a method to insert M into N such that M starts at bit j and ends at bit i. You
 * can assume that the bits j through i have enough space to fit all of M. That is, if
 * M = 10011, you can assume that there are at least 5 bits between j and i. You would not, for
 * example, have j = 3 and i = 2, because M could not fully fit between bit 3 and bit 2.
 * EXAMPLE
 * Input:
 * N = 10000000000, M = 10011, i = 2, j = 6
 * Output: N = 10001001100
 * Hints: #137, #769, #215
 *
 * After completing:
 * Works but still need to get familiarized with bitwise operations
 *
 * After checking with solution:
 * Missed clearing existing bit in the number
 * Got an idea of doing it altogether instead of doing over a loop
 * Instead of backup and restore, it creates left mask, right mask and then merges them. Create right mask
 * by doing r = 1 << n+1, then r = r - 1. Create left mask using leftMask = ~0 << (j + 1)
 */
public class Question5_1 {

    public static void main(String[] args) {
        int result = insertMintoN_v2(parseInt("10000000000", 2), parseInt("10011", 2),2 , 6);
        assertEquals("10001001100", Integer.toBinaryString(result));

        int result2 = insertMintoN_v2(parseInt("11111111111", 2), parseInt("10000", 2),2 , 6);
        assertEquals("11111000011", Integer.toBinaryString(result2));
    }

    static int insertMintoN_v1(int n, int m, int i, int j) {
        if(m > n) {
            throw new IllegalArgumentException("m > n");
        }

        int mLen = binaryLen(m);
        if(mLen > j - i + 1) {
            throw new IllegalArgumentException("Indexes j and i can't fit m between them");
        }

        for(int x = 0; x < mLen; x++) {
            int lastBit = m & 1;
//            System.out.println(lastBit);
//            int zeroMask = ??;
            int mask = lastBit << (x + i);
            n = n | mask;
            m = m >> 1;
        }

        return n;
    }

    static int insertMintoN_v2(int n, int m, int i, int j) {
        if (m > n) {
            throw new IllegalArgumentException("m > n");
        }

        int mLen = binaryLen(m);
        if (mLen > j - i + 1) {
            throw new IllegalArgumentException("Indexes j and i can't fit m between them");
        }

        int newM = m << i;
        int leftMask = ~0 << (j + 1);
        int rightMask = (1 << i) - 1;
        int mask = leftMask | rightMask;

        n = n & mask;
        n = n | newM;

        return n;
    }

    private static int binaryLen(int m) {
        int c = 0;
        do {
            c++;
            m = m >> 1;
//            System.out.println(Integer.toBinaryString(m));
        }
        while(m > 0);
        return c;
    }
}

/**
 *
 * Assertion:
 * len(n) >= len(m)
 * j-i+1 >=len(m)
 *
 *
 * iterate x from 0 to len(n-1)
 *   let lb = last bit of m i.e., lb = lb & 1
 *   let newIndex = x + i
 *   let lbToN = lb << newIndex
 *   n = n | lbToN
 *   m = m >> 1
 *
 * len(int a):
 *   int c
 *   do
 *     c++
 *     a = a >> 1
 *   while(a > 0)
 *   return c
 *
 * convert
 */


/**
 * After checking solution:
 * Missed clearing existing bit in the number
 * Got an idea of doing it altogether instead of doing over a loop
 *
 * get m ready for imposing into n
 * backup of n's index from 0 to i-1
 * create mask of 10000000 to clear 0-j in n
 * clear bits 0 through j in n
 * do | to impose n in m
 * restore n's index from 0 to i-1
 *
 * int newM = m << i
 * int jBkp = n & (1 << i) // includes i's index but we don't want it
 * n = n & (1 << (j+1))
 * n = n | newM
 * for a = 0 to i - 1
 *   n = n | (jBkp & 1)
 *   jBpk >> 1
 *
 *
 * After checking solution:
 * Instead of backup and restore, it creates left mask, right mask and then merges them
 *
 * get m ready for imposing into n
 * create mask to clear i-j in n
 * clear i-j in n
 * do | to impose n in m
 *
 * int newM = m << i
 * int leftMask = ~0 << j
 * int rightMask = (1 << i) - 1
 * int mask = leftMask | rightMask
 * n = n & mask
 * m = n | m
 *
 *
 */