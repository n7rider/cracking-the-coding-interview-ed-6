package n7rider.ch16.moderate;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * T9: On old cell phones, users typed on a numeric keypad and the phone would provide a list of
 * words that matched these numbers. Each digit mapped to a set of O - 4 letters. Implement an algorithm
 * to return a list of matching words, given a sequence of digits. You are provided a list of valid
 * words (provided in whatever data structure you'd like). The mapping is shown in the diagram below:
 *
 *       1         2          3
 *                abc        def
 *
 *       4         5          6
 *      ghi       jkl        mno
 *
 *       7         8          9
 *     pqrs       tuv       wxyz
 *
 *                 0
 *
 * EXAMPLE
 * Input:
 * Output:
 * 8733
 * tree, used
 *
 * After completion:
 * Trie works but it looks complex to set it up. I think there is no other way though. You need to be able to navigate
 * down, but also figure out the entire word (navigate up). Caching can eliminate navigating up, but it can take a lot
 * of space.
 *
 * After comparing with solution:
 * Once the trie is figured out, the solution is simple. Navigating down a linked list. The book solution is a little
 * more graceful with a recursion to build the string (using the append everything to everything-so-far pattern for
 * finding all combinations)
 *
 */
public class Solution16_20 {
    public static void main(String[] args) {
        T9Dict dict = setup();
        var out1 = predictWords(dict, "8733");
        assertTrue(out1.size() == 2 && out1.contains("tree") && out1.contains("used"));

        var out2 = predictWords(dict, "8");
        assertTrue(out2.size() == 3 && out2.contains("t") && out2.contains("u") && out2.contains("v"));
    }

    static T9Dict setup() {
        Alphabet E3 = new Alphabet('e', List.of(), null); // e2 for tree
        Alphabet E2 = new Alphabet('e', List.of(E3), null); // e1 for tree
        Alphabet R2 = new Alphabet('r', List.of(E2), null); // r for tree

        Alphabet D2 = new Alphabet('d', List.of(), null); // d for used
        Alphabet E4 = new Alphabet('e', List.of(D2), null); // e for used
        Alphabet S2 = new Alphabet('s', List.of(E4), null); // s for used

        Alphabet D1 = new Alphabet('d', List.of(), null);
        Alphabet E1 = new Alphabet('e', List.of(), null);
        Alphabet R1 = new Alphabet('r', List.of(), null);
        Alphabet S1 = new Alphabet('s', List.of(), null);
        Alphabet T1 = new Alphabet('t', List.of(R2), null);
        Alphabet U1 = new Alphabet('u', List.of(S2), null);
        Alphabet V1 = new Alphabet('v', List.of(), null);

        E3.parent = E2;
        E2.parent = R2;
        R2.parent = T1;

        D2.parent = E4;
        E4.parent = S2;
        S2.parent = U1;

        NumKey three = new NumKey('3', List.of(D1, E1));
        NumKey seven = new NumKey('7', List.of(R1, S1));
        NumKey eight = new NumKey('8', List.of(T1, U1, V1));

        Map<Character, NumKey> mappings = Map.of('3', three, '7', seven, '8', eight);
        return new T9Dict(mappings);
    }

    static Set<String> predictWords(T9Dict dict, String inp) {
        // Validate inp is not empty
        // Validate inp is alphaNumeric

        Set<Alphabet> currTrieAlphabets = new CopyOnWriteArraySet<>(); // T U V
        NumKey currNumKey = dict.mappings.get(inp.charAt(0));
        for(Alphabet keyAlph: currNumKey.alphabets) {
            currTrieAlphabets.add(keyAlph);
            System.out.printf("Added %c to Set \n\n", keyAlph.key);
        }

        for(int i = 1; i < inp.length(); i++) {
            currNumKey = dict.mappings.get(inp.charAt(i));

            // Find all currKey alphabets (e.g., 7's PQRS) that match curr. output (T1's list)
            for(Alphabet currTrieAlph: currTrieAlphabets) {
                for(Alphabet nextAlph: currTrieAlph.nextNodes) {
                    for(Alphabet keyAlph: currNumKey.alphabets) {
                        if (nextAlph.key == keyAlph.key) {
                            System.out.printf("Added %c to Set \n", nextAlph.key);
                            currTrieAlphabets.add(nextAlph);
                        }
                    }
                }
                System.out.printf("Removed %c from Set \n\n", currTrieAlph.key);
                currTrieAlphabets.remove(currTrieAlph);
            }
        }

        // print out
        Set<String> out = new HashSet<>();
        for(Alphabet edgeAlph: currTrieAlphabets) {
            StringBuilder currWord = new StringBuilder();
            currWord.append(edgeAlph.key);
            while(edgeAlph.parent != null) {
                currWord.append(edgeAlph.parent.key);
                edgeAlph = edgeAlph.parent;
            }

            out.add(currWord.reverse().toString());
        }

        return out;
    }

    static class T9Dict {
        Map<Character, NumKey> mappings;

        T9Dict(Map<Character, NumKey> mappings) {
            this.mappings = mappings;
        }
    }

    static class NumKey {
        char key;
        List<Alphabet> alphabets;

        NumKey(char key, List<Alphabet> alphabets) {
            this.key = key;
            this.alphabets = alphabets;
        }
    }

    static class Alphabet {
        char key;
        List<Alphabet> nextNodes;
        Alphabet parent;

        Alphabet(char key, List<Alphabet> nextNodes, Alphabet parent) {
            this.key = key;
            this.nextNodes = nextNodes;
            this.parent = parent;
        }
    }
}

/*
So, it's simple:
    Alphabet -  Node of key | List<Nodes> or Map<Char, Node> | Node parent
    Num - int key | Alphabet[]

    We should be able to piece things together using the parent at the end


Alg:
    currNum = in[idx] // idx = 0
    For each alpha in currNum // A translation e.g., 8 - t u v
        Node[] endNodes = numKey.alphabets // can be a set
    idx++
    while idx < inp.length
        alphabetsForCurrNum = in[idx]
        for(endNode: endNodes)
            if endNode.map.contains(any of alphabets for curr num)
                endNodes.remove(endNode)
                endNode.add (all that matches)
        idx++

    // print out
    for(endNode: endNodes)
        keep going up
        print each string

 */

/*
Common knowledge: T9 and similar algs use trie

Auto-correct is not needed, so just keep going down a node for a match
e.g.,

8 (tuv) --> 7 (pqrs) -> 3 (def)
t u v        r s        (leading to tree, used,..)
        --> 2 (abc)
             a         (leading to tap...)
        --> 6 (mno)
             n o       (leading to undo, to, ton...)


Data structure:
Node:
    key  int
    val  char[]
    nextLetter - Map<key, Node>

e.g.,
    key - 8 | val - [t, u, v]
    | nextLetter - 7 [r, s]

Each no. can have 0-4 chars, and each char has its own chain, not each no.

So present them as NoKey and Alphabet

NumKey:
    key int
    vals Alphabet[]

Alphabet
    key char
    map<Int, Alphabet[])

These are not swappable, their position in the tree matters

NoKey: key = 8, vals - [t, u , v]

Alphabet | t - next [7, r] & then next [3, e]...
Alphabet | u - next [7, s] & then next [3, e]...
Alphabet | v - ...

We can have enums for these vars to minimize size

Algo:
    // validation
    String out[] = "" // Not used
    Map<Char, String> out
    Alphabets[] currKeysAlphabets = []
    do {
    int i = 0
    if out[] is empty
        Start at root - Root Map<Int, NumKey>
        currKeysAlphabets = map.get(in[i])  // Now it's [t, u, v]
        out = map.get(in[i]).vals.forEach(String::new) // key and val are same
    else
        currKeysAlphabets.forEach
            Add alpha.get(in[i]) to out i.e., word-so-far. You can use alpha.key for i = 1, but for rest you need all prev
            Just change out[] to a map<Char, String> with currKeyAlpha as key, and word-so-far as string
            So, word = out.get(currAlpha) + alpha.get(in[i])

This should work, but it looks heavy. I should visualize, use pen and paper to draw the tree structure. It should be simpler.


So, it's simple:
    Alphabet -  Node of key | List<Nodes> or Key<Char, Node> | Node parent
    Num - int key | Alphabet[]

    We should be able to piece things together using the parent at the end


Alg:
    currNum = in[idx] // idx = 0
    For each alpha in currNum // A translation e.g., 8 - t u v
        Node[] endNodes = numKey.alphabets // can be a set
    idx++
    while idx < inp.length
        alphabetsForCurrNum = in[idx]
        for(endNode: endNodes)
            if endNode.map.contains(any of alphabets for curr num)
                endNodes.remove(endNode)
                endNode.add (all that matches)
        idx++

    // print out
    for(endNode: endNodes)
        keep going up
        print each string






 */