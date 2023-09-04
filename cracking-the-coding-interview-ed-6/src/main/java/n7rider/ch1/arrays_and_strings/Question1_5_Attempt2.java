package n7rider.ch1.arrays_and_strings;

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
 * - Runtime is O(n)
 * <p>
 * After comparing with solution:
 * - I do the if checks inline but the solution writes separate checks for ins/del and replacement
 * - It's probably more modular than mine, but mine is quite readable too.
 */

public class Question1_5_Attempt2 {

    public static void main(String[] args) {
        assertTrue(isOneEditAway("pale", "ple"));
        assertTrue(isOneEditAway("pales", "pale"));
        assertTrue(isOneEditAway("pale", "bale"));
        assertFalse(isOneEditAway("pale", "bake"));
        assertFalse(isOneEditAway("palers", "ple"));
        assertFalse(isOneEditAway("pale", "pke"));
    }

    private static boolean isOneEditAway(String s1, String s2) {
        // Skipping null checks
        if (Math.abs(s1.length() - s2.length()) > 1) {
            return false;
        }

        // Assuming lower chars
        String smaller, larger;
        if(s1.length() >= s2.length()) {
            larger = s1;
            smaller = s2;
        } else {
            larger = s2;
            smaller = s1;
        }

        int count = 0;
        for(int i = 0; i < smaller.length(); i++) {
            if(smaller.charAt(i) != larger.charAt(i)) {
                if(smaller.length() != larger.length()) {
                    // can't be a replacement. must be an insert/delete
                    if(smaller.charAt(i) == larger.charAt(i + 1)) {
                        count++; // an insert/delete
                    } else {
                        // next char too doesn't match, so > 1 chars are different
                        return false;
                    }
                }
                else {
                    count++; // a replacement
                }
                if(count > 1) {
                    return false;
                }
            }
        }
        return true;
    }
}

/**
 * Delete/Insert: pale ple (put smaller string second)
 * Replace: pale bale
 *
 * do len check - len diff should be <= |1|
 * if s1.len > s2. len
 *   larg = s1
 *   sm = s2;
 * else
 *   sm = s1;
 *   larg = s2;
 *
 * count = 0
 * for i = 0 to sm.leng - 1
 *   if sm[i] != larg[i]
 *      if sm.len != larg.len && sm[i] = larg[i]+1
 *         // it is a delete/insert
 *         count++
 *      else
 *       // len diff is 1, so only alternative is a replacement
 *         count++
 *
 *      if count > 1
 *         return false
 *
 * return true
 *
 *
 * pales, pale
 * pale, bake
 * pale, pke
 */
