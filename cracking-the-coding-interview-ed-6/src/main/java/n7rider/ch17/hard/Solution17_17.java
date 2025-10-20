package n7rider.ch17.hard;

/**
 * Multi Search: Given a string b and an array of smaller strings T, design a method to search b for
 * each small string in T.
 * Example:
 * T = {"is", "ppi", "hi", "sis", "i", "ssippi"}
 * b = "mississippi"
 *
 * After finishing:
 * 2 or 3 works. 2 is simpler, but I wonder if my code is too simple.
 *
 * After comparing:
 * The solutions are similar. An assumption that I didn't get in the problem statement - you have
 * to run through the entire string 'b' and find all possible places. The book has 2 viable solutions:
 * Looking through the hashmap like Solution 1 - Runtime of O(kbt) where k is the length of the longest string in T.
 * Using a Trie to map all strings in T and running through b while looking at the trie. Runtime is O(kb + kt)
 * where kT is to create the trie, kB is to run through b.
 * It's similar through what I had, but I gave up on trie just 2 problems back. Probably I should consider and compare both.
 * 
 */
public class Solution17_17 {
}

/*
Simple solution:

Create a hashmap<Char, List<Integer>> for position of each char in 'b'
For every string in t
    Start from Int value in hashmap and see if it tracks
        Get substring and see if it matches

Runtime - O(n) + O(m) i.e., length of b + total length of words in t

Downsides:
- if b is too long and t is too small, there is no need to put everything in hashmap
- if t repeats a lot, we'll be taking substrings a lot of time. We can cache Strings to save time
---

Init an empty hashmap
Sort T by char-length
int hashMapIndexedLen = 0
For each string in T
    Look up hashmap
    Use substring
    If not found
        Continue looking from hashMapIndexedLen until found
    Create secondary hashmap for content in T if found
        e.g., ssippi will p with index 3, so ppi can be found here, but we also need to store actual index from b also here.

Same runtime but cache can reduce runtime to as low as O(n) + 1, but worst case is O(n) + (m)
--

How about inverting the flow?

Create a hashmap<Char, Int> denoting starting char and index of ele in T.
Go through b
    If an letter matches a letter in hashmap, look it up

Same runtime
---

A recursive solution probably points towards a caching of strings but that's just too expensive.
We are told the strings in T are smaller, so probably we can cache them.
Also, this changes things - the hashmap of Char, List<Int> in Attempt 1 will have too big lists.

So 2 or 3 can works.
In 3, rather than caching hashmap <char, int>, we can cache hashmap< char, Row<int + String + boolean>>
As we go through b,
    we can look for the string if we get a entry in the hashmap, and update the boolean if it's found


 */

