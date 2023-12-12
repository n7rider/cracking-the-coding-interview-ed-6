package n7rider.ch5.bit_manipulation;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * 5.4
 * Next Number: Given a positive integer, print the next smallest and the next largest number that
 * have the same number of 1 bits in their binary representation.
 * Hints: #747, #7 75, #242, #372, #339, #358, #375, #390
 *
 * After completing:
 * Runs in O(b) - b is the time to find pattern
 *
 * After checking solution:
 * Similar
 */
public class Question5_4 {
    public static void main(String[] args) {

        Result result1 = nextNumbers(38); // 100110
        assertTrue(result1.nextSmallest == 37 && result1.nextLargest == 42);
        assertThrows(IllegalArgumentException.class, () -> nextNumbers(63));
    }

    static class Result {
        int nextLargest;
        int nextSmallest;

        public Result(int nextLargest, int nextSmallest) {
            this.nextLargest = nextLargest;
            this.nextSmallest = nextSmallest;
        }
    }

    static Result nextNumbers(int n) {
        int[] patternIndexes = validateAndFindIndex(n);

        int nextLargest = n;
        nextLargest = nextLargest | (1 << (patternIndexes[1] + 1));
        nextLargest = nextLargest & ~(1 << (patternIndexes[1]));

        int nextSmallest = n;
        nextSmallest = nextSmallest | (1 << (patternIndexes[0]));
        nextSmallest = nextSmallest & ~(1 << (patternIndexes[0] + 1));

        return new Result(nextLargest, nextSmallest);
    }

    private static int[] validateAndFindIndex(int n) {
        int c = 0;
        int[] result = new int[2];
        boolean leftZero = false;
        boolean onesPtn = false;
        boolean rightZeroPtn = false;

        while(n > 0 && !leftZero) {
            if(!rightZeroPtn) {
                if((n & 1) == 0) {
                    rightZeroPtn = true;
                }
            } else if(!onesPtn) {
                if((n & 1) == 1) {
                    onesPtn = true;
                    result[0] = c - 1;
                }
            } else if(!leftZero) {
                if((n & 1) == 0) {
                    leftZero = true;
                    result[1] = c - 1;
                }
            }

            n = n >> 1;
            c++;
        }

        if(leftZero) {
            return result;
        } else {
            throw new IllegalArgumentException("Can't find results for this number");
        }
    }
}

/**
 * Simplest way:
 *
 * int x = findOnes(c)
 * findNextLargest:
 *   do
 *     n = n+1
 *     c = findOnes(n)
 *   while(x == c)
 *
 * findNextSmallest:
 *   do
 *     n = n - 1
 *     c = findOnes(n)
 *   while(x == c)
 *
 * findOnes(n)
 *   int c = 0
 *   while n > 0
 *     c = c + (n & 1)
 *     n = n >> 1
 *
 * // Checked book solution
 * // The book solution inspects bits and goes for a solution
 * n = 1000100101010
 * Can we inspect last 'n' bits to find solution?
 *
 * e.g., if crop(n) = 1010, nextLargest (with same 1s) = 1100, nextSmallest = 1001
 * n = 010, nl = 100, ns = 001
 * n = 100, nl = 1000 (not possible), ns = 010
 * but if n = 0100, n1 = 1000, ns=010
 *
 * n = 0011, nl = 010, ns = not possible
 * n = 001, nl = 010, ns = not possible
 *
 * n = 111, nl = not possible, ns = not possible
 * n = 110, nl = not possible, ns = 101
 * So, we need 01*0* (0 before 1* is needed for NL), (0 after 1* is needed for NS)
 *
 * Algorithm:
 * ptn = findReqPattern(n) // Find last few bits solving 01*0*
 * nl = findNextLargestPtn(ptn) // Perform the above simple way on these bits
 * n2 = findNextSmallestPtn(ptn)
 * mergePtnToNum(nl) // apply the output on the actual num
 * mergePtnToNum(ns)
 *
 * // Book solution suggests just flip 0s and 1s instead of adding to find NL
 * Validate if n has 01*0* pattern
 * For NL, find 01* pattern and flip 1 and 0
 * For NS, find 1*0* pattern and flip 1 and 0
 *
 * validate:
 *   bool leftZero, onePtn, rightZeroPtn = false
 *   int ptnIndex[2]
 *   bool currPtn
 *   int c = 0
 *   while n > 0 && !leftZero
 *
 *     if(!rightZeroPtn)
 *       if(n & 0 == 0)
 *         rightZeroPtn = true
 *
 *       else
 *         continue
 *
 *     if(!onePtn)
 *       if(n & 1 == 1)
 *         onePtn = true
 *         ptnIndex[0] = c - 1 // index of end of rightZeroPtn
 *       else
 *         continue
 *
 *     if(n & 0 == 0)
 *       leftZero = true
 *       ptnIndex[1] = c - 1 // index of end of onePtn
 *
 *     n = n >> 1
 *     c++
 *
 *   if(!leftZero)
 *      throw
 *   else
 *      return ptnIndex
 *
 *
 * NL-flip:
 *  nl = nl | (1 << (ptnIndex[1] + 1)) // Flip 0 to 1
 *  nl = nl & ~(1 << ptnIndex[1]) // Flip 1 to 0
 *
 * NS-flip:
 *  ns = ns | (1 << ptnIndex[0]) // Flip 0 to 1
 *  ns = ns & ~(1 << ptnIndex[1] + 1) // Flip 1 to 0
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */