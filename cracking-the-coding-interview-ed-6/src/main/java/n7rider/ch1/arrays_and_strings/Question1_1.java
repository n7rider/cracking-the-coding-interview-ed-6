package n7rider.ch1.arrays_and_strings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 1.1
 * Is Unique: Implement an algorithm to determine if a string has all unique characters. What if you
 * cannot use additional data structures?
 * Hints: #44, #7 7 7, #732
 * ---
 * Post-run observations:
 * - The runtime is O(n)
 * <p>
 * After comparing with solution:
 * - Almost similar to the approach there
 */
public class Question1_1 {
    public static void main(String[] args) {

        assertFalse(findIfCharsAreUnique("ascii"));
        assertTrue(findIfCharsAreUnique("Aa"));
        assertTrue(findIfCharsAreUnique("term0-1"));
    }

    private static boolean findIfCharsAreUnique(String s) {
        // Assume strings are non-null and length > 0
        // Assume all chars are from ASCII A-Za-z0-9

        boolean b[] = new boolean[80]; // Hold from ASCII #48 - #122 approx
        for (char c : s.toCharArray()) {
            int i = ((int) c) - 45;
            if (b[i]) {
                System.out.println("Character " + c + " in " + s + " occurs multiple times");
                return false;
            } else {
                b[i] = true;
            }
        }
        System.out.println("All character in string " + s + " are unique.");
        return true;
    }
}
/**
 * Approach:
 * ---
 * <p>
 * for each char c in s
 *   check if h contains c
 *   if yes, quit - NOT UNIQUE
 *   if no, add to h & continue
 * <p>
 * number of char - 255
 * create boolean[255]
 * for each char c in s
 *   convert c to ASCII int i
 *   if boolean[i] == true, quit - NOT UNIQUE
 *   false - set b[i] = true, continue
 */