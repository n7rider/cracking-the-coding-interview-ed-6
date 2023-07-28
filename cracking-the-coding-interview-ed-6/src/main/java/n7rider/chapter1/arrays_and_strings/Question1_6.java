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
 * - I store previousChar and currentChar to compare. The solution always checks the next char.
 * This eliminates previousChar and also the odd logic I have after the end of the for loop.
 * // TODO Try it out
 *
 */
public class Question1_6 {
    public static void main(String[] args) {
        assertEquals("a2b1c5a3", compress("aabcccccaaa"));
        assertEquals("abcdef", compress("abcdef"));
        assertEquals("abcdeff", compress("abcdeff"));
        assertEquals("aaabbd", compress("aaabbd")); // because a2b2d1.len = 6
    }

    static int count = 1;
    static StringBuilder result = new StringBuilder();
    static char pc = ' ';

    private static String compress(String s) {
        // Skip null check

        if (s.length() == 1) {
            return s;
        }

        pc = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == pc) {
                count++;
            } else {
                result.append(pc);
                // Adding + 0 converts int to char. Without it, it'll get the ASCII char for count's value
                result.append((char) (count + '0'));
                pc = c;
                count = 1;

                if(result.length() >= s.length()) {
                    return s;
                }
            }
        }
        pc = s.charAt(s.length() - 1);
        result.append(pc);
        result.append((char) (count + '0'));

        if (result.length() >= s.length()) {
            return s;
        } else {
            return result.toString();
        }
    }
}

/**
 * iterate through the string
 * keep adding as long as the char is same
 * if not same, concat char + count to the new string
 *   store highest
 * repeat
 * if highest > 1,  return new string, else return original string
 * e.g., abcdef -> a1b1c1d1e1f1. So reusing string is not worth it.
 * <p>
 * prev_char=s[0]
 * count = 0
 * for i = 1 to s.length
 *   char c = s[i]
 *   if(c == prev_char)
 *     count++
 *   else
 *     new_s += prev_char + count
 *     prev_char = s[i]
 * new_s += prev_char + count
 */
