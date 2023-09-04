package n7rider.ch1.arrays_and_strings;

import static org.junit.Assert.*;

/**
 * Palindrome Permutation: Given a string, write a function to check if it is a permutation of a palindrome.
 * A palindrome is a word or phrase that is the same forwards and backwards. A permutation
 * is a rearrangement of letters. The palindrome does not need to be limited to just dictionary words.
 *
 * EXAMPLE
 * Input:  Tact Coa
 * Output: True (permutations: "taco cat", "atco eta", etc.)
 * Hints: #106, #121, #134, #136
 * Post-run observations:
 * - Runtime is O(n)
 * <p>
 * Optimizations:
 * - Split into different methods for readability
 * <p>
 * After comparing with solution:
 * - Same as mine
 * - Optimization: Instead of a char table, use a bit array using 0 for even, 1 for odd. Saves space
 */
public class Question1_4 {
    public static void main(String[] args) {
        assertTrue(isPalindromePermutation("Tact Coa"));
        assertFalse(isPalindromePermutation("Tact CoaT"));
    }

    private static boolean isPalindromePermutation(String s) {
        char[] ca = s.toLowerCase().toCharArray();
        int[] arr = new int[26];

        for(char c : ca) {
            if(c == ' ') {
                continue;
            }
            int index = ((int) c) - 97;
            arr[index]++;
        }

        int oddCount = 0;
        for(int count: arr) {
            if(count % 2 != 0) {
                oddCount++;
            }
            if(oddCount > 1) {
                return false;
            }
        }

        return true;
    }

}



/**
 * Approach:
 * - Go through each char
 * - Save char count (use int[ascii-index] = count structure)
 * - Palindrome ex: tatat (t=3, a=2) | tttaaattt (t=6, a=3) => 1 count can be odd, all else is even
 * - Go through array
 * - If > 1 value is odd, fail. Else succeed
 *
 * Assumptions:
 * - We get at least 1 char in string
 * - Only ascii alphabets - we convert all to lowercase (Ex says so)
 * - No numbers, symbols
 * - Ignore spaces
 *
 * Algorithm:
 * for each c in ca:
 *   arr[(int) c]++;
 *
 * int c = 0;
 * for int i = 0 to arr.length-1:
 *   if(arr[i] %2 !=0]
 *     c++;
 *   if c > 1
 *     return false;
 *
 * return true;
 *
 */