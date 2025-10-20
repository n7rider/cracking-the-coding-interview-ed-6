package n7rider.ch17.hard;

/**
 * Re-Space: Oh, no! You have accidentally removed all spaces, punctuation, and capitalization in a
 * lengthy document. A sentence like "I reset the computer. It still didn't boot!"
 * became "iresetthecomputeri tstilldidntboot''. You'll deal with the punctuation and capitalization later;
 * right now you need to re-insert the spaces. Most of the words are in a dictionary but
 * a few are not. Given a dictionary (a list of strings) and the document (a string), design an algorithm
 * to unconcatenate the document in a way that minimizes the number of unrecognized characters.
 * EXAMPLE:
 * Input:
 * jesslookedjustliketimherbrother
 * Output: jess looked just like tim her brother (7 unrecognized characters)
 *
 * My approach:
 * There was vacation and work between algorithm and coding, so I'm stopping with the algorithm.
 * My approach is simple - rather than just going from start or end (which can stop in the middle of a word), I
 * kept going from the start until two words are found - if the combination is valid, it is a bigger word. Else,
 * we take whichever is a common word.
 * The trouble is, if a word can be broken down into too many smaller words e.g., catatonic, this will split it as
 * cat at on ic.
 * Keeping on going forward and trying to match with a word can be very expensive since we will consider the
 * entire sentence for a word by the end.
 * But essentially, at an index i, it checks for a potential word for 0-i, 1-i, 2-i and so on, and we'll take the
 * biggest word. This still is not clear how it deals with catatonickelodeon - should parse catatonic + ... OR,
 * cat + at + o + nickelodeon
 *
 * Book solution:
 * It goes from the back. At each char, it adds letters one by one and check how many chars are unmatched in the dict
 * e.g., in ismikesfavoritefood, 'd' goes alone.
 * Next goes o, od. The one after goes o, oo, ood etc.. So unmatched chars become zero at food,
 * so rest + " " + food is returned as the best combination so far. Similarly, favorite has invalid at 0 i.e., the lowest
 * so it's added to the result set.
 * For unmatched char, e.g., for chars 'e' and 's' that doesn't exist in dictionary, existing alone gives them the smallest
 * invalid chars, so they stay that way.
 * How does it differentiate between rite and favorite? Simple. It always keeps going until the end e.g., a goes up to
 * avoritefood, so we'll find out if a bigger word exists.
 * How does memoization apply here? In two ways:
 * 1. If number of invalid increases e.g., rite will have invalid 0, but ritefo
 * has more invalid chars, so we'll skip and keep looking.
 * 2. We cache the least number of invalids and the best possibility at each index, so when avoritefood comes looking, the
 * index for 'r' will return invalids = 0, best combination as rite food, so unless a word can do better i.e., create a
 * even bigger word, it's not getting selected. Thus, we choose the sentence with fewest words.
 *
 * THis has a downside though. If words are not in the dictionary, they are returned as individual chars e.g., mikesfood
 * will return m i k e s food. But this can be fixed with a simple change that adds to the return type for dict detected, or
 * we can do it ourselves.
 *
 * In short, this is how it works - Go from the back, keep trying all possibilites from 1 letter word to n letter words from
 * each position while choosing the ones that return the least invalids. We keep record of previous best records to avoid
 * looking into everything once again.
 *
 * I had the right idea but I believed this is too much brute force to be getting into. This works from the beginning too,
 * one just should not not hesitate to have an additional structure to look up things. Actually, memoization makes it easier
 * and more intuitive than the simpler way of going and checking deeper down the line every time
 * which needs more brain power to visualize.
 *
 *
 *
 *
 */
public class Solution17_13 {
}

/*
Simple solution:
keep adding chars to temp
wait until the word is found is dictionary
add word, space to output string builder

Runtime: O(n * m) - length of string + length of dictionary

Missing logic:
Word can be left truncated - catchtheball - will result in cat chthe ball
If we go from the back, it can result in catchtheb all
Can keep going at unknown words

For unknown words e.g., jess - How do we know when to stop, keep looking at all combinations
e.g., for jesslooked, look at jesslooked, esslooked, sslooked, slooked, looked etc.

For big words, how do we know when to stop?
e.g., catch the, how to stop at catch and not at cat?
even at catc, try to ca + tc and see if it both matches
e.g., cat + ch - cat and catch match but not ch, so catch is the word
thebig - the and big match, but not thebig, so 2 separate words
so keep going until you find a word, then use this check

go from the back:
not needed if the above two words

Looking up a list takes O(l) everytime - length of dict - pretty huge
Making it a trie or hash set  is much easier
    hash set takes more space (duped chars) but is faster

Algorithm:
sb = ""
keep pulling chars one by one
    at least start with 2 (one doesn't make sense unless it's the end)
check all n ways of split ( e.g., n = 5, try 1,4 | 2,3 | 3,2)
    stop if word is found
    check other word + whole
        if both are words - then we have 2 separate words
        if second is word - 1 is unknown, 2 is a word (but just push 1 to finished, other might be incomplete
            e.g., jesscatch will stop at jess + cat here, so keep working with cat...
        if only first is word - keep going

pseudocode
convert dict (list) to hash set
    for each entry, add to hash set

curIdx = 0
curr = "" //type: StringBuilder
out = "" // type: sb
process():
    pull char from document
        s.charAt(currIdx)
    if charAt == null
        quit
    if curr.length == 1
        curr.concat (charAt)
        process(curr, idx + 1)
    else
        findFirstWord
            for i = 1 to n
                leftHalf = substring(0, i)
                    wordCheck(left, right)
                        if left.len == 1
                            if both results are true
                                out.concat leftHalf, space
                                process(currIdx, rightHalf) // right half is not a whole word yet,
                            else
                                continue
                        // len > 2
                        if both results are true
                            out.concat leftHalf, space, rightHalf
                            process(currIdx, "")
                        else
                            if left is false && right is true
                                out.concat leftHalf, space, rightHalf // left is unknown word
                                process(currIdx, "")







checkWaysToSplit(half1, half2) // Use Result { isLeftWord, isRightWord }
    check set.contains half1
    check set.contains half2
    return


 */