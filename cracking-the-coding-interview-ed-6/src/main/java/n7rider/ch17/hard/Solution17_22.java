package n7rider.ch17.hard;

/**
 * Word Transformer: Given two words of equal length that are in a dictionary, write a method to
 * transform one word into another word by changing only one letter at a time. The new word you get
 * in each step must be in the dictionary.
 * EXAMPLE
 * Input: DAMP, LIKE
 * Output: DAMP-> LAMP-> LIMP-> LIME-> LIKE
 *
 * After finishing:
 * My approach (at the end) creates map for patterns that don't work, and list of items for each letter.
 * This runs through the dict only 1 time and
 * the runtime is O((n ^ k) * m) // k = no. of letters, m = dict length, n = no of paths forming a word
 *
 * After comparing:
 * The book does something similar but is more comprehensive. For each word, it creates all wildcards e.g., damp,
 * gives _amp, d_mp, da_p, dam_. Thus, to change damp, to *amp, we can call the hashmap for _amp, and then explore
 * each path.
 * It goes further by proceeding from both sides to reduce the runtime to O((k ^ n/2) * m). Rather than doing say,
 * n ^ 4 for a 4 letter word, we get n ^ 2 + n ^ 2.
 * 
 */
public class Solution17_22 {
}

/*

Challenge is to find dictionary words for every transition
Resembles the maze walkthrough - explore a path, if it fails, explore the next option

Simplest solution:
getNextWord
    fOr each char in string
        FLip char to one in destination
        if word in dictionary
            update tracker boolean[] with true For curr index
        eLse // e.g. if LAMP is not a word, but LIKE and LIME are potential words, so keep going
            continue

The else statement shows a trick. Some letter fLip witt in words onLy if the sequence is in a specific way
So we need to explore all potential combinations

So try all combinations 00000 to 11111 starting at every char
Stop early if combination is not working

for i- 0 to n-1 // i = what char are we starting with
    List currTrail = new List
    List sequence = anagramifyRestofSequence(i) // if i=2, this returns all combinations to try 2, (0, 1, 3) | (0, 3, 1) | (1, 0, 3) | (1, 3, 0) etc.
    for j= i to sequence.len -1 // j - curr combination # we're trying
        for k = 0 to n-1 // curr  number in each sequence
            x = sequence[j][k]
            char currChar
            currTrailLen = currTrail.len
            for l = 0 to 25 // each ASCII
                string = replace string[x] with destination[]
                if (isWord(string, dict))
                    currTrail.add word
                    update resultWord
                    if resultWord == destination
                        return currTrail // OUTPUT
            if currTrailLen == currTrail.len // No letter added to trail
                currTrail
                break // go to a new sequence that j controls, this sequence won't work

Runtime:
i loop = n
j loop = n
k Loop = n

 m = dict length
n * n * n * m = 0(n^3 * m)

Can we make this better?
- AnagramSequence can be prepared once and reused with boundSafeAdd and mod
- We can create ranges that don't work e.g., _ctm won't work so we can create ranges that don't work inside the 'l' loop
- Create ranges that work for each 2 letter, 3 letter., n-1 combinations

We can also do this initially when running through the dict

So, j loop is O(1)
    e.g., if we have LAMP and we're looking at sequence 2 (0, 1, 3) - we try LA_P
    we get a list of letters that end with _P in O(1) time
    We take each 1 and continue through rest of j iterations
    So k loop is eliminated, and j needs O(m ) average time to run
So run time reduces to O(n * n * m).


 */