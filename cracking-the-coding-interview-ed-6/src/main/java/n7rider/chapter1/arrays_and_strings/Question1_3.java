package n7rider.chapter1.arrays_and_strings;

import static org.junit.Assert.assertEquals;

/**
 * URLify: Write a method to replace all spaces in a string with '%20'. You may assume that the string
 * has sufficient space at the end to hold the additional characters, and that you are given the "true"
 * length of the string. (Note: If implementing in Java, please use a character array so that you can
 * perform this operation in place.)
 * EXAMPLE
 * Input: "Mr John Smith ", 13
 * Output: "Mr%20John%20Smith"
 * Hints: #53, # 118
 * <p>
 * Post-run observations:
 * - Runtime is O(2n) i.e., O(n)
 * <p>
 * Optimizations:
 * - If i == wi => skip write (If write speed is slower than check)
 * - Running loop from beginning reduces writes, but introduces a stack, counter (so increased complexity)
 * Runtime still remains same though
 * <p>
 * After comparing with solution:
 * - Same as mine
 */

public class Question1_3 {

    private static final char[] rep = "%20".toCharArray();

    public static void main(String[] args) {
        String in1 = "Mr John Smith ";
        assertEquals("Mr%20John%20Smith%20", urlify(fromString(in1), in1.length()).trim());
        String in2 = "  ";
        assertEquals("%20%20", urlify(fromString(in2), in2.length()).trim());
        String in3 = "";
        assertEquals("", urlify(fromString(in2), in3.length()).trim());
    }

    private static String urlify(char[] ca, int len) {
        // Skip null checks, is already safe with blanks
        int c = 0;
        for (int i = 0; i < len; i++) {
            if (ca[i] == ' ') {
                c++;
            }
        }

        int extraChars = (rep.length - 1) * c;
        int wi = len - 1 + extraChars;
        int ri = len - 1;
        for (int i = ri; i >= 0; i--) {
            if (ca[i] != ' ') {
                ca[wi--] = ca[i];
            } else {
                for (int j = rep.length - 1; j >= 0; j--) {
                    ca[wi--] = rep[j];
                }
            }
        }
        return String.valueOf(ca);
    }

    private static char[] fromString(String s) {
        // Assuming total chars required is <= 100
        char[] ca = new char[100];
        for (int i = 0; i < s.length(); i++) {
            ca[i] = s.charAt(i);
        }
        return ca;
    }
}

/**
 * Approach:
 * ---
 * <p>
 * <p>
 * Ex: Mr John ->
 * Mr%20John
 * <p>
 * if char != %20
 *   continue;
 * else
 *   s[i]=%
 * char t1=s[i+1] | char t2=s[i+2] (Check for index out of bounds for each)
 * insert '2', '0', t1, t2
 * This will fail if two spaces happen right next to each other
 * <p>
 * Mr J o -> Mr%20J%20o
 * <p>
 * sp = 0 // maintains track of chars to keep in stack
 * if char == ' '
 *   add 3 more elements to stack
 *   pop stack
 * else
 *   add element to stack
 *   pop stack
 * stack = array + int counter
 * <p>
 * Algorithm - if O(2n) is okay (instead of O(n));
 * run through string to find c = number of ' '
 * set l = s.length + c * "%20".length
 * set ri = s.length - 1 // read index
 * set wi = l - 1 // write index
 * set x = "%20"
 * for i = ri - 1 to 0
 *   if s[i] != ' '
 *     s[wi] = s[ri]
 *   else
 *     for j = x.len - 1 to 0
 *       s[wi--] = x[j]
 * <p>
 * Algorithm - If O(2(n) is not okay, we need to use stack
 * But this needs more space and complexity increases
 * <p>
 * Find write index:
 * 'M o ' -> 'M%20o%20' i.e., 4->8
 * Last char position: c[3] -> c[7]
 */