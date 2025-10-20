package n7rider.ch17.hard;

/**
 * Longest Word: Given a list of words, write a program to find the longest word made of other words
 * in the list.
 * EXAMPLE
 * Input:cat, banana, dog, nana, walk, walker, dogwalker
 * Output: dogwalker
 *
 * After completing:
 * Use a trie/hashmap, and check char of each word, but it can break early if the starting seq
 * doesn't match. Still runs in O(n*m) time
 *
 * After comparing:
 * Looks like the problem is limited to words made entirely with other words from the list, no just
 * a word that has the longest word from the list in its substring.
 * The book uses a Hashmap<String, boolean>  for the dict. This is efficient than a Set in this case,
 * since you can flip the boolean based on convenience
 * Also the book tries to optimize by adding every previously found key to the hash map. Looking up
 * a non-existing key has the same cost in a hashmap, so unless the requirement say that the actual
 * look up is expensive, this is not really beneficial. This is something you can aim for if you don't
 * see any other room for improvement.
 */
public class Solution17_15 {
}

/*
Simplest solution:

longestCount = 0
longestWord = ""

checkString(word)
for i = 0 to n-1
    for j = i to n-1
        currWord = text(i, j) // We're considering 1 char words too
        if currWord IS NOT a word in list
            continue
        if currWord.count > longestCount
            longestWord = word

for i = 0 to n-1
    checkString(list.get(i)

Runtime:
checkString takes n * m // n - no of words, m - avg word length
For n words, this takes O(n^2 * m)
IS a word in list => can take O(n) times more unless we add a hash map, so O(n ^ 3 * m)

Make this better:

- Add a hashmap/set/trie for lookup
    if currWord IS word in list // do in O(1) with hash map or O(log n) with trie. Trie is efficient but hashmap is simpler
- SKip looking at smaller words
    currWord = text (i, max(j, longestCount)) // doesn't matter even if smaller words match
    // Also skip if max(j, longestCount) goes beyond text's max index
    // Also skip if word.len < longestWord

Runtime:
Without IS a word in list check, it is O(n^3), but checkString will take around O(n * m / 2)
because by average, we skip half the text

Creating the trie once takes O(n)

Memoization?
    Go through the trie for curr. word. If it is not there, you can continue immediately, so need for j = i to n-1 to
    keep running unless there's a viable path.
    j = 0 to n-1 will still run the entire length though.

Pseudocode:

Trie {
   List<Node> nodes // starting nodes

   wordExists(String s)
        nodes = nodes;
        for(char c : s.toCharArray)
            node = nodes.stream.filter(x -> x = c).findFirst()
            if node != null
                return false
            else
                nodes = node.childNode
        return true

}

Node {
    List<Node> childNodes
    char c
}

// Globally kept, but we can pass these around as params too
longestWord = ""
longestCount = 0

findLongestWordWithOtherWords(List<String> list)
    // Add validations for the list here
    for i = 0 to n-1
        prepare trie
            append to its list if char exists, else add char


    for i = 0 to n-1
        for j = i to n-1
            currWord = text(i, j) // We're considering 1 char words too
            if currWord IS NOT a word in list
                continue
            if currWord.count > longestCount
                longestWord = word

 */