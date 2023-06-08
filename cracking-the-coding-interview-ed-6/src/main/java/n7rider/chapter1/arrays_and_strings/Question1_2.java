package n7rider.chapter1.arrays_and_strings;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 1.2 Check Permutation: Given two strings, write a method to decide if one is a permutation of the other.
 * Hints: #1, #84, #122, #131
 * ---
 * Post-run observations:
 * - The runtime is O(n+m)
 * <p>
 * After comparing with solution:
 * - The solution doesn't use additional data structure
 * - The solution finds count of all possible characters using int[]
 * where index is the ASCII value, value is the count.
 * This runtime will be O(n+m) and the space complexity is more
 * - I should hereafter try without additional data structure
 */
public class Question1_2 {
    public static void main(String[] args) {
        assertTrue(arePermutations("case", "asec"));
        assertFalse(arePermutations("Aas", "saa"));
        assertFalse(arePermutations("sa", "saa"));
        assertFalse(arePermutations("abc", "bcd"));
    }

    private static boolean arePermutations(String s1, String s2) {
        // Assumption: No nulls are passed. I can add null check if required

        if (s1.length() != s2.length()) {
            return false;
        }

        Map<Character, Integer> s1Map = createCharMap(s1);
        Map<Character, Integer> s2Map = createCharMap(s2);

        if (s1Map.keySet().size() != s2Map.keySet().size()) {
            return false;
        }

        for (char c : s1Map.keySet()) {
            int s1Count = s1Map.get(c);
            if (s2Map.get(c) == null || s2Map.get(c) != s1Count) {
                return false;
            }
        }
        return true;
    }

    private static Map<Character, Integer> createCharMap(String s) {
        Map<Character, Integer> s1Map = new HashMap<>();
        for (char c : s.toCharArray()) {
            s1Map.put(c, s1Map.get(c) == null ? 0 : s1Map.get(c));
        }
        return s1Map;
    }
}


/**
 * Approach:
 * ---
 * <p>
 * Ex 1: case & asec - True | case & casd - False
 * Ex 2: aas & saa - True  | Aas & saa - False
 * <p>
 * Assumptions:
 * Case-sensitive, holds only numbers and alphabets
 * <p>
 * Brute force - Bubble sort - Works but worst - O(n^2)
 * Hash map <char, count> - Run it for both strings
 * <p>
 * Early exit conditions:
 * Check string length (equality)
 * ? While building second string, check if unknown char is entered or count > first's (Not always suitable)
 * <p>
 * Algorithm:
 * // Early exits
 * Check string length (equality)
 * <p>
 * create <char, count> map for first string
 * create <char, count> map for second string
 * check keyset count length (equality)
 * for each key, value in first map, second map should match
 */