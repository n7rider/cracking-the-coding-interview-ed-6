package n7rider.ch5.bit_manipulation;

import static org.junit.Assert.assertEquals;

/**
 * 5.3
 * Flip Bit to Win: You have an integer and you can flip exactly one bit from a 0 to a 1. Write code to
 * find the length of the longest sequence of ls you could create.
 * EXAMPLE
 * Input:
 * 1775
 * Output:
 * 8
 * (or: 11011101111)
 * Hints: #759, #226, #374, #352
 *
 * After completing:
 * Works
 *
 * After checking with solution:
 * Similar to mine
 */
public class Question5_3 {
    public static void main(String[] args) {
       assertEquals(8, flipToWin(1775)); // 11011101111
       assertEquals(3, flipToWin(2730)); // 101010101010
    }

    static int flipToWin(int n) {

        int currLen = 0;
        int maxLen = 0;
        int lastZeroIdx = -1;

        for(int mask = 1, i = 0; mask <= n; mask = mask << 1, i++) {
            int currBit = n & mask;

            if(currBit == 0) {
                if(currLen > maxLen) {
                    System.out.println(String.format("Found a new max %d ending at index %d", currLen, i));
                    maxLen = currLen;
                }

                currLen = i - lastZeroIdx;
                lastZeroIdx = i;
            } else {
                currLen++;
            }
        }

        return maxLen;
    }
}

/**
 * while i = LSB to MSB
 *   if i == 0
 *     flip it
 *     find length from prev 0 to curr
 *     if currLen > max
 *       max = currLen
 *
 *  return max
 *  ---
 *
 *  mask = 1
 *  currLen = 0
 *  max = 0
 *  int lastKnown0 = -1
 *  while mask <= n // use for loop?
 *    int temp = n & checker
 *    if temp == 0
 *
 *      if(currLen > max)
 *        sout Found a new max ending at index idx
 *        max = counter
 *
 *      currLen = idx - lastKnown0
 *      lastKnown0 = idx
 *
 *
 *
 *    else
 *      currLen++
 *
 *    mask = mask << 1
 *
 */