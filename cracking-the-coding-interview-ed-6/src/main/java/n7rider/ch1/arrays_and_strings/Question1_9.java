package n7rider.ch1.arrays_and_strings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * String Rotation:Assume you have a method isSubstring which checks if one word is a substring
 * of another. Given two strings, sl and s2, write code to check if s2 is a rotation of sl using only one
 * call to isSubstring (e.g., "waterbottle" is a rotation of"erbottlewat").
 * Hints: #34, #88, # 7 04
 *
 * <p>
 * Post-run observations:
 * - Runtime is O(n) but worst case e.g., rarbrc vs brcrar can make it O(n^2)
 * <p>
 * After comparing with solution:
 * This is a trick solution. Just write s2 twice, and you should get s1 in the middle of it.
 *
 */
public class Question1_9 {

    public static void main(String[] args) {
        assertTrue(isSubString("waterbottle", "erbottlewat"));
        assertTrue(isSubString("tatter", "tterta"));
        assertFalse(isSubString("tatter", "tertta"));
        assertTrue(isSubString("rrrttt", "rtttrr"));
        assertTrue(isSubString("eeeeee", "eeeeee"));
    }

    private static boolean isSubString(String s1, String s2) {
        // Skip null check
        int len = s1.length();
        if(len != s2.length()) {
            return false;
        }

        int idx = s2.indexOf(s1.charAt(0)); // where you find s1's first char
        while(idx != -1) {
            int i;
            for(i = 1; i < len; i++) {
                if(s2.charAt((idx + i) % len) != s1.charAt(i)) {
                    break;
                }
            }

            if(i == len) {
                return true;
            }
            else {
                idx = s2.indexOf(s1.charAt(0), idx + 1); // search from the next char
            }
        }

        return false;
    }
}

/**
 * Only one call to isSubstring, so can't rotate a char and check every time
 * Find first char and then check each char?
 *
 * Do length check
 * do
 *   Find x = a[0]'s index in b
 *   for i = 0 to l-1
 *      if b[(x+i) %l] != a[i]
 *      break
 *   Find x = a[0]'s next index in b
 * while x != -1
 */