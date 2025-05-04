package n7rider.ch16.moderate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Master Mind: The Game of Master Mind is played as follows:
 * The computer has four slots, and each slot will contain a ball that is red (R). yellow (Y). green (G) or
 * blue (B). For example, the computer might have RGGB (Slot #1 is red, Slots #2 and #3 are green, Slot
 * #4 is blue).
 * You, the user, are trying to guess the solution. You might, for example, guess YRGB.
 * When you guess the correct color for the correct slot, you get a "hit:' If you guess a color that exists
 * but is in the wrong slot, you get a "pseudo-hit:' Note that a slot that is a hit can never count as a
 * pseudo-hit.
 * For example, if the actual solution is RGBY and you guess GGRR, you have one hit and one pseudo-hit.
 * Write a method that, given a guess and a solution, returns the number of hits and pseudo-hits.
 *
 * After comparing:
 * ---
 *
 * After finishing:
 * The book solution is highly specialized for the case in hand. Mine can handle varying number of slots, possibilities.
 * This is something that can be discussed with the interviewer. There is no need to complicate things unless needed.
 * Also, it's worth almost trying to minimize usage e.g., here, instead of a hash map, I can use an array where the
 * index is determined by the ASCII value of the char.
 */
public class Solution16_15 {
    public static void main(String[] args) {
        // Hits: 2 | PH: 1
        System.out.println(findScore("RGGB".toCharArray(), "YRGB".toCharArray()));

        // Hits: 2 | PH: 2
        System.out.println(findScore("AAGBRR".toCharArray(), "RRGBZZ".toCharArray()));

        // Hits: 1 | PH: 3
        System.out.println(findScore("AAAGBRR".toCharArray(), "ZZZAAAR".toCharArray()));
    }

    static class Score {
        int hits;
        int pseudoHits;

        @Override
        public String toString() {
            return String.format("Hits: %d | Pseudo-hits: %d \n", hits, pseudoHits);
        }
    }

    static Score findScore(char[] actual, char[] guess) {
        validate(actual, guess);
        Score out = new Score();
        // +1 for actual char, -1 when a guess becomes a PH
        Map<Character, Integer> slotMap = new HashMap<>();
        for(int i = 0; i < guess.length; i++) {
            if(guess[i] == actual[i]) {
                out.hits++;
            } else {
                Integer guessCharCount = slotMap.get(guess[i]);
                Integer actualCharCount = slotMap.get(actual[i]);

                if(actualCharCount == null || actualCharCount >= 0) {
                    slotMap.merge(actual[i], 1, (currVal, newVal) -> currVal + 1);
                } else {
                    // supply < 0, so a PH is owed
                    out.pseudoHits++;
                    slotMap.put(actual[i], actualCharCount + 1);
                }
                if(guessCharCount != null && guessCharCount > 0) {
                    // supply > 0, so a PH is acquired
                    out.pseudoHits++;
                    slotMap.put(guess[i], guessCharCount - 1);
                } else {
                    slotMap.merge(guess[i], -1, (currVal, newVal) -> currVal - 1);
                }
            }
            System.out.println(slotMap.toString());
        }
        return out;
    }

    private static void validate(char[] actual, char[] guess) {
        if(actual == null || actual.length == 0 ||
                guess == null || guess.length == 0 || guess.length != actual.length) {
            throw new IllegalArgumentException("Invalid input");
        }
    }
}

/*
Easiest solution
--
Run two passes + use a set as marker + use map as reference
pass 1 - look for hits, update hit-set with idx, update map <Color, int>
pass 2 - look for pseudo hit
   Skip if curr idx is in set, so proceed only if curr color is not a hit
   Look up curr color in map
   If in map, add to pseudo hit
   subtract 1 from map

Reducing space complexity:
map is needed - we need to store and check entries for a potential pseudo hit
set - if set is removed, we need a logic in map, or we can use an boolean array to min space

Reducing time complexity:
Can we do this in 1 pass?
Will work if we store PH first, use it later
If PH is called first, stored later
    We need a structure to store PH calls, then deduct things
        Map<Color, Int> to store PH demands, then counter  for PHs

Algorithm:
For each slot:
    Compare guess[i] <-> actual[i]
    If true
        Update Hcount
    Else
        if demandMap has guess[i]
            Deduct 1
            Update PHcount
        else
            Add 1 to map

RGGB
YRGB

What to do if not a hit
    actual - add to map
    guess  - sub from map

actual happens before guess - simple +1 becomes 0, 2 becomes 1 etc.
guess happens before actual though - -1 becomes 0 (obvious),
    but if 2 becomes three, do we add to PH or no?
        ex: Val becomes -1 (1 guess), 5 actual (+4 now, PH++), more guesses come in (takes it to 0)
        So, actual adds to PH only if val < 0?
        Yes, because guess (demand) only takes what it needs but (actual) supply can be more than that.
            So, it val is -1 it means we owe PH to some guesses, but if it is >0, then there's no outstanding PH owed.



 */