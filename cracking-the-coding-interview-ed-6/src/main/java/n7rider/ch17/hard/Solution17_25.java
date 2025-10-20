package n7rider.ch17.hard;

/**
 * Word Rectangle: Given a list of millions of words, design an algorithm to create the largest possible
 * rectangle of letters such that every row forms a word (reading left to right) and every column forms
 * a word (reading top to bottom). The words need not be chosen consecutively from the list but all
 * rows must be the same length and all columns must be the same height.
 *
 * After finishing:
 * After separating by count and creating trie, there is not much optimization - since the results can vary entirely
 * by combination. Optimizations due to new chars being added or removed is taken care of by trie anyway.
 *
 * After comparing:
 * Same as mine
 */
public class Solution17_25 {
}
/*
We want to construct a N*M matrix that fulfills some rules (that each row and col are words) from a matrix of size million * ~10.

>The words need not be chosen consecutively from the list
Not sure what this means, but I'm guessing we can take any word from anywhere in the dictionary to construct this
rectangle
So it's not finding something inside the matrix, but creating a new rectangle (matrix) using the dictionary

To remember: The rows must be of the same length, substrings are not mentioned, so some sort of grouping can help.

Brute force approach:
Group dictionary by letters in a word  // O(d)
For each group // O(10)
    Create a trie for all words
    init maxSeq=[], maxLen=0
    For each word // O(d)
        init currSeq=word
        if word.nextLetter has hits in trie
            for each hit // cats -> we found alps, aims // O(D/2)
                for each letter in idx // l->i, p->m, s->s   // O(10)
                    if each column leads to something in the trie
                        add to cur seq
                        if has end next to it all
                            setCurSeqAsMax // if bigger
                    else
                        break
                currSeq=word // start over with some other word

Runtime: O(d^3) // Add (* n^2) if char limit is considered 10
Space: O(d)

To optimize, how about going for multiple runs rather than d*d*d?

a i m
s c o
h e w

Make some connections at the beginning?
After constructing trie
    for each word
        look up tree and populate possibleChars[]
        // e.g., if we only have aim, sco, yak, hew & then ash, look up all connections from 'a' and just add those.
        // So it sees ash and first finds each letter has entries in trie.
        // Then, it finds what other words can start with a, so adds ash, awe and so on.
        // So it adds words starting s & h from ash, then w & e from awe -- so adds sco, hew and so on.
        // The third O(d) will not call each word, but just look up each from here
            // Similarly, we can save for the next char too
            // e.g., for sco, it first checks if 'c' and 'o' have words, and then look for words that have s in idx 1 i.e., branching out
            from this s, and only add these to s.
            ...

So for each word
    It will look up a fraction of the connected words but still O(d * d/2 * 10/2 * d/2 * 10/2)

Or does it? Can we pre-collect a list of words? It's going to be the same.


 */