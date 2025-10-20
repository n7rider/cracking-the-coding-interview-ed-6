package n7rider.ch17.hard;

import java.util.*;

/**
 * Word Distance: You have a large text file containing words. Given any two words, find the shortest
 * distance (in terms of number of words) between them in the file. If the operation will be repeated
 * many times for the same file (but different pairs of words), can you optimize your solution?
 *
 * After finishing:
 * Not implementing case insensitivity, ignoring symbols, but the core idea is there.
 * The trie is a good idea, but otherwise it's run-of-the-mill which makes me wonder if there is a better way.
 *
 * After comparing:
 * The shortest distance is to be found between any two numbers - not the distance between successive occurrences of the same word.
 */
public class Solution17_11 {
    public static void main(String[] args) {
        System.out.println(findShortestDistance("the", "file.txt"));
    }

    static Trie trie;

    static int findShortestDistance(String word, String fileName) {

        if(trie == null) {
            trie = new Trie();
            trie.head = new Node(' ', 0);
            Node head = trie.head;

            FileReadResult nextWordResult = new FileReadResult("", 0, Integer.MAX_VALUE);
            do {
                nextWordResult = getNextWord(fileName, nextWordResult.lastPosition);
                traverseToLeaf(nextWordResult.word, head, nextWordResult.lastPosition);
            } while (nextWordResult.lastPosition < nextWordResult.totalLength );

        }

        Node head = trie.head;

        System.out.println(trie);

        return findShortestDistance(word, head);
    }

    static int findShortestDistance(String word, Node head) {
        Node currParent = head;
        for(char c: word.toCharArray()) {
            Optional<Character> currNode = currParent.childNodes.keySet().stream().filter(currChar -> currChar.equals(c)).findFirst();
            Node updatedNode;
            if(currNode.isEmpty()) {
                return - 1; // Word doesn't exist
            } else {
                updatedNode = currParent.childNodes.get(c);
            }
            currParent = updatedNode;
        }
        return currParent.smallestDist;
    }

    static void traverseToLeaf(String word, Node head, int index) {
        Node currParent = head;
        for(char c: word.toCharArray()) {
            Optional<Character> currNode = currParent.childNodes.keySet().stream().filter(currChar -> currChar.equals(c)).findFirst();
            Node updatedNode;
            if(currNode.isEmpty()) {
                updatedNode = new Node(c, index);
                currParent.childNodes.put(c, updatedNode);
            } else {
                updatedNode = currParent.childNodes.get(c);
                if(updatedNode.smallestDist > index - updatedNode.lastIdx) {
                    updatedNode.smallestDist = index - updatedNode.lastIdx;
                }
                updatedNode.lastIdx = index;
            }

            currParent = updatedNode;
        }
    }

    private static FileReadResult getNextWord(String fileName, int index) {
        // Mimicking file reading here
        String text = "Far in the the past, it was part but not too far. What is the cat?";
        
        StringBuilder out = new StringBuilder();
        while (index < text.length()) {
            char currChar = text.charAt(index);
            if(currChar == ' ') {
                if(out.length() > 0) {
                    break;
                }
            } else {
                out.append(currChar);
            }
            index++;
        }
        return new FileReadResult(out.toString(), index, text.length());
    }



    static class Trie {
        Node head;

        @Override
        public String toString() {
            return String.format("%s", head.childNodes);
        }
    }

    static class Node {
        char c;
        int smallestDist;
        int lastIdx;
        Map<Character, Node> childNodes;

        Node(char c, int lastIdx) {
            this.c = c;
            this.smallestDist = Integer.MAX_VALUE;
            this.lastIdx = lastIdx;
            this.childNodes = new HashMap<>();
        }

        @Override
        public String toString() {
            return String.format("%c - %d - %d - %s", c, smallestDist, lastIdx, childNodes);
        }
    }

    static class FileReadResult {
        String word;
        int lastPosition;
        int totalLength;

        FileReadResult(String word, int lastPosition, int totalLength) {
            this.word = word;
            this.lastPosition = lastPosition;
            this.totalLength = totalLength;
        }

        @Override
        public String toString() {
            return String.format("%s - %d - %d", word, lastPosition, totalLength);
        }
    }
}

/*
Simplest solution:
- Use a hash map to store text, <smallest length, last index>

while book.nextWord  != null
    currWord = getNextWord() // accumulate characters until you hit space
    if map.contains CurrWord
        int dist = abs (last index - curr)
        update sm. len if dist is lesser
    else
        map.put sm len = inf, last idx = currIndex

return map.get (word)

Optimize runtime:
We need to run till the end for the first word, but for the next word onwards hashmap can be used

Can we optimize space?
If we optimize space, we might end up increasing runtime
And we duplicate a lot of space e.g., nation and national are duplicated in the namespace.
How about using a trie? where we store sm. len + last index at each letter
Do we need both sm. len & last idx?
    Yes if sm. len happened at say 14-40, we need to be able to find if something smaller happened later after 1000 words.

How do we use trie instead of hashmap
- Rather than calling map.get, do trie.find
    trie.find will do currLevel.getChild(char) at every level
    If it can't find a char, it keeps adding until all the chars are added

Each word's dist is saved only at the leaf node

Pseudocode:
Node
  int smLen, lastIdx, char c
  Node[] nextNodes; // can have a hash map too if it gets too big. But with a max of 26 possibilities, it's probably not needed

Trie
  Node root

Result
  Node node
  int len // len upto which we were able to travel

We can repeat the above algorithm entirely:

while book.nextWord  != null
    currWord = getNextWord() // accumulate characters until you hit space
    if map.contains CurrWord // try navigating to the end of the word. Return Result type
        int dist = abs (last index - curr)
        update sm. len if dist is lesser
    else
        map.put sm len = inf, last idx = currIndex


 */