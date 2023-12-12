package n7rider.ch5.bit_manipulation;

import static org.junit.Assert.assertEquals;

/**
 * 5.7
 * Pairwise Swap: Write a program to swap odd and even bits in an integer with as few instructions as
 * possible (e.g., bit 0 and bit 1 are swapped, bit 2 and bit 3 are swapped, and so on).
 * Hints: #745, #248, #328, #355
 *
 * After finishing solution:
 * My solution takes two bits at a time, find its new state, clears these bits in the old number, and apply
 * the new pattern. So it takes O(2 log n) time
 *
 * After comparing with book:
 * Should know how to use hexadecimal numbers to create masks such as 101010..., 010101 etc. easily
 * Remember int has 32 bits (4 bytes), so you can create masks covering a whole int
 *
 * >> just shifts the sign bit, but >>> shifts the sign bit too. The book uses >>> to make sure the sign bit is 0
 * Changing sign bit isn't needed even for negative numbers for e.g., -8 is 1111 1111 1111 1111 1111 1111 1111 1000
 * Its flipped form is 1111 1111 1111 1111 1111 1111 1111 0100 which is -12.
 * However, if we just use >>, the output is still -12. However, the individual shifts are odd.
 * With >>>, odd shifted bits represent 1431655764, even shifted bits -1431655776, the final OR is -12
 * With >>, odd shifted is -715827884, even shifted is -1431655776, but the final OR is still -12
 * Other than the sign bit, 1431655764 and -715827884 have the same binary representation. I'm not sure choosing
 * between a >> and >>> matters.
 */
public class Question5_7 {
    public static void main(String[] args) {
        int n1 = 10; // 00000 ..... 1010
        assertEquals(5, flipConsecutiveBits(n1));

        int n2 = -8; // 00000 ..... 1010
        assertEquals(-12, flipConsecutiveBits(n2));
    }

    static int flipConsecutiveBits(int n) {
        int oddMask = 0xaaaaaaaa;
        int oddShiftedN = (n & oddMask) >>> 1;
        System.out.println("Odd shifted n is: " + oddShiftedN);

        int evenMask = 0x55555555;
        int evenShiftedN = (n & evenMask) << 1;
        System.out.println("Even shifted n is: " + evenShiftedN);

        int shiftedN = oddShiftedN | evenShiftedN;
        return shiftedN;
    }
}

    /** * oddMask = 0x aaaa aaaa // 8*4 = 32 bits = 4 bytes is the length for int in Java.
     * int oddShiftedN = (x & oddMask) >>> 1 // Uses Logical right shift instead of arithmetic. This ignores the sign bit
     * evenMask = 0x 5555 5555
     * int evenShiftedN = (x & evenMask) <<< 1
     *
     * int shiftedN = oddShiftedN | evenShiftedN
}

/**
 * Example:
 * a = 01 01 01 becomes 10 10 10
 * b = 11 01 10 becomes 11 10 01
 *
 * 00 becomes 00
 * 01 becomes 10
 * 10 becomes 01
 * 11 becomes 11
 *
 * q = AB_ + A_B
 *
 * 01 ^ 11 = 10
 * 10 ^ 11 = 01
 *
 * This is what we want. We need to XOR the bits with mask 11 to get the flipped value.
 * We just need to not do anything for 00 and 11.
 *
 * 00 ^ 11 = 11 (Just check if currTwo = 0)
 * 11 ^ 11 = 00 (Just check if xOr result == 0)
 *
 * This finds if the curr two bits are 01 or 01, and whether we flip or not.
 *
 * How do we make the actual change in the number?
 * e.g., c =    10 00 11 10 10 00
 *      mask             11 00 00
 *      currTwo          10 00 00
 *
 *      xorResult        01 00 00
 *
 *      currTwo~         01 11 11
 *
 * mask is always 11
 * c = 00, mask = 11, new_c = 00 (A & B)
 * c = 01, mask = 11, new_c = 10 (A_ & B)
 * c = 10, mask = 11, new_c = 01 (A_ & B)
 *
 * e.g., c =    10 00 11 10 10 00
 *      mask                11 00
 *      currTwo             10 00
 *
 *      xorResult           01 00
 *
 *      currTwo~      ...11 01 11
 *
 * mask is always 11
 * c = 00, mask = 11, new_c = 00 (A & B)
 * c = 01, mask = 11, new_c = 10 (A_ & B)
 * c = 10, mask = 11, new_c = 01 (A_ & B)
 *
 *
 * Simplest way:
 * int mask = (1 << 2) - 1
 * do
 *   int currTwo = c & mask
 *
 *   int xorResult = currTwo ^ mask
 *
 *   if(currTwo != 0 && xorResult != 0) {
 *      c = c & (~currTwo) + xorResult // The & wipes the two numbers, xorResult has the flip result
 *   }
 *
 *   mask = mask << 2
 * while (mask > (c << 1));
 *
 * --- Checked book solution after finishing my algorithm.
 *
 * It is simpler. It shifts odd bits right by 1, shifts even bits left by 1, then adds both
 *
 * To shift odd bits right by 1, we need a mask to get all odd numbers. Mask = ......10101010
 * 1010 = 0x a, therefore the book uses aaaa aaaa for this.
 * Similarly, even no mask = ....01010101
 * 01001 = 0x 5, therefore the book uses 5555 5555 for this.
 *
 * oddMask = 0x aaaa aaaa // 8*4 = 32 bits = 4 bytes is the length for int in Java.
 * int oddShiftedN = (x & oddMask) >>> 1 // Uses Logical right shift instead of arithmetic. This ignores the sign bit
 * evenMask = 0x 5555 5555
 * int evenShiftedN = (x & evenMask) <<< 1
 *
 * int shiftedN = oddShiftedN = evenShiftedN
 *
 *
 */
