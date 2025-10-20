package n7rider.ch17.hard;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Word Distance: You have a large text file containing words. Given any two words, find the shortest
 * distance (in terms of number of words) between them in the file. If the operation will be repeated
 * many times for the same file (but different pairs of words), can you optimize your solution?
 *
 * After finishing:
 * Same as merge sort, so didn't write it out
 *
 * After comparing:
 * Same as mine
 */
public class Solution17_11_2 {


}

/*
Simplest solution:
- Use a hash map to store String, List<Long> positions

Run through the book to save entries to hashmap
Locate the two words and their list
    Do something like merge sort
    Compare a[0]  to b[0], iterate the list with the smaller number next (to try for a smaller gap)
    Repeat this while always storing the smallest

Ways to optimize:
    Replace hashmap with a trie to save space
    A mechanism to speed this up for successive queries

Above method takes O(w) + (r) - Length of book + Length of word

Speed up for same words:
    Simple - cache results to a HashMap of <String + String, Long>

Speed up for different words:
    Difficult e.g., A - 1, 4, 147 | B - 10, 63 | C - 100
    So, what to keep for A varies based on where B or C happen

    Are there cases that will never be used? Consider A - 5, 10, 30, 31, 50, 100
    Any number can be used e.g., if B -4 | C - 29 | D-49 - shows none of the values of A can be rejected
    Concentration of values don't mean the probability is high there either since the other word might have a different distribution

 */
