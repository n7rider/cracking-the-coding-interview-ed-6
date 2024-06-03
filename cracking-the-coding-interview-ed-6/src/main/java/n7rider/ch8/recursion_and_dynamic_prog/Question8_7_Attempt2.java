package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Permutations without Dups: Write a method to compute all permutations of a string of unique
 * characters.
 *
 * After finishing:
 * I used the list-based algorithm to find combination, then found permutation for each combination.
 *
 * After comparing with solution:
 * The book's approach is similar.
 * - Take one character at a time
 * - But rather than concat to create combination and then find permutation, the book just concats
 * the new char to each possible position. e.g., in a1, when you add a2, add to first and last pos
 * so you get a2a1 and a1a2. When a3 is added, you get 3+3=6 permutations with a3 and so on.
 * You need to use insertCharAt() to do the above efficiently.
 *
 * The book has another solution to build one char string, two char string. However, I guess the
 * binary search-ish algorithm has a for loop/recursion to consider one char as current, then build
 * other chars.
 * e.g., For cat,
 * Run loop i = 0 to rem.len (Initially rem = entire string)
 *   take char.At(i) as prefix i.e., c when i=0, at as reminder
 *   Call method recursively for prefix + currentChar, rem
 *      So prefix is ca | reminder t
 *         Then prefix is cat | reminder: "", so print prefix
 *
 *      when i = 1 for len = 2, char.At(i) = t. prefix + currChar = ct, rem = a
 *         This eventually prints cta and so on.
 *
 * In short, this prints cat | cta | then takes a as the first char and does act | atc and so on
 * So, this takes a char as first char, recursively do it, until there is only one left
 * Changing the first char taken out at each recursion and you have the algorithm.
 * This only prints permutations at the length of the string, not the substrings though.
 *
 *
 *
 */
public class Question8_7_Attempt2 {

    public static void main(String[] args) {

        PermutationFinder.printAllPermutations("cats");

    }

    static class PermutationFinder {

        static void printAllPermutations(String inp) {
            List<String> combs = new ArrayList<>();
            combs.add("");
            for(int i = 0; i < inp.length(); i++) {
                combs = findCombinations(combs, inp, i);
            }
        }

        static private List<String> findCombinations(List<String> prevCombs, String inp, int currIdx) {
            List<String> output = new ArrayList<>();
            output.addAll(prevCombs);
            char currChar = inp.charAt(currIdx);
            for(String prevComb : prevCombs) {
                String newComb = prevComb + currChar;
                printPermutations(newComb, newComb.length());
                output.add(newComb);
            }

            //System.out.printf("Combinations for currIdx %d is %s\n", currIdx, output);
            return output;
        }

        static private void printPermutations(String inp, int n) {
            if(n == 1) {
                System.out.println(inp);
                return;
            }

            for(int i = 0; i < n; i++) {
                printPermutations(inp, n - 1);
                inp = rotatedString(inp, n);
            }
        }

        static private String rotatedString(String inp, int c) {
            // cat | 1 --> cta  | newIdx = 1
            //             012
            // cat | 2 --> tca  | newIdx = 0
            char lastChar = inp.charAt(inp.length() - 1);
            int newIdx = inp.length() - c;
            char[] inpArr = inp.toCharArray();
            for(int i = inp.length() - 1; i > newIdx; i--) {
                inpArr[i] = inpArr[i-1];
            }
            inpArr[newIdx] = lastChar;
            return String.valueOf(inpArr);
        }
    }


}



/*
foxes
cats
cat

cat
  {}
  {} + c
  {} + c + a + ca
  {} + c + a + ca + t + ct + at + cat

for int i = 0; i < 2^inp.len; i++
  while i = 0

 */