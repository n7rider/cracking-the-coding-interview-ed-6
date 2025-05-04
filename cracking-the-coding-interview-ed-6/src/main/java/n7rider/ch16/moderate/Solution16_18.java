package n7rider.ch16.moderate;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Pattern Matching: You are given two strings, pattern and value. The pattern string consists of
 * just the letters a and b, describing a pattern within a string. For example, the string catcatgocatgo
 * matches the pattern aabab (where cat is a and go is b). It also matches patterns like a, ab, and b.
 * Write a method to determine if value matches pattern.
 *
 * After finishing:
 * Mine takes O(n^2) to run. The hash map makes it look simpler, but there are better ways to do this
 * storage-wise e.g., just run through the string
 *
 * After comparing:
 * The book checks the pattern multiple times through the string rather than using a hash map. Otherwise,
 * it is similar.
 */
public class Solution16_18 {
    public static void main(String[] args) {
        assertEquals(true, doesMatchPattern("catcatgocatgo", "aabab"));
        assertEquals(false, doesMatchPattern("catcatgocatgocat", "aabab"));
    }

    static boolean doesMatchPattern(String str, String pattern) {
        // null, empty validatons for text, pattern, validate pattern has 2 unique chars
        Map<Character, Integer> occMap = buildOccMap(pattern);
        // number of times `a` occurs in the pattern
        int aCharCount = occMap.get(pattern.charAt(0));
        // Thanks to validations, this will never be empty
        char bChar = occMap.keySet().stream().filter(c -> c != pattern.charAt(0)).findFirst().get();
        System.out.printf("aChar = %c | bChar = %c\n", pattern.charAt(0), bChar);
        for(int i = 1; i <= str.length() / aCharCount; i++) {
            int remainingChars = str.length() - (i * aCharCount);
            if(remainingChars % occMap.get(bChar) != 0) {
                continue; // Not a possible combination
            } else {
                boolean flag = true;
                Map<Character, String> patternMatchMap = new HashMap<>();
                int strIdx = 0;
                int subStrLen;
                for(int k = 0; k < pattern.length(); k++) {
                    if(pattern.charAt(k) == pattern.charAt(0)) {
                        subStrLen = i;
                    } else {
                        subStrLen = remainingChars / occMap.get(bChar);
                    }
                    String subStr = str.substring(strIdx, strIdx + subStrLen);

                    if(patternMatchMap.containsKey(pattern.charAt(k))) {
                        if(!patternMatchMap.get(pattern.charAt(k)).equals(subStr)) {
                            flag = false;
                            break;
                        }
                    } else {
                        patternMatchMap.put(pattern.charAt(k), subStr);
                    }

                    strIdx += subStrLen;
                }

                if(flag) {
                    System.out.printf("Pattern matched: a's len = %d | b's len = %d\n", i, remainingChars / occMap.get(bChar));
                    System.out.println(patternMatchMap.toString());
                    return flag;
                }
            }
        }

        return false;
    }

    private static Map<Character, Integer> buildOccMap(String pattern) {
        Map<Character, Integer> out = new HashMap<>();
        for(char c: pattern.toCharArray()) {
            if(out.containsKey(c)) {
                out.put(c, out.get(c) + 1);
            } else {
                out.put(c, 1);
            }
        }
        return out;
    }
}

/*
create occMap for pattern <Char, Int>
for i = a's len = 0 to str.len / occMap.get(a)
    for j = b's len = 0 to str.len / occMap.get(b)
        // math possibility
        bCharCount = str.len - (i * occMap.get(a))
        if bCharCount % occMap.get(b) != 0
            continue // reminder is there, not a possible combination
        else
            int strIdx = 0
            bool flag = true
            Map<Char, Str> patternMatchMap
            for int k = 0 to pattern.len // iterate through pattern and see if it fits
                substr = str.substring (strIdx, i)
                if patternMatchMap.get (pattern[idx]) exists // convert pattern to array
                    if entry == substr // pattern is repeated and still matches
                        continue
                    else
                        flag = false
                        break
                else
                    just insert to map
            if flag == true
                return true

 */
/*

simple algorithm:
try diff lengths of each char in pattern e.g., with ab in pattern try lengths of 0...n 0...,m
for each.
With more combinations in pattern, we'll try 0..l for each of them

Keep marker for each patternChar's count, and try all combinations.

entry
    //    replace pattern with int (use asciii)
    map = charCount int pattern.
    ptnIndCharCount = 0 for each entry // but len of string/its occurence for the last one
    // e.g., for b, idx = 1, var = 13/2 = 6.5 won't work, so keep trying
    helper (string, ptnIndCharCount, 0, 0)

helper (string, pattern, ptnCharMap, int[] ptnIndCharCount, int currArrIdx, int currPtnIndex)

    // Only true reaches here
    if currArrIdx = len
        return true

    pattern[currPtxIndex]
    if currArrIdx = len - 1
         // e.g.,  when a count = 0, b = (13 - 0) / 2
        if(string.length - (all prev char's len * occ) % this-one's occurrence) == 0
            use quotient
        else
            find-earliest-prev-array that can be incrementable and add 1 to it
            return false

    map<Char, String> stringsectionMap

    currSubString = string.substring (0 to ptnIndCharCount[currArrIdx]) // Is 0 correct?
    if(stringSectionMap has pattern[ptnIdex))
        if currString == existing Value
            currPtxIdx++ // matching so far, keep going
        else
            // not a match
            return false

    return true



Algorithm Replacement:
    Create map of <pattern-char, count> [(a=3, b=2)]
    Create set that maintains order // can even convert set to array list [a, b]
    Create array of currOcc of each pattern char [0, rest]
    Create a loop of n times or do recursion of n times
        Establish Mathematical possibility
        Test characters
        Return true/false and cascade



for int i = 0 to len - 1 // len-1 th char in pattern gets all that's left
    if i == len - 1
        cal occ-from-map * array[i] and get the rest
    set[i]'s entry in set takes = its occurence from map * array[i]



I'm going with the same approach here again.
It's doable but will be highly complex.

--

Let's try with just 2 possibilities in the pattern a and b
And maybe later I can expand.

the string catcatgocatgo matches the pattern aabab (where cat is a and go is b)

create occMap for pattern <Char, Int>
for i = a's len = 0 to str.len / occMap.get(a)
    for j = b's len = 0 to str.len / occMap.get(b)
        // math possibility
        bCharCount = str.len - (i * occMap.get(a))
        if bCharCount % occMap.get(b) != 0
            continue // reminder is there, not a possible combination
        else
            int strIdx = 0
            bool flag = true
            Map<Char, Str> patternMatchMap
            for int k = 0 to pattern.len // iterate through pattern and see if it fits
                substr = str.substring (strIdx, i)
                if patternMatchMap.get (pattern[idx]) exists // convert pattern to array
                    if entry == substr // pattern is repeated and still matches
                        continue
                    else
                        flag = false
                        break
                else
                    just insert to map
            if flag == true
                return true

// how do make this fit for any number of chars in pattern?

replace for loops with an array that holds values of i, j. etc
iterate these vars manually
To find bCharCount

global fields: str, pattern, occMap // treeMap - returns consistent order, also get by idx (else create another map
of char, idx)
entry
    iterArr = 0 for all fields (len = occMap.keySet.size)
    helper(iterArr, 0)

helper (iterArr, currIterIdx)

    if currIterIdx = occMap.keySet.size - 1 // last one, so take all that's left
        for int i = 0 to occMap.keySet.size - 2
            charCountUsed = currIterIdx[i] * occMap.get(1) // e.g., add a's len * a's occ)
        lastsCharCount = str.len - ( charCountUsed )
        if lastsCharCount % occMap.get(occMap.get(size - 1) != 0
            continue // reminder is there, not a possible combination
        else
            int strIdx = 0
            bool flag = true
            Map<Char, Str> patternMatchMap
            for int k = 0 to pattern.len // iterate through pattern and see if it fits
                substr = str.substring (strIdx, i)
                if patternMatchMap.get (pattern[idx]) exists // convert pattern to array
                    if entry == substr // pattern is repeated and still matches
                        continue
                    else
                        flag = false
                        break
                else
                    just insert to map
            if flag == true
                return true

        if iterArr[currIterIdx] >= str.len / occMap.get(currIterIdx)
            if(currIterIdx = last && iterArr[0] at its max)
                return false
            else if(currIterIdx = 0)
                currIterIdx++
            else
                iterArr[currIterIdx] = 0
                currIterIdx--

        return helper(iterArr, currIterIdx)

// This is too complex to finish in an interview


 */