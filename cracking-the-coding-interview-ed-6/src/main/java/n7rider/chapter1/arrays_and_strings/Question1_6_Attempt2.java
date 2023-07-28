package n7rider.chapter1.arrays_and_strings;

import static org.junit.Assert.assertEquals;

/**
 * String Compression: Implement a method to perform basic string compression using the counts
 * of repeated characters. For example, the string aabcccccaaa would become a2blc5a3. If the
 * "compressed" string would not become smaller than the original string, your method should return
 * the original string. You can assume the string has only uppercase and lowercase letters (a - z).
 * Hints:#92, #110
 *
 * Post-run observations:
 * - Runtime is O(n)
 * <p>
 * Optimizations:
 * -
 * <p>
 * After comparing with solution:
 * - There is an additional check everytime but, it keeps the logic cleaner and the code smaller.
 * Always try to refactor if you have special exit or init logic
 *
 */
public class Question1_6_Attempt2 {
    public static void main(String[] args) {
        assertEquals("a2b1c5a3", compress("aabcccccaaa"));
        assertEquals("abcdef", compress("abcdef"));
        assertEquals("abcdeff", compress("abcdeff"));
        assertEquals("aaabbd", compress("aaabbd")); // because a2b2d1.len = 6
    }
    private static String compress(String s) {
        int count = 0;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            count++;

            if (i == s.length() - 1 || s.charAt(i + 1) != s.charAt(i)) {
                result.append(s.charAt(i));
                result.append(count);
                count = 0;
            }
        }

    return result.length() < s.length() ? result.toString() : s;

    }
}

/**
 * for i = 0 to n-1
 *   count++
 *   if s[i+1] != s[i] || i == n-1
 *      result += s[i] + count
 *  return result or s
 */
