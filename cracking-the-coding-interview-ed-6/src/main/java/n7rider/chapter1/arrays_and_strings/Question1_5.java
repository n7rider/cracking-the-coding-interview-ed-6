package n7rider.chapter1.arrays_and_strings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * One Away: There are three types of edits that can be performed on strings: insert a character,
 * remove a character, or replace a character. Given two strings, write a function to check if they are
 * one edit (or zero edits) away.
 * EXAMPLE
 * pale, ple -> true
 * pales, pale -> true
 * pale, bale -> true
 * pale, bake -> false
 * Hints:#23, #97, #130
 * <p>
 * Post-run observations:
 * - Runtime is O(n+m+26) i.e., O(n+m)
 * <p>
 * Optimizations:
 * - Use bitwise operators but boolean too uses limited memory
 * <p>
 * After comparing with solution:
 * - I got it right that insertion/removal are alike but replacement is different
 * - My solution runs through the loop twice. In this case, creating method actually hurts. Just use a for/while loop to do both
 * - I counted overall bool count but, it doesn't matter. With one replacement, you just need to look this side or that side.
 *      That's because I missed the logic that replacement needs string with equal length. Add/remove needs diff of 1.
 * //TODO TRY AGAIN with a logic like the one there
 *
 */

public class Question1_5 {

    public static final int ASCII_LOWERCASE_A = 97;

    public static void main(String[] args) {
        assertTrue(isOneEditAway("pale", "ple"));
        assertTrue(isOneEditAway("pales", "pale"));
        assertTrue(isOneEditAway("pale", "bale"));
        assertFalse(isOneEditAway("pale", "bake"));
        assertFalse(isOneEditAway("palers", "ple"));
    }

    private static boolean isOneEditAway(String s1, String s2) {
        // Skipping null checks
        if (Math.abs(s1.length() - s2.length()) > 1) {
            return false;
        }

        // Assuming lower chars
        boolean[] boolArray = new boolean[26];
        setBoolArray(s1, boolArray);
        setBoolArray(s2, boolArray);

        int count = 0;
        for (boolean b : boolArray) {
            if (b) {
                count++;
            }
        }

        if (count <= 2) {
            return true;
        }

        return false;
    }

    private static void setBoolArray(String s, boolean[] boolArray) {
        for (char c : s.toCharArray()) {
            int ascii = c;
            boolArray[ascii - ASCII_LOWERCASE_A] = !boolArray[ascii - ASCII_LOWERCASE_A];
        }
    }

}

/**
 * Approach 1 - Simplest - Use char array
 * - Put String 1 in an array of arr[ascii_index]=count
 * - For String 2, subtract arr[ascii_index]=count
 * - Add all elements in array, and see if value is -1 <= x <= 1.
 * Won't work for strings of equal length, but totally different characters e.g., pale, bake
 * - To fix this, just count non-zeroes in the array. Should be <=2 .g., pale vs bale would return 2.
 * Won't work for strings of non-equal length e.g., palers vs pale
 * <p>
 * Appraoch 2 - Use bool array
 * - Put String 1 in an array of bool arr[ascii_index]=count
 * - For String 2, flip arr[ascii_index]=count
 * - Count all elements with 'true'. Must be -2 <= x <= 2
 * pale vs bake returns 4 (F)
 * palers vs pale returns T (but predicate catches it)
 * pale vs ple - 1 (T)
 * pales vs pale - 1 (T)
 * pale vs bale - 2 (T)
 * <p>
 * Optimizations:
 * - Stop right away if length diff is > 1
 */
