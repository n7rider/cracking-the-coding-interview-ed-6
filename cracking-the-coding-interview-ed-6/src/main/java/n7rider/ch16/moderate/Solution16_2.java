package n7rider.ch16.moderate;

/**
 * Word Frequencies: Design a method to find the frequency of occurrences of any given word in a
 * book. What if we were running this algorithm multiple times?
 *
 * After finishing:
 * I should probably write code for a simpler version before doing this. This is a jungle of 'while' and
 * 'if' - not complex by interviewing standards, but still is tricky for human brain, and I might run out of time.
 * Or I should check with the interviewer if we're okay to ditch the simpler version.
 *
 * After comparing with solution:
 * The book just went with trim(). That works too!
 *
 */
public class Solution16_2 {

    public static void main(String[] args) {
        System.out.println(findOccurrences("random", "delta"));
    }


    static int findOccurrences(String bookName, String word) {
        int occurrence = 0;
        int start = 0;
        int chunkLength = getChunkLength();
        char[] currChunk = getCurrentChunk(bookName, start, chunkLength);
        while(currChunk.length != 0) { // Keep loading chunks of the book
            int currChunkIdx = 0;
            while(currChunkIdx < currChunk.length) { // Run through the current chunk
                if(currChunk[currChunkIdx] == ' ' || currChunk[currChunkIdx] == '\n') {
                    if(currChunkIdx + word.length() < currChunk.length) { // Stop if we'll overflow
                        if(doWordsMatch(currChunk, currChunkIdx + 1, word)) {
                            occurrence++;
                            System.out.println("Word found. Occurrence as of now: " + occurrence);
                        }
                    }
                }
                currChunkIdx++;
            }
            start = start + currChunk.length;
            currChunk = getCurrentChunk(bookName, start, chunkLength);
        }

        return occurrence;
    }

    /*
    Simulates loading chunks from the book
     */
    static char[] getCurrentChunk(String bookName, int start, int chunkLength) {
        System.out.println("Getting chunk for start: " + start + " , chunkLength: " + chunkLength);
        String bookTextString = "alpha beta gamma delta epsilon delta delta";
        if(start >= bookTextString.length()) {
            return new char[] {};
        } else {
            // Assuming words don't exceed 15 chars. We try to stop at chunkLength though
            int endIndex = Math.min(start + chunkLength, bookTextString.length()); // 1-index based
            StringBuilder out = new StringBuilder(bookTextString.substring(start, endIndex)); // copy chunk
            int currPos = endIndex; // 0-based, so this is actually the next char
            while(currPos < bookTextString.length() && bookTextString.charAt(currPos) != ' ' &&
                    bookTextString.charAt(currPos) != '\n') { // continue copying until end of word is reached
                out.append(bookTextString.charAt(currPos));
                currPos++;
            }
            System.out.println("Curr chunk: " + out);
            return out.toString().toCharArray();
        }
    }

    private static boolean doWordsMatch(char[] currChunk, int wordStart, String givenWord) {
        for(int i = 0; i < givenWord.length(); i++) {
            if(givenWord.charAt(i) != currChunk[wordStart + i]) {
                return false;
            }
        }
        return true;
    }

    private static int getChunkLength() {
        return 10;
    }

}

/*
Find frequency of a word in a book (i.e., a long string)

Algorithm (Simplest, not Memory-sensitive):
Load book contents in String
Tokenize using String.split
Check each word for given word(s)

Needs storage of O(2l) where l is the length of the book

Algorithm (Memory-sensitive)
Stream a few chars, say 1000 at a time (OR) Load book contents in String from a String array from a feeder method that
  handles the streaming
if each char == space or LF character
    if next char + ... MATCH given word(s) // streaming shouldn't split between words, so it shouldn't stop right after
        reaching 1000, but after reaching next word. The array can be sized 1000 chars
      count

If we're running algorithm multiple times, we can
- store results in map <String, Integer>
- store all word count in map <String, Integer>
- create a trie storing char + int e.g., c - 0, ca - 0, cat - 3, catc, 0, catch - 2


 */