package n7rider.ch5.bit_manipulation;

/**
 * 5.8
 * Draw Line: A monochrome screen is stored as a single array of bytes, allowing eight consecutive
 * pixels to be stored in one byte. The screen has width w, where w is divisible by 8 (that is, no byte will
 * be split across rows). The height of the screen, of course, can be derived from the length of the array
 * and the width. Implement a function that draws a horizontal line from ( xl, y) to ( x2, y).
 * The method signature should look something like:
 * drawline(byte[] screen, int width, int xl, int x2, int y)
 * Hints: #366, #387, #384, #397
 *
 * After coding:
 * Takes (x2-x1)/8 iterations. Without bit manipulation, it would be x2-x1 iterations
 *
 * After comparing with book solution:
 * Added 0xFF for places where all 8 bits are to be flipped.
 */
public class Question5_8 {

    public static void main(String[] args) {
        byte[] screen = new byte[100];

        drawLine(screen, 5, 10, 29, 2);
        printScreen(screen, 5);

    }

    /**
     * Assume width aka width-per-row is always in bytes
     */
    static void drawLine(byte[] screen, int width, int x1, int x2, int y) {
        int index = (width * y) + (x1 / 8);

        do {
            int startPos = x1 % 8;
            int next8Multiple = (x1 /8 + 1) * 8;
            int endPos = next8Multiple > x2 ? (x2 % 8) : 7;

            int bitsCount = endPos - startPos + 1;
            if(bitsCount == 8) {
                screen[index] = (byte) 0xFF;
            } else {
                int mask = (1 << bitsCount) - 1;
                int addLeadingMask = mask << startPos;

                screen[index] = (byte) (screen[index] | (byte) addLeadingMask);
            }

            x1 = next8Multiple;
            index++;
        }
        while(x1 <= x2);



    }

    static void printScreen(byte[] screen, int width) {
        for(int i = 1; i <= screen.length; i++) {
            if(screen[i-1] != 0) {
                System.out.print(String.format("%8s  ", printBitByBit(screen[i-1])));

            } else {
                System.out.print(String.format("%08d  ", screen[i - 1]));
            }

            if(i % width == 0) {
                System.out.println();
            }
        }
    }

    private static String printBitByBit(byte screenSegment) {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < 7; i++) {
            out.append(screenSegment & 1);
            screenSegment = (byte) (screenSegment >> 1);
        }
        return out.toString();
    }
}

/**
 * eg if width = 40 bits, byte[] size = 100, then height = ?
 * width per row in bytes = 5
 * height = 100 / 5 = 20
 *
 * e.g., x1 = 10, x2 = 19, y = 2
 *
 * find pos of X1 in byte[]:
 *   // find the byte[] ele to start
 *   index = width_per_row_in_bytes * y + (x1 / 8);
 *
 *   do
 *     int startPos = x1 % 8
 *     int endPos = ((x1 / 8) + 1) * 8 > x2 ? (x2 % 8) : 7
 *
 *
 *     int mask = (1 << (endPos - startPos + 1)) - 1
 *     int addLeadingMask = mask << startPos // e.g., if we start at 10, you need 0 for 8 and 9.
 *     byte[index] = byte[index] | mask;
 *
 *     x1 = ((x1 / 8) + 1) * 8
 *   while ( x1 <= x2)
 *
 * x1 = 10, x2 = 15. mask = 00 111 111 //exp: mask = 11 111 100
 * x1 = 16, x2 = 19. mask = 00 001 111 //exp: mask = 00 001 111
 *

 */
