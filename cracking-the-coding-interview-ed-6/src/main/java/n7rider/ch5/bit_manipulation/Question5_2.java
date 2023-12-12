package n7rider.ch5.bit_manipulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * 5.2
 * Binary to String: Given a real number between O and 1 (e.g., 0.72) that is passed in as a double, print
 * the binary representation. If the number cannot be represented accurately in binary with at most 32
 * characters, print "ERROR:'
 * Hints: #743, #767, #7 73, #269, #297
 *
 * After completing:
 * Works
 *
 * After checking with solution:
 * Replace while(true) & break with while(condition)
 * Rather than doing modulus and casting to int, do n > 1 check to find the number to be appended to binary
 */
public class Question5_2 {
    public static void main(String[] args) {
        assertEquals("0.11", binaryOfFraction(0.75));
        assertThrows(IllegalArgumentException.class, () -> binaryOfFraction(0.33));
    }

    static String binaryOfFraction(double n) {
        if(n < 0 || n >= 1) {
            throw new IllegalArgumentException("Number outside limits");
        }

        StringBuilder out = new StringBuilder("0.");
        int count = 0;
        while(n != 0.0) {
            if(count == 32) {
                throw new IllegalArgumentException("Number too long to find fraction");
            }

            n = n * 2;
            if (n >= 1.0) {
                out.append(1);
                n -= 1.0;
            } else {
                out.append(0);
            }

            System.out.println("out becomes " + out);
            count++;

            System.out.println("n becomes " + n);;
        }
        return out.toString();
    }
}

/**
 * Convert fraction to binary e.g. 0.72
 * If it can't be finished after 32 digits, throw error
 * out = 0.
 * loop:
 *   n = n * 2
 *   out = out + str(n / 1)
 *   n = n % 1
 *   count++
 *   if(n == 0) quit
 *   if count = 32 throw error
 * return out
 *
 * things unknown:
 * n % 1 - will ti work for float?
 *
 *
 * out = 0.
 * loop:
 *   n = n * 2
 *   out = out + str(n / 1)
 *   n = n % 1
 *   count++
 *
 *   if(n == 0) quit
 *   if(count == 32) throw error
 *   return out
 *
 * things unknown:
 * n % 1 - will it work for float?
 */